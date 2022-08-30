package com.mordeno.imageuploader.service;

import com.github.sonus21.rqueue.core.RqueueMessageEnqueuer;
import com.mordeno.imageuploader.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUploadQueueProducer {

    private final RqueueMessageEnqueuer rqueueMessageEnqueuer;
    private static final String IMAGE_UPLOAD_QUEUE = "image-upload-queue";

    public void uploadImage(Image image) {
        rqueueMessageEnqueuer.enqueue(IMAGE_UPLOAD_QUEUE, image);
        log.info("Image ID {} pushed to queue", image.getId());
    }
}

