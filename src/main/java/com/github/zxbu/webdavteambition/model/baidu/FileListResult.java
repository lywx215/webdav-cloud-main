package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FileListResult<T> {
    @JsonProperty("errno")
    private Integer errno;
    @JsonProperty("guid_info")
    private String guidInfo;
    @JsonProperty("list")
    private List<T> list;
    @JsonProperty("request_id")
    private Long requestId;
    @JsonProperty("guid")
    private Integer guid;
}
