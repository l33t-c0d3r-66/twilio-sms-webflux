package org.demo.twilio.dto;

public record PasswordResetRequest(String phoneNumber,
                                   String userName,
                                   String otp) {
}
