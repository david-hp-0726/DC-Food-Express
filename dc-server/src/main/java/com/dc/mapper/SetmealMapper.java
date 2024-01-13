package com.dc.mapper;

import com.dc.dto.SetmealPageQueryDTO;
import com.dc.entity.Dish;
import com.dc.entity.Setmeal;
import com.dc.vo.SetmealVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

    public Integer getCountByCategoryId(Long categoryId);

    Long insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    Setmeal getById(Long id);

    void update(Setmeal setmeal);

    void delete(Long id);
}
