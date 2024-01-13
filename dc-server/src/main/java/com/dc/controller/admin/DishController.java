package com.dc.controller.admin;

import com.dc.dto.DishDTO;
import com.dc.dto.DishPageQueryDTO;
import com.dc.entity.Dish;
import com.dc.result.PageResult;
import com.dc.result.Result;
import com.dc.service.DishService;
import com.dc.vo.DishVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @GetMapping("/list")
    public Result<List<Dish>> getByCategoryId(Long categoryId) {
        List<Dish> dishList = dishService.getByCategoryId(categoryId);
        return Result.success(dishList);
    }

    @PostMapping
    public Result insert(@RequestBody DishDTO dishDTO) {
        dishService.insert(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result activateOrDeactivate(@PathVariable Integer status, Long id) {
        dishService.activateOrDeactivate(status, id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteById(@RequestParam List<Long> ids) {
        dishService.deleteByIds(ids);
        return Result.success();
    }

}
