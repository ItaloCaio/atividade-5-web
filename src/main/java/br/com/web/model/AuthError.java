package br.com.web.model;

public class AuthError {
    private String message;

    public AuthError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
