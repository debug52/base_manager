package com.debug52.utils.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author debug52
 * @date 2022年04月14日 13:51
 */

@Slf4j
public class BeanConverUtil {
    /**
     * 单个类转换
     *
     * @param sourceObj
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T conver(Object sourceObj, Class<T> targetClass) {
        if (sourceObj == null || targetClass == null) {
            return null;
        }
        T targetObj = null;
        try {
            targetObj = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("sourceObj:{},targetClass:{}", sourceObj, targetClass, e);
            return null;
        }
        BeanUtils.copyProperties(sourceObj, targetObj);
        return targetObj;
    }

    /**
     * List之间转换
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> converList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList) || targetClass == null) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(sourceObj -> conver(sourceObj, targetClass)).collect(Collectors.toList());
    }

}
