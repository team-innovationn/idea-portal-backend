package com.ecobank.idea.service;

import com.ecobank.idea.entity.VerificationToken;
import com.ecobank.idea.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.ecobank.idea.constants.AppConstants.OTP_VALIDITY_MINUTES;

@Service
@RequiredArgsConstructor
public class TokenVerificationService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    public boolean validateOtp(String email, String otp) {
        Optional<VerificationToken> otpToken = verificationTokenRepository.findByEmailAndTokenAndUsedFalseAndExpiryTimeAfter(
                email,
                otp,
                LocalDateTime.now()
        );

        if (otpToken.isPresent()) {
            VerificationToken token = otpToken.get();
            token.setUsed(false);
            verificationTokenRepository.save(token);
            return true;
        }
        return false;
    }

    public void sendOtp(String email) {
        String otp = generateOtp();

        VerificationToken otpToken = VerificationToken.builder().email(email).token(otp).expiryTime(LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES)).used(false).build();

        try {
            verificationTokenRepository.save(otpToken);
        } catch (Exception exception) {
            throw new RuntimeException("Unable to generate token!" + exception.getMessage());
        }

        System.out.println(otpToken);

        String htmlMsg = generateOTPHtmlMessage(otp);

        String htmlSubject = "EMAIL VERIFICATION";

        try {
            emailService.sendVerificationEmail(email, htmlSubject, htmlMsg);
        } catch (Exception exception) {
            throw new RuntimeException("Error sending verification token!" + exception.getMessage());
        }
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private String generateOTPHtmlMessage(String OTP) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body style=\"margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;\">" +
                "    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"100%\" style=\"max-width: 600px; margin: auto; padding: 40px 20px;\">" +
                "        <tr>" +
                "            <td style=\"background-color: #ffffff; padding: 40px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);\">" +
                "                <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">" +
                "                    <tr>" +
                "                        <td style=\"padding-bottom: 32px; text-align: center;\">" +
                "                            <img src=\"YOUR_LOGO_URL\" alt=\"Company Logo\" style=\"max-width: 150px; height: auto;\">" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr>" +
                "                        <td style=\"padding-bottom: 24px;\">" +
                "                            <h1 style=\"margin: 0; font-size: 24px; font-weight: bold; color: #333333; text-align: center;\">" +
                "                                Your One-Time Password" +
                "                            </h1>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr>" +
                "                        <td style=\"padding-bottom: 24px; text-align: center; color: #666666;\">" +
                "                            <p style=\"margin: 0; font-size: 16px; line-height: 24px;\">" +
                "                                Please use the following OTP to complete your sign in. This code will expire in 5 minutes." +
                "                            </p>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr>" +
                "                        <td style=\"padding-bottom: 32px; text-align: center;\">" +
                "                            <div style=\"background-color: #f8f9fa; padding: 16px; border-radius: 4px; display: inline-block;\">" +
                "                                <span style=\"font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #333333;\">" +
                "                                    " + OTP +
                "                                </span>" +
                "                            </div>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr>" +
                "                        <td style=\"padding-bottom: 24px; text-align: center; color: #666666;\">" +
                "                            <p style=\"margin: 0; font-size: 14px; line-height: 20px;\">" +
                "                                If you didn't request this code, please ignore this email." +
                "                            </p>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr>" +
                "                        <td style=\"border-top: 1px solid #e0e0e0; padding-top: 24px; text-align: center; color: #999999;\">" +
                "                            <p style=\"margin: 0; font-size: 12px; line-height: 18px;\">" +
                "                                This is an automated message, please do not reply to this email.<br>" +
                "                                © 2024 Your Company Name. All rights reserved." +
                "                            </p>" +
                "                        </td>" +
                "                    </tr>" +
                "                </table>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";
    }

}
