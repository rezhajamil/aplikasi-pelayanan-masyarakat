package com.dina.aplikasipelayananmasyarakat;

public class Report {
    String email;
    String message;

    public Report(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
