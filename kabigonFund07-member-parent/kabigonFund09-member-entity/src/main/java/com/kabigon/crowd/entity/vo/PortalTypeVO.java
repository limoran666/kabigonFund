package com.kabigon.crowd.entity.vo;


import java.util.List;

public class PortalTypeVO {
    private Integer id;
    private String name;
    private String remark;
    private List<PortalTypeVO> portalTypeVOList;

    public PortalTypeVO() {
    }

    public PortalTypeVO(Integer id, String name, String remark, List<PortalTypeVO> portalTypeVOList) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.portalTypeVOList = portalTypeVOList;
    }

    public List<PortalTypeVO> getPortalTypeVOList() {
        return portalTypeVOList;
    }

    public void setPortalTypeVOList(List<PortalTypeVO> portalTypeVOList) {
        this.portalTypeVOList = portalTypeVOList;
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
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
