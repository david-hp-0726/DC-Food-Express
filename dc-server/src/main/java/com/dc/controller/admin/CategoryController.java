package com.dc.controller.admin;

import com.dc.dto.CategoryDTO;
import com.dc.dto.CategoryPageQueryDTO;
import com.dc.entity.Category;
import com.dc.result.PageResult;
import com.dc.result.Result;
import com.dc.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    public Result<List<Category>> list(String type) {
        List<Category> categoryList = categoryService.list(type);
        return Result.success(categoryList);
    }

    @PostMapping
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        categoryService.insert(categoryDTO);
        return Result.success();
    }

    @PostMapping ("/status/{status}")
    public Result activateOrDeactivate(@PathVariable Short status, Long id) {
        categoryService.activateOrDeactivate(status, id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }


    @DeleteMapping
    public Result delete(Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
