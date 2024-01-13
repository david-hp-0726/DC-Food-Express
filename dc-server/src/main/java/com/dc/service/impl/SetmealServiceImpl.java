package com.dc.service.impl;

import com.dc.annotation.AutoFill;
import com.dc.constant.MessageConstant;
import com.dc.constant.StatusConstant;
import com.dc.dto.SetmealDTO;
import com.dc.dto.SetmealPageQueryDTO;
import com.dc.entity.Setmeal;
import com.dc.entity.SetmealDish;
import com.dc.enumeration.OperationType;
import com.dc.exception.DeletionNotAllowedException;
import com.dc.mapper.SetmealDishMapper;
import com.dc.mapper.SetmealMapper;
import com.dc.result.PageResult;
import com.dc.service.SetmealService;
import com.dc.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @AutoFill(OperationType.INSERT)
    @Transactional
    public void insert(SetmealDTO setmealDTO) {
        // Add setmeal
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);

        // Add setmeal-dish relations
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishRelations = setmealDTO.getSetmealDishes();
        setmealDishRelations.forEach(relation -> relation.setSetmealId(setmealId));
        setmealDishMapper.insertBatch(setmealDishRelations);
    }
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmealVOPage = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(setmealVOPage.getTotal(), setmealVOPage.getResult());
    }

    @Transactional
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> setmealDishRelations = setmealDishMapper.getBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishRelations);
        return setmealVO;
    }

    @Transactional
    @AutoFill(OperationType.UPDATE)
    public void update(SetmealDTO setmealDTO) {
        // Update setmeal
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        // Update setmeal-dish relations
        List<SetmealDish> setmealDishRelations = setmealDTO.getSetmealDishes();
        Long setmealId = setmealDTO.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);
        if (setmealDishRelations != null && setmealDishRelations.size() > 0) {
            setmealDishRelations.forEach(relation -> relation.setSetmealId(setmealId));
            setmealDishMapper.insertBatch(setmealDishRelations);
        }
    }

    public void activateOrDeactivate(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    @Transactional
    public void delete(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        ids.forEach(id -> {
            setmealMapper.delete(id);
            setmealDishMapper.deleteBySetmealId(id);
        });
    }
}
