package com.dc.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    public Integer getCountByCategoryId(Long categoryId);
}
