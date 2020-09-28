package com.example.navigation;

import java.io.Serializable;

public class classsalon implements Serializable {
    public int idsalon,size,freesize,kc;
    public String diachi,img1,sdt,phuong;
    public Double dx,dy,ivaluate,price;

    public classsalon() {
    }

    public classsalon(int kc,int idsalon,int size,int freesize,String diachi,String img1,String sdt,String phuong,Double dx,Double dy,Double ivaluate,Double price){
        this.kc = kc;
        this.idsalon = idsalon;
        this.size = size;
        this.freesize = freesize;
        this.diachi = diachi;
        this.img1= img1;
        this.sdt = sdt;
        this.phuong = phuong;
        this.dx = dx;
        this.dy = dy;
        this.ivaluate = ivaluate;
        this.price = price;
    }

    public int getKc() {
        return kc;
    }

    public void setKc(int kc) {
        this.kc = kc;
    }

    public int getIdsalon() {
        return idsalon;
    }

    public void setIdsalon(int idsalon) {
        this.idsalon = idsalon;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFreesize() {
        return freesize;
    }

    public void setFreesize(int freesize) {
        this.freesize = freesize;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public Double getDx() {
        return dx;
    }

    public void setDx(Double dx) {
        this.dx = dx;
    }

    public Double getDy() {
        return dy;
    }

    public void setDy(Double dy) {
        this.dy = dy;
    }

    public Double getIvaluate() {
        return ivaluate;
    }

    public void setIvaluate(Double ivaluate) {
        this.ivaluate = ivaluate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "classsalon{" +
                "idsalon=" + idsalon +
                ", size=" + size +
                ", freesize=" + freesize +
                ", diachi='" + diachi + '\'' +
                ", img1='" + img1 + '\'' +
                ", sdt='" + sdt + '\'' +
                ", phuong='" + phuong + '\'' +
                ", dx=" + dx +
                ", dy=" + dy +
                ", ivaluate=" + ivaluate +
                ", price=" + price +
                '}';
    }
}
