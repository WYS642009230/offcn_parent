package com.offcn.project.mapper;

import com.offcn.project.po.TType;
import com.offcn.project.po.TTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTypeMapper {
    long countByExample(TTypeExample example);

    int deleteByExample(TTypeExample example);

    int insert(TType record);

    int insertSelective(TType record);

    List<TType> selectByExample(TTypeExample example);

    int updateByExampleSelective(@Param("record") TType record, @Param("example") TTypeExample example);

    int updateByExample(@Param("record") TType record, @Param("example") TTypeExample example);
}