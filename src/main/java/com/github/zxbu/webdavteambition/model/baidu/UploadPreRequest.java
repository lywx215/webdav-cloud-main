package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UploadPreRequest {
    private String path;
    private Long size;
    private Integer isdir;
    private String block_list;
    private Integer autoinit;
    private Integer rtype;
    private String uploadid;
    @JsonProperty("content-md5")
    private String content_md5;
    @JsonProperty("slice-md5")
    private String slice_md5;
    private String local_ctime;
    private String local_mtime;
}
