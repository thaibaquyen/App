package com.example.navigation;

public class classcomment {
    private String sdt,comment,datetime;
    private Double start;

    public classcomment() {
    }

    public classcomment(String sdt, String comment, Double start, String datetime) {
        this.sdt = sdt;
        this.comment = comment;
        this.start = start;
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }
}
