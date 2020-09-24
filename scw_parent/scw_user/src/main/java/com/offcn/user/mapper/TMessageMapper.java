package com.offcn.user.mapper;

import com.offcn.user.pojo.TMessage;
import com.offcn.user.pojo.TMessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMessageMapper {
    long countByExample(TMessageExample example);

    int deleteByExample(TMessageExample example);

    int insert(TMessage record);

    int insertSelective(TMessage record);

    List<TMessage> selectByExample(TMessageExample example);

    int updateByExampleSelective(@Param("record") TMessage record, @Param("example") TMessageExample example);

    int updateByExample(@Param("record") TMessage record, @Param("example") TMessageExample example);
}