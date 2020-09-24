package com.offcn.user.mapper;

import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.pojo.TMemberAddressExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMemberAddressMapper {
    long countByExample(TMemberAddressExample example);

    int deleteByExample(TMemberAddressExample example);

    int insert(TMemberAddress record);

    int insertSelective(TMemberAddress record);

    List<TMemberAddress> selectByExample(TMemberAddressExample example);

    int updateByExampleSelective(@Param("record") TMemberAddress record, @Param("example") TMemberAddressExample example);

    int updateByExample(@Param("record") TMemberAddress record, @Param("example") TMemberAddressExample example);
}