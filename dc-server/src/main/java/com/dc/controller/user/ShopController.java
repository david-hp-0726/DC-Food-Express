package com.dc.controller.user;

import com.dc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    private String KEY = "SHOP_STATUS";

    @GetMapping("/status")
    public Result<Integer> getShopStatus() {
        Integer status = Integer.valueOf((String) redisTemplate.opsForValue().get(KEY));
        return Result.success(status);
    }
}
