package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UploadPreInfo {
    private Integer errno;
    @JsonProperty("return_type")
    private Integer returnType;
    @JsonProperty("block_list")
    private Integer[] blockList;
    private String uploadid;
    @JsonProperty("request_id")
    private Long requestId;
}
