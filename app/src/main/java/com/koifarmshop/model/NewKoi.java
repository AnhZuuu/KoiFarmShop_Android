package com.koifarmshop.model;

public class NewKoi {
    int id;
    String tenCa;
    String gia;
    String hinhAnh;
    String mota;
    int loai;

    public String getHinhAnh() {
        return hinhAnh;
    }
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTenCa() {
        return tenCa;
    }
    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public String getGia() {
        return gia;
    }
    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }
    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }
    public void setLoai(int loai) {
        this.loai = loai;
    }
}
