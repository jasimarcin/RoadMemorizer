package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;


public class CurrentLocationViewState {

    protected boolean showSaveButton;
    protected boolean showStopSavingButton;
    protected boolean showAlignButton;

    public boolean isShowSaveButton() {
        return showSaveButton;
    }

    public boolean isShowStopSavingButton() {
        return showStopSavingButton;
    }

    public boolean isShowAlignButton() {
        return showAlignButton;
    }

    protected CurrentLocationViewState(Builder builder) {
        showSaveButton = builder.showSaveButton;
        showStopSavingButton = builder.showStopSavingButton;
        showAlignButton = builder.showAlignButton;
    }

    public void setShowSaveButton(boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
    }

    public void setShowStopSavingButton(boolean showStopSavingButton) {
        this.showStopSavingButton = showStopSavingButton;
    }

    public void setShowAlignButton(boolean showAlignButton) {
        this.showAlignButton = showAlignButton;
    }

    public static class Builder {
        protected boolean showSaveButton;
        protected boolean showStopSavingButton;
        protected boolean showAlignButton;

        public Builder() {
        }

        public Builder showSaveButton(boolean val) {
            showSaveButton = val;
            return this;
        }

        public Builder showStopSavingButton(boolean val) {
            showStopSavingButton = val;
            return this;
        }

        public Builder showAlignButton(boolean val) {
            showAlignButton = val;
            return this;
        }

        public CurrentLocationViewState build() {
            return new CurrentLocationViewState(this);
        }
    }
}
