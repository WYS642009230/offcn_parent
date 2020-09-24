package com.offcn.project.mapper;

import com.offcn.project.po.TProject;
import com.offcn.project.po.TProjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProjectMapper {
    long countByExample(TProjectExample example);

    int deleteByExample(TProjectExample example);

    int insert(TProject record);

    int insertSelective(TProject record);

    List<TProject> selectByExample(TProjectExample example);

    int updateByExampleSelective(@Param("record") TProject record, @Param("example") TProjectExample example);

    int updateByExample(@Param("record") TProject record, @Param("example") TProjectExample example);
}