package com.offcn.project.mapper;

import com.offcn.project.po.TProjectImages;
import com.offcn.project.po.TProjectImagesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProjectImagesMapper {
    long countByExample(TProjectImagesExample example);

    int deleteByExample(TProjectImagesExample example);

    int insert(TProjectImages record);

    int insertSelective(TProjectImages record);

    List<TProjectImages> selectByExample(TProjectImagesExample example);

    int updateByExampleSelective(@Param("record") TProjectImages record, @Param("example") TProjectImagesExample example);

    int updateByExample(@Param("record") TProjectImages record, @Param("example") TProjectImagesExample example);
}