package com.offcn.user.mapper;

import com.offcn.user.pojo.TMemberProjectFollow;
import com.offcn.user.pojo.TMemberProjectFollowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMemberProjectFollowMapper {
    long countByExample(TMemberProjectFollowExample example);

    int deleteByExample(TMemberProjectFollowExample example);

    int insert(TMemberProjectFollow record);

    int insertSelective(TMemberProjectFollow record);

    List<TMemberProjectFollow> selectByExample(TMemberProjectFollowExample example);

    int updateByExampleSelective(@Param("record") TMemberProjectFollow record, @Param("example") TMemberProjectFollowExample example);

    int updateByExample(@Param("record") TMemberProjectFollow record, @Param("example") TMemberProjectFollowExample example);
}