package com.debug52.utils.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础响应封装类
 * <p>json数据一个常用的格式</p>
 * @author Javid Xi
 * @version 1.0
 */
@Data
@ApiModel
public class BaseRespVO<T> {
    /**
     * 自定义数据
     */
    @ApiModelProperty("泛型类，封装核心返回值数据")
    T data;

    /**
     * 自定义消息
     */
    @ApiModelProperty("返回值消息")
    String msg;

    /**
     * 总条数
     */
    @ApiModelProperty("数据总条数")
    Integer count;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "返回值状态码",name = "status")
    long status;

    /**
     * 响应成功
     * @return BaseRespVo
     */
    public static BaseRespVO ok(){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setStatus(200);
        baseRespVo.setMsg("成功");
        return baseRespVo;
    }


    /**
     * 响应成功 + data
     * @param data 自定义数据
     * @return BaseRespVo
     */
    public static BaseRespVO ok(Object data){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setData(data);
        baseRespVo.setStatus(200);
        baseRespVo.setMsg("成功");
        return baseRespVo;
    }

    /**
     * 响应成功 + data
     * @param data 自定义数据
     * @return BaseRespVo
     */
    public static BaseRespVO ok(Object data, Integer count){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setData(data);
        baseRespVo.setStatus(200);
        baseRespVo.setCount(count);
        baseRespVo.setMsg("成功");
        return baseRespVo;
    }

    /**
     * 响应成功 + msg
     * @return BaseRespVo
     */
    public static BaseRespVO ok(String msg){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setStatus(200);
        baseRespVo.setMsg(msg);
        return baseRespVo;
    }

    /**
     * 响应成功
     * @param data 自定义数据
     * @param msg 自定义消息
     * @return BaseRespVo
     */
    public static BaseRespVO ok(Object data, String msg){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setData(data);
        baseRespVo.setStatus(200);
        baseRespVo.setMsg(msg);
        return baseRespVo;
    }

    /**
     * 响应失败
     * @return BaseRespVo
     */
    public static BaseRespVO fail(){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setStatus(500);
        baseRespVo.setMsg("失败");
        return baseRespVo;
    }

    /**
     * 响应失败
     * @param msg 自定义消息
     * @return BaseRespVo
     */
    public static BaseRespVO fail(String msg){
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setStatus(500);
        baseRespVo.setMsg(msg);
        return baseRespVo;
    }

    /**
     * 相应失败
     * @param errmsg 自定义消息
     * @param errno 错误代码
     * @return BaseRespVO
     */
    public static BaseRespVO fail (long errno, String errmsg) {
        BaseRespVO<Object> baseRespVo = new BaseRespVO<>();
        baseRespVo.setStatus(errno);
        baseRespVo.setMsg(errmsg);
        return baseRespVo;
    }
}
