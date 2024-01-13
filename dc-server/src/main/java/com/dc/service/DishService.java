package com.dc.service;

import com.dc.dto.DishDTO;
import com.dc.dto.DishPageQueryDTO;
import com.dc.entity.Dish;
import com.dc.result.PageResult;
import com.dc.vo.DishVO;

import java.util.List;

public interface DishService {

    void insert(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteByIds(List<Long> ids);

    void activateOrDeactivate(Integer status, Long id);

    void update(DishDTO dishDTO);

    DishVO getById(Long id);

    List<Dish> getByCategoryId(Long categoryId);
}
