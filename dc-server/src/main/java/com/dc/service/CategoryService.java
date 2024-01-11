package com.dc.service;

import com.dc.dto.CategoryDTO;
import com.dc.dto.CategoryPageQueryDTO;
import com.dc.entity.Category;
import com.dc.result.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void add(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(Long id);

    void activateOrDeactivate(Short status, Long id);

    List<Category> list(String type);
}
