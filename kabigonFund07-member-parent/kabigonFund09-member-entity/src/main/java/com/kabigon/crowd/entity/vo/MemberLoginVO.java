package com.kabigon.crowd.entity.vo;


import java.io.Serializable;

public class MemberLoginVO implements Serializable {
    private static  final long serialVersionUID=1L;

    private Integer id;

    private String username;

    private String email;

    public MemberLoginVO() {
    }

    public MemberLoginVO(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
