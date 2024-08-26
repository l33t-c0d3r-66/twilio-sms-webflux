package org.demo.twilio.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.demo.twilio.config.TwilioConfig;
import org.demo.twilio.domain.OtpStatus;
import org.demo.twilio.dto.PasswordResetRequest;
import org.demo.twilio.dto.PasswordResetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.Deflater;

@Service
public class TwilioOtpService {

    @Autowired
    private TwilioConfig twilioConfig;

    private Map<String, String> otpMap = new HashMap<>();

    public Mono<PasswordResetResponse> sendOtpForPasswordReset(PasswordResetRequest passwordResetRequest) {
        PasswordResetResponse passwordResetResponse;
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequest.phoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOtp();
            String otpMessage = "Dear Customer, Your OTP is ##"+otp+"## Use this passcode to complete your transaction.";
            otpMap.put(passwordResetRequest.userName(), otp);
            Message message = Message.creator(to, from, otpMessage)
                    .create();

            passwordResetResponse = new PasswordResetResponse(OtpStatus.DELIVERED, otpMessage);

        } catch(Exception e) {
            passwordResetResponse = new PasswordResetResponse(OtpStatus.FAILED, e.getMessage());
        }
        return Mono.just(passwordResetResponse);
    }

    public Mono<String> validateOtp(String userInputOtp, String userName) {
       if(userInputOtp.equals(otpMap.getOrDefault(userName, ""))) {
           return Mono.just("Valid otp please proceed with your transaction");
        }
       return Mono.error(new IllegalArgumentException("Invalid otp please retry"));
    }

    private String generateOtp() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
