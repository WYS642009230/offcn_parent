package com.offcn.user.mapper;

import com.offcn.user.pojo.TMemberCert;
import com.offcn.user.pojo.TMemberCertExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMemberCertMapper {
    long countByExample(TMemberCertExample example);

    int deleteByExample(TMemberCertExample example);

    int insert(TMemberCert record);

    int insertSelective(TMemberCert record);

    List<TMemberCert> selectByExample(TMemberCertExample example);

    int updateByExampleSelective(@Param("record") TMemberCert record, @Param("example") TMemberCertExample example);

    int updateByExample(@Param("record") TMemberCert record, @Param("example") TMemberCertExample example);
}