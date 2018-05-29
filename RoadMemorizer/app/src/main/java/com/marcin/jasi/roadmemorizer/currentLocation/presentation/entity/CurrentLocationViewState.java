package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

public abstract class CurrentLocationViewState {

    private boolean showSaveButton;
    private boolean showStopSaveingButton;
    private boolean showAligmButton;


    public boolean isShowSaveButton() {
        return showSaveButton;
    }

    public boolean isShowStopSavingButton() {
        return showStopSaveingButton;
    }

    public boolean isShowAligmButton() {
        return showAligmButton;
    }

    public void setShowSaveButton(boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
    }

    public void setShowStopSaveingButton(boolean showStopSaveingButton) {
        this.showStopSaveingButton = showStopSaveingButton;
    }

    public void setShowAligmButton(boolean showAligmButton) {
        this.showAligmButton = showAligmButton;
    }
}
