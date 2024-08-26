package org.demo.twilio.resource.handler;

import org.demo.twilio.dto.PasswordResetRequest;
import org.demo.twilio.service.TwilioOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioOtpHandler {

    @Autowired
    private TwilioOtpService twilioOtpService;

    public Mono<ServerResponse> sendOtp(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequest.class)
                .flatMap(dto -> twilioOtpService.sendOtpForPasswordReset(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(dto)));
    }

    public Mono<ServerResponse> ValidateOtp(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequest.class)
                .flatMap(dto -> twilioOtpService.validateOtp(dto.otp(), dto.userName()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(dto)));
    }


}
