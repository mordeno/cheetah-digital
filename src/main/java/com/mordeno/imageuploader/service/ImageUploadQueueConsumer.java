package com.mordeno.imageuploader.service;

import com.github.sonus21.rqueue.annotation.RqueueListener;
import com.mordeno.imageuploader.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUploadQueueConsumer {

    private final AtomicInteger matched = new AtomicInteger(0);
    private final ImageUploadValidator imageUploadValidator;
    private static final String IMAGE_UPLOAD_QUEUE = "image-upload-queue";

    @RqueueListener(value = IMAGE_UPLOAD_QUEUE)
    public void uploadImage(Image image) {
        Integer imageId = image.getId();

        if (imageUploadValidator.existsInFilter(imageId)) {
            if (imageUploadValidator.existsInStorage(imageId)) {
                log.info("Image ID {} already exists!", imageId);
            } else {
                log.info("Image ID {} uploaded successfully! {} total images uploaded.",
                        imageId, matched.incrementAndGet());
            }
        } else {
            log.info("Image ID {} uploaded successfully! {} total images uploaded.",
                    imageId, matched.incrementAndGet());
        }
    }
}
