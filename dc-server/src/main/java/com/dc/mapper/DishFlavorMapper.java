package com.dc.mapper;

import com.dc.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insertBatch(List<DishFlavor> dishFlavors);

    void deleteByDishId(Long dishId);

    List<DishFlavor> getByDishId(Long id);
}
