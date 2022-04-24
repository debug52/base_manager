package com.debug52.utils.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 页码封装基础类
 * @author debug52
 * @version 1.0
 **/
@Data
@ApiModel
public class PageInfoBO {
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页数据量")
    private Integer pageSize;
}
