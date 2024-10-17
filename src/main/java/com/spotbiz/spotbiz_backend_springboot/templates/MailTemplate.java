package com.spotbiz.spotbiz_backend_springboot.templates;

import java.nio.charset.StandardCharsets;

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

    public static String getCouponEmail(String recipientName, String couponCode, float discountPercentage) {
        try{
            String apiUrl = "https://quickchart.io/qr";
            String parameters = String.format(
                    "ecLevel=H&size=200&text=%s",
                    java.net.URLEncoder.encode(String.valueOf(couponCode), StandardCharsets.UTF_8));
            String qrCodeUrl = apiUrl + "?" + parameters;

            return "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; }" +
                    ".container { background-color: #ffffff; margin: 50px auto; padding: 20px; max-width: 600px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                    ".header { text-align: center; padding: 10px 0; }" +
                    ".content { font-size: 16px; line-height: 1.6; }" +
                    ".button { display: inline-block; padding: 10px 20px; margin: 20px 0; font-size: 16px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }" +
                    ".footer { text-align: center; font-size: 12px; color: #888888; margin-top: 20px; }" +
                    ".qr-code { text-align: center; margin: 20px 0; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<div class='header'>" +
                    "<h1>SpotBiz</h1>" +
                    "</div>" +
                    "<div class='content'>" +
                    "<p>Dear " + recipientName + ",</p>" +
                    "<p>Congratulations! 🎉 You have won a discount coupon worth <strong>" + discountPercentage + "% off</strong> your next purchase!</p>" +
                    "<p>Use the coupon code below at checkout:</p>" +
                    "<h2 style='text-align: center; color: #007bff;'>" + couponCode + "</h2>" +
                    "<div class='qr-code'>" +
                    "<p>Or simply scan the QR code below to redeem your discount:</p>" +
                    "<img src='" + qrCodeUrl + "' alt='Coupon QR Code' style='max-width: 100%; height: auto;' />" +
                    "</div>" +
                    "<p>Don't miss out on this amazing opportunity. Happy shopping!</p>" +
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
        catch(Exception e){
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String banBusinessTemplate(String recipientName, String reason, Integer businessId) {
        String redirectLink = "http://localhost:5173/login/"; // attach the business id to the link
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
                "<p>Dear "+ recipientName +"</p>" +
                "<p>We regret to inform you that due to a violation of our terms of service, your account on SpotBiz has been temporarily banned.</p>" +

                "<p><b>Reason for the ban:</b><p>"+
                "<p>" + reason +"</p>" +

                "<p>We understand that this may be concerning, and we want to offer you the opportunity to appeal this decision if you believe it was made in error. To submit an appeal, please click the button below and provide further details regarding your case.</p>" +
                "<a href='" + redirectLink + "' class='button'>Submit Appeal</a>" +
                "<br/><br/>" +
                "<p>If the button above does not work, use the link below:</p>" +
                "<a href='" + redirectLink + "'>" + redirectLink + "</a>" +

                "<p>Please note that during the appeal process, your account will remain inaccessible. We appreciate your patience and cooperation.</p>" +
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

    public static String appealApprovedTemplate(String recipientName) {
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
                "<p>Dear "+ recipientName +"</p>" +
                "<p>We hope this message finds you well.</p>" +
                "<p>We wanted to inform you that your appeal regarding the ban on your account has been carefully reviewed. Our team has taken the time to consider all the details and information you provided.</p>" +
                "<p>We are pleased to inform you that your appeal has been approved, and your account has been reinstated. You can now access your account and continue using our services.</p>" +
                "<p>We appreciate your patience and understanding throughout this process. If you have any questions or concerns, please do not hesitate to contact us.</p>" +
                "<p>Thank you for choosing SpotBiz.</p>" +
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
