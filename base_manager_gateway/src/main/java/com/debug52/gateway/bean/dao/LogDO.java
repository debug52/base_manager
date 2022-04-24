package com.debug52.gateway.bean.dao;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LogDO {
    private Integer id;

    private String remark;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private Integer isDelete;

    private String uri;

    private String ip;

    private String username;

    private String requestMethod;
    private String userAgent;

}