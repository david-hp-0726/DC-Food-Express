package com.dc.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

    public Integer getCountByCategoryId(Long id);
}
