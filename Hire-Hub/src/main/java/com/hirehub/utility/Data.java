package com.hirehub.utility;

public class Data {

    public static String getMessageBody(String otp, String name) {

    	return "<div style='font-family: Segoe UI, Arial; background-color: #eef2f7; padding: 30px;'>"
                + "<div style='max-width: 520px; margin: auto; background: #ffffff; border-radius: 14px;"
                + " box-shadow: 0 6px 18px rgba(0,0,0,0.1); padding: 30px; text-align: center;'>"
                + "<h1 style='color: #4f46e5;'>HireHub</h1>"
                + "<h2>🔐 Password Reset OTP</h2>"
                + "<p>Hello<b>" + name + "</b>,</p>"
                + "<p>Your OTP is:</p>"
                + "<div style='font-size:28px;font-weight:bold;color:white;"
                + "background: linear-gradient(90deg,#4f46e5,#7c3aed);"
                + "padding:10px 20px;border-radius:10px;display:inline-block;'>"
                + otp +
                "</div>"
                + "<p>This OTP is valid for 5 minutes.</p>"
                + "<p style='font-size:12px;color:gray;'>© 2026 HireHub</p>"
                + "</div></div>";
    }
}