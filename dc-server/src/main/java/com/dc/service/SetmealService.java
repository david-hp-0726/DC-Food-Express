package com.dc.service;

import com.dc.dto.SetmealDTO;
import com.dc.dto.SetmealPageQueryDTO;
import com.dc.result.PageResult;
import com.dc.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void insert(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO getById(Long id);

    void update(SetmealDTO setmealDTO);

    void activateOrDeactivate(Integer status, Long id);

    void delete(List<Long> ids);
}
