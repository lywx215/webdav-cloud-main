package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MultiMediaResult<T> {
    @JsonProperty("cursor")
    private Integer cursor;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("errno")
    private Integer errno;
    @JsonProperty("has_more")
    private Integer hasMore;
    @JsonProperty("list")
    private List<T> list;
    @JsonProperty("request_id")
    private String requestId;
}
