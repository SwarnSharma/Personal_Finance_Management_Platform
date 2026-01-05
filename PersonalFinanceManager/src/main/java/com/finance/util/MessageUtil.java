package com.finance.util;

import jakarta.servlet.http.HttpSession;

public class MessageUtil {

    public static void setSuccess(HttpSession session, String message) {
        session.setAttribute("successMessage", message);
        session.removeAttribute("errorMessage");
    }

    public static void setError(HttpSession session, String message) {
        session.setAttribute("errorMessage", message);
        session.removeAttribute("successMessage");
    }

    public static void clear(HttpSession session) {
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
    }
}
