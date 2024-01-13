package com.dc.mapper;

import com.dc.annotation.AutoFill;
import com.dc.dto.DishPageQueryDTO;
import com.dc.entity.Dish;
import com.dc.enumeration.OperationType;
import com.dc.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface DishMapper {
    public Integer getCountByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    Long insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    Dish getById(Long id);

    void deleteById(Long id);

    void update(Dish dish);

    List<Dish> getByCategoryId(Long categoryId);
}
