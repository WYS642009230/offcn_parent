package com.offcn.project.mapper;

import com.offcn.project.po.TProjectTag;
import com.offcn.project.po.TProjectTagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProjectTagMapper {
    long countByExample(TProjectTagExample example);

    int deleteByExample(TProjectTagExample example);

    int insert(TProjectTag record);

    int insertSelective(TProjectTag record);

    List<TProjectTag> selectByExample(TProjectTagExample example);

    int updateByExampleSelective(@Param("record") TProjectTag record, @Param("example") TProjectTagExample example);

    int updateByExample(@Param("record") TProjectTag record, @Param("example") TProjectTagExample example);
}