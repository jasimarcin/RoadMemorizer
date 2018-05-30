package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

public class ErrorViewState extends CurrentLocationViewState {

    private String message;

    public ErrorViewState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
