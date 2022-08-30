package com.mordeno.imageuploader.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class Image implements Serializable {
    private static final long serialVersionUID = -3057465488569039568L;
    private Integer albumId;
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
