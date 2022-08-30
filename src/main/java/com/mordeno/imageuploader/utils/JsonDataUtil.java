package com.mordeno.imageuploader.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mordeno.imageuploader.model.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDataUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<Image> getJsonImageData(String src) {
        List<Image> images = new ArrayList<>();
        TypeReference<List<Image>> typeReference = new TypeReference<List<Image>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream(src);
        try {
            images = MAPPER.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }
}
