package com.offcn.project.mapper;

import com.offcn.project.po.TProjectType;
import com.offcn.project.po.TProjectTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProjectTypeMapper {
    long countByExample(TProjectTypeExample example);

    int deleteByExample(TProjectTypeExample example);

    int insert(TProjectType record);

    int insertSelective(TProjectType record);

    List<TProjectType> selectByExample(TProjectTypeExample example);

    int updateByExampleSelective(@Param("record") TProjectType record, @Param("example") TProjectTypeExample example);

    int updateByExample(@Param("record") TProjectType record, @Param("example") TProjectTypeExample example);
}