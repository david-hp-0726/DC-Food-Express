package com.dc.mapper;

import com.dc.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    public List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    void insertBatch(List<SetmealDish> setmealDishRelations);

    List<SetmealDish> getBySetmealId(Long setmealId);

    void deleteBySetmealId(Long setmealId);
}
