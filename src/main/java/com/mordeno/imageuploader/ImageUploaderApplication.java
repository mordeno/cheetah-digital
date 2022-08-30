package com.mordeno.imageuploader;

import com.mordeno.imageuploader.filter.RedissonBloomFilter;
import com.mordeno.imageuploader.model.Image;
import com.mordeno.imageuploader.service.ImageUploaderService;
import com.mordeno.imageuploader.utils.JsonDataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * An image uploader application that demonstrates queueing, filtering, and saving data using Redis.
 * At startup, data from active.json will be saved to Redis.
 * Also, a bloom filter will be populated with the same dataset.
 * After data initialization, uploadImages() method will be triggered.
 *
 * All sample data in stream.json will be pushed to a queue one by one.
 * At the same time, a consumer listens to that event and will consume the data.
 * Each data will be checked first in bloom filter. If it exists, it will check again in Redis storage.
 * If it does not exists, it will increment the total count for 'not matched' data.
 *
 * Data matched will print 'Image ID {id} already exists!'
 * Data not matched will print 'Image ID {id} uploaded successfully!'
 *
 * Expected results:
 * Total matched - 2355
 * Total not matched - 2645
 */


@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ImageUploaderApplication {

	private final RedissonClient redissonClient;
	private final ImageUploaderService imageUploaderService;
	private final RedissonBloomFilter redissonBloomFilter;

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			redissonClient.getKeys().flushall();
			initializeData();
			uploadImages();
		};
	}

	private void initializeData() {
		log.info("Initializing data");
		List<Image> images = JsonDataUtil.getJsonImageData("/data/active.json");
		populateRedisStorage(images);
		populateBloomFilter(images);
		log.info("Initializing data finished");
	}

	private void uploadImages() {
		log.info("Uploading images");
		List<Image> images = JsonDataUtil.getJsonImageData("/data/stream.json");
		imageUploaderService.upload(images);
	}

	private void populateRedisStorage(List<Image> images) {
		RMap<Integer, Image> rmImages = redissonClient.getMap("images");
		images.forEach(image -> rmImages.fastPut(image.getId(), image));
		log.info("Redis storage populated with {} entries", images.size());
	}

	private void populateBloomFilter(List<Image> images) {
		RBloomFilter<Integer> imageFilter = redissonBloomFilter.createFilter();
		images.stream().map(Image::getId).forEach(imageFilter::add);
		log.info("Bloom filter populated with {} entries", images.size());
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageUploaderApplication.class, args);
	}
}
