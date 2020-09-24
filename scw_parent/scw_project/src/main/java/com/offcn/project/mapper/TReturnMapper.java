package com.offcn.project.mapper;

import com.offcn.project.po.TReturn;
import com.offcn.project.po.TReturnExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TReturnMapper {
    long countByExample(TReturnExample example);

    int deleteByExample(TReturnExample example);

    int insert(TReturn record);

    int insertSelective(TReturn record);

    List<TReturn> selectByExample(TReturnExample example);

    int updateByExampleSelective(@Param("record") TReturn record, @Param("example") TReturnExample example);

    int updateByExample(@Param("record") TReturn record, @Param("example") TReturnExample example);
}