package com.marcin.jasi.roadmemorizer.general.view;

public class RowAppToolbarViewModel {

    private AppToolbarData data;

    public RowAppToolbarViewModel(AppToolbarData data) {
        this.data = data;
    }

    String getTitle() {
        return data.getRowText();
    }
}
