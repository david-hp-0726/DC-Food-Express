package com.dc.mapper;

import com.dc.annotation.AutoFill;
import com.dc.dto.CategoryDTO;
import com.dc.dto.CategoryPageQueryDTO;
import com.dc.entity.Category;
import com.dc.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(Category category);

    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    @AutoFill(OperationType.UPDATE)
    void activateOrDeactivate(Short status, Long id);

    void delete(Long id);

    List<Category> list(String type);
}
