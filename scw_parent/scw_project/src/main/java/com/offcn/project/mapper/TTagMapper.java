package com.offcn.project.mapper;

import com.offcn.project.po.TTag;
import com.offcn.project.po.TTagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTagMapper {
    long countByExample(TTagExample example);

    int deleteByExample(TTagExample example);

    int insert(TTag record);

    int insertSelective(TTag record);

    List<TTag> selectByExample(TTagExample example);

    int updateByExampleSelective(@Param("record") TTag record, @Param("example") TTagExample example);

    int updateByExample(@Param("record") TTag record, @Param("example") TTagExample example);
}