package com.kabigon.entity;

import java.util.ArrayList;
import java.util.List;

public class Auth {
    private Integer id;

    private String name;

    private String title;

    private Integer categoryId;

    private List<Auth> children=new ArrayList<>();
    //控制节点默认是否打开
    private Boolean open=true;

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Auth() {
    }

    public Auth(Integer id, String name, String title, Integer categoryId, List<Auth> children) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.categoryId = categoryId;
        this.children = children;
    }

    public List<Auth> getChildren() {
        return children;
    }

    public void setChildren(List<Auth> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}