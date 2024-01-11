package com.dc.mapper;

import com.dc.dto.CategoryDTO;
import com.dc.dto.CategoryPageQueryDTO;
import com.dc.entity.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void add(Category category);

    void update(Category category);

    void activateOrDeactivate(Short status, Long id);

    void delete(Long id);

    List<Category> list(String type);
}
