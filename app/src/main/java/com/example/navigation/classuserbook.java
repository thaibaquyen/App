package com.example.navigation;

public class classuserbook {
    public int idsalon;
    public String sdtnguoidat, datetime;

    public classuserbook() {
    }

    public classuserbook(int idsalon, String sdtnguoidat, String datetime) {
        this.idsalon = idsalon;
        this.sdtnguoidat = sdtnguoidat;
        this.datetime = datetime;
    }

    public int getIdsalon() {
        return idsalon;
    }

    public void setIdsalon(int idsalon) {
        this.idsalon = idsalon;
    }

    public String getSdtnguoidat() {
        return sdtnguoidat;
    }

    public void setSdtnguoidat(String sdtnguoidat) {
        this.sdtnguoidat = sdtnguoidat;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
