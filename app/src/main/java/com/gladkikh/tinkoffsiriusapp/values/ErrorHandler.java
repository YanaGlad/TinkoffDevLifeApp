package com.example.tinkoffsiriusapp.values;

public class ErrorHandler {
    private static String[] type_error = {
            "SUCCESS",
            "LOAD_ERROR",
            "IMAGE_ERROR"
    };

    public static String success() {
        return type_error[0];
    }

    public static String loadError() {
        return type_error[1];
    }

    public static String imageError() {
        return type_error[2];
    }

    private String currentError = type_error[0];

    public void setSuccess() {
        this.currentError = type_error[0];
    }

    public void setLoadError() {
        this.currentError = type_error[1];
    }

    public void setImageError() {
        this.currentError = type_error[2];
    }

    public String getCurrentError() {
        return currentError;
    }
}
