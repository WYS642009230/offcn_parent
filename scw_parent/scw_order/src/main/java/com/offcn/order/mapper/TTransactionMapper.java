package com.offcn.order.mapper;

import com.offcn.order.po.TTransaction;
import com.offcn.order.po.TTransactionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTransactionMapper {
    long countByExample(TTransactionExample example);

    int deleteByExample(TTransactionExample example);

    int insert(TTransaction record);

    int insertSelective(TTransaction record);

    List<TTransaction> selectByExample(TTransactionExample example);

    int updateByExampleSelective(@Param("record") TTransaction record, @Param("example") TTransactionExample example);

    int updateByExample(@Param("record") TTransaction record, @Param("example") TTransactionExample example);
}