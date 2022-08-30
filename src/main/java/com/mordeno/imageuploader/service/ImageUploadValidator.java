package com.mordeno.imageuploader.service;

import com.mordeno.imageuploader.filter.RedissonBloomFilter;
import com.mordeno.imageuploader.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUploadValidator {

    private final RedissonClient redissonClient;
    private final RedissonBloomFilter redissonBloomFilter;

    public boolean existsInFilter(Integer id) {
        return redissonBloomFilter.getBloomFilter().contains(id);
    }

    public boolean existsInStorage(Integer id) {
        RMap<Integer, Image> rmImages = redissonClient.getMap("images");
        return rmImages.containsKey(id);
    }
}
