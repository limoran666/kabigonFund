package com.kabigon.crowd.entity.po;

import java.util.Date;

public class OrderPO {
    private Integer id;

    private String orderno;

    private Date orderdate;

    private Integer orderuser;

    private Double ordermoney;

    private Integer orderstatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Integer getOrderuser() {
        return orderuser;
    }

    public void setOrderuser(Integer orderuser) {
        this.orderuser = orderuser;
    }

    public Double getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(Double ordermoney) {
        this.ordermoney = ordermoney;
    }

    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }
}