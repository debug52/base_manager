package com.debug52.utils.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author debug52
 * @date 2022年04月14日 13:58
 */

@Data
@ApiModel
public class BaseSelectBO {

    @ApiModelProperty("查询ID")
    private Integer id;
}
