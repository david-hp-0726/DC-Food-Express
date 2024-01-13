package com.dc.service.impl;

import com.dc.annotation.AutoFill;
import com.dc.constant.MessageConstant;
import com.dc.constant.StatusConstant;
import com.dc.dto.DishDTO;
import com.dc.dto.DishPageQueryDTO;
import com.dc.entity.Dish;
import com.dc.entity.DishFlavor;
import com.dc.enumeration.OperationType;
import com.dc.exception.DeletionNotAllowedException;
import com.dc.mapper.DishFlavorMapper;
import com.dc.mapper.DishMapper;
import com.dc.mapper.SetmealDishMapper;
import com.dc.result.PageResult;
import com.dc.service.DishService;
import com.dc.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> dishVOPage = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(dishVOPage.getTotal(), dishVOPage.getResult());
    }

    @AutoFill(OperationType.INSERT)
    @Transactional
    public void insert(DishDTO dishDTO) {
        // insert dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        // insert dish flavor
        Long dishId = dish.getId();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if (dishFlavors != null && dishFlavors.size() > 0) {
            dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(dishFlavors);
        }
    }

    @Transactional
    public void deleteByIds(List<Long> ids) {
        // Check if the dish is on sale
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // Check if the dish is associated with setmeals
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);

        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // Delete dish
        for (Long id : ids) {
            dishMapper.deleteById(id);
            // Delete flavors associated with dish
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    public void activateOrDeactivate(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }

    @Transactional
    @AutoFill(OperationType.UPDATE)
    public void update(DishDTO dishDTO) {
        // Update dish in the database
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // Update flavors
        dishFlavorMapper.deleteByDishId(dish.getId());

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        Long dishId = dishDTO.getId();
        if (dishFlavors != null && dishFlavors.size() > 0) {
            dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(dishFlavors);
        }
    }

    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    public List<Dish> getByCategoryId(Long categoryId) {
        List<Dish> dishList = dishMapper.getByCategoryId(categoryId);
        return dishList;
    }
}
