package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Floder {
    @JsonProperty("server_filename")
    private String serverFilename;
    @JsonProperty("privacy")
    private Integer privacy;
    @JsonProperty("category")
    private Integer category;
    @JsonProperty("unlist")
    private Integer unlist;
    @JsonProperty("fs_id")
    private Long fsId;
    @JsonProperty("dir_empty")
    private Integer dirEmpty;
    @JsonProperty("server_atime")
    private Integer serverAtime;
    @JsonProperty("server_ctime")
    private Integer serverCtime;
    @JsonProperty("local_mtime")
    private Integer localMtime;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("isdir")
    private Integer isdir;
    @JsonProperty("share")
    private Integer share;
    @JsonProperty("path")
    private String path;
    @JsonProperty("local_ctime")
    private Integer localCtime;
    @JsonProperty("server_mtime")
    private Integer serverMtime;
    @JsonProperty("empty")
    private Integer empty;
    @JsonProperty("oper_id")
    private Integer operId;
}
