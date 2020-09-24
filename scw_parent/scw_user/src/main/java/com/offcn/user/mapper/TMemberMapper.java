package com.offcn.user.mapper;

import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMemberMapper {
    long countByExample(TMemberExample example);

    int deleteByExample(TMemberExample example);

    int insert(TMember record);

    int insertSelective(TMember record);

    List<TMember> selectByExample(TMemberExample example);

    int updateByExampleSelective(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByExample(@Param("record") TMember record, @Param("example") TMemberExample example);
}