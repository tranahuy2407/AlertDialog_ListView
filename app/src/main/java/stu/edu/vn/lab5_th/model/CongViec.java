package stu.edu.vn.lab5_th.model;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;

import stu.edu.vn.lab5_th.util.FormatUtil;

public class CongViec implements Serializable {
    private String ten;
    private Date han;

    public CongViec() {
    }

    public CongViec(String ten, Date han) {
        this.ten = ten;
        this.han = han;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getHan() {
        return han;
    }

    public void setHan(Date han) {
        this.han = han;
    }

    @Override
    public String toString() {
        return ten + "-" + FormatUtil.formatDateTime(this.han);
    }
}
