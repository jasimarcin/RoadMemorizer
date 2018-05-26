package com.marcin.jasi.roadmemorizer.general.view;

public class AppToolbarData {

    private int id;
    private String rowText;

    public AppToolbarData(int id, String rowText) {
        this.id = id;
        this.rowText = rowText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRowText() {
        return rowText;
    }

    public void setRowText(String rowText) {
        this.rowText = rowText;
    }
}
