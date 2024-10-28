package com.koifarmshop.model;

public class FishKind {
    int id;
    String tenCa;
    String hinhAnh;

    public FishKind(String tenCa, String hinhAnh) {
        this.tenCa = tenCa;
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

    public String getHinhAnh() {
        return hinhAnh;
    }
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
