package com.spotbiz.spotbiz_backend_springboot.templates;

public class MailTemplate {

    public static String getVerificationEmail(String recipientName, String verificationCode) {
        String verificationLink = "http://localhost:8080/api/v1/customer/verify/" + verificationCode;

        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; }" +
                ".container { background-color: #ffffff; margin: 50px auto; padding: 20px; max-width: 600px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                ".header { text-align: center; padding: 10px 0; }" +
                ".content { font-size: 16px; line-height: 1.6; }" +
                ".button { display: inline-block; padding: 10px 20px; margin: 20px 0; font-size: 16px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }" +
                ".footer { text-align: center; font-size: 12px; color: #888888; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>SpotBiz</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Dear " + recipientName + ",</p>" +
                "<p>Thank you for registering. Please click the button below to verify your email address:</p>" +
                "<a href=\"" + verificationLink + "\" class='button'>Verify Email</a>" +
                "<p>If the button above does not work, you can also verify your email by clicking the link below:</p>" +
                "<a href=\"" + verificationLink + "\">" + verificationLink + "</a>" +
                "<br/><br/>" +
                "<p>Best regards,</p>" +
                "<p>Team SpotBiz</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>&copy; " + java.time.Year.now().getValue() + " SpotBiz. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public static String getBusinessVerificationEmail(String recipientName, String businessName) {
        String redirectLink = "http://localhost:5173/login/";
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #ebf3fa;  FONmargin: 0; padding: 0; }" +
                ".container { background-color: #ffffff; margin: 50px auto; padding: 20px; max-width: 600px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                ".header { text-align: center; padding: 10px 0; background-color: #0D3B66; color: #ffffff; border-top-left-radius: 8px; border-top-right-radius: 8px; }" +
                ".header h1 { margin: 0; }" +
                ".content { font-size: 16px; line-height: 1.6; color: #374151; }" +
                ".button { display: inline-block; padding: 10px 20px; margin: 20px 0; font-size: 16px; color: #ffffff; background-color: #bfdbfe; text-decoration: none; border-radius: 5px; }" +
                ".footer { text-align: center; font-size: 12px; color: #6b7280; margin-top: 20px; background-color: #FAF0CA; padding: 10px; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>SpotBiz</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Dear " + recipientName + ",</p>" +
                "<p>Congratulations! Your business, " + businessName + ", has been verified by the admin panel.</p>" +
                "<p>Thank you for registering. Please click the button below to Login to your account:</p>" +
                "<a href='" + redirectLink + "' class='button'>Click Here to Continue</a>" +
                "<p>If the button above does not work, you can also verify your email by clicking the link below:</p>" +
                "<a href='" + redirectLink + "'>" + redirectLink + "</a>" +
                "<br/><br/>" +
                "<p>Best regards,</p>" +
                "<p>Team SpotBiz</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>&copy; " + java.time.Year.now().getValue() + " SpotBiz. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
