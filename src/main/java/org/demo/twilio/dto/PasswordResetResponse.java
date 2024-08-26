package org.demo.twilio.dto;

import org.demo.twilio.domain.OtpStatus;

public record PasswordResetResponse(OtpStatus otpStatus,
                                    String message) {
}
