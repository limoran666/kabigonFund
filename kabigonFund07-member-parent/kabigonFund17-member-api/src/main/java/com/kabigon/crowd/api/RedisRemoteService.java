package com.kabigon.crowd.api;

import com.kabigon.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@FeignClient("kabigon-crowd-redis")
public interface RedisRemoteService {
    @RequestMapping("/set/Redis/Key/Value/Remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key,@RequestParam("value") String value);
    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("time") long time, @RequestParam("timeUnit") TimeUnit timeUnit);


    @RequestMapping("/get/redis/string/value/by/key")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);
    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key);
}
