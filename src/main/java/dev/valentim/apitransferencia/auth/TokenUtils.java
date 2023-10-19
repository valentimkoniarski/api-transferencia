package dev.valentim.apitransferencia.auth;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (!isFormatoTokenValido(token)) {
            return null;
        }
        return token.substring(7, token.length());
    }

    public static boolean isFormatoTokenValido(String token) {
        return token != null && (token.startsWith("Bearer ") || token.startsWith("bearer "));
    }
}
