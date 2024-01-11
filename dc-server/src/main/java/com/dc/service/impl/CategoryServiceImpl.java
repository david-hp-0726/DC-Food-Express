package com.dc.service.impl;

import com.dc.constant.MessageConstant;
import com.dc.context.BaseContext;
import com.dc.dto.CategoryDTO;
import com.dc.dto.CategoryPageQueryDTO;
import com.dc.entity.Category;
import com.dc.exception.DeletionNotAllowedException;
import com.dc.mapper.CategoryMapper;
import com.dc.mapper.DishMapper;
import com.dc.mapper.SetmealMapper;
import com.dc.result.PageResult;
import com.dc.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> categoryPage = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(categoryPage.getTotal(), categoryPage.getResult());
    }

    public List<Category> list(String type) {
        List<Category> categoryList = categoryMapper.list(type);
        return categoryList;
    }

    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(0);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.add(category);
    }

    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    public void delete(Long id) {
        Integer dishCount = dishMapper.getCountByCategoryId(id);
        if (dishCount > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        Integer setmealCount = setmealMapper.getCountByCategoryId(id);
        if (setmealCount > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.delete(id);
    }

    public void activateOrDeactivate(Short status, Long id) {
        categoryMapper.activateOrDeactivate(status, id);
    }
}
