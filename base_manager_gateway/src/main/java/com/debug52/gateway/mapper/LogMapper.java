package com.debug52.gateway.mapper;



import com.debug52.gateway.bean.dao.LogDO;

import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogDO record);

    LogDO selectByPrimaryKey(Integer id);

    List<LogDO> selectAll();

    int updateByPrimaryKey(LogDO record);
}