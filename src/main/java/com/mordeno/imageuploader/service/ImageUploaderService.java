package com.mordeno.imageuploader.service;

import com.mordeno.imageuploader.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUploaderService {

    private final ImageUploadQueueProducer imageUploadQueueProducer;

    public void upload(List<Image> toUpload) {
        toUpload.forEach(imageUploadQueueProducer::uploadImage);
    }
}
