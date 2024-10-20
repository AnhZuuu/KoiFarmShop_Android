package com.koifarmshop.model;

public class Cart {
    int idCa;
    String tenCa;
    long giaCa;
    String hinhCa;
    int soluong;

    public Cart() {

    }

    public int getIdCa() {
        return idCa;
    }

    public void setIdCa(int idCa) {
        this.idCa = idCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public long getGiaCa() {
        return giaCa;
    }

    public void setGiaCa(long giaCa) {
        this.giaCa = giaCa;
    }

    public String getHinhCa() {
        return hinhCa;
    }

    public void setHinhCa(String hinhCa) {
        this.hinhCa = hinhCa;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
