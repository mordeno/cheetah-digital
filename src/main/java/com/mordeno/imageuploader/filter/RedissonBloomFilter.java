package com.mordeno.imageuploader.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedissonBloomFilter {

    private final RedissonClient redissonClient;
    private static final Integer SIZE = 100_000;
    private static final Double PERCENTAGE = 0.01;
    private RBloomFilter<Integer> bloomFilter;

    public RBloomFilter<Integer> createFilter() {
        if (bloomFilter == null) {
            bloomFilter = redissonClient.getBloomFilter("imageFilter");
            bloomFilter.tryInit(SIZE, PERCENTAGE);
        }
        return bloomFilter;
    }

    public RBloomFilter<Integer> getBloomFilter() {
        return bloomFilter;
    }
}
