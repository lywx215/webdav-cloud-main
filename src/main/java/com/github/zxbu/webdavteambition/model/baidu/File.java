package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class File {
    @JsonProperty("privacy")
    private Integer privacy;
    @JsonProperty("category")
    private Integer category;
    @JsonProperty("unlist")
    private Integer unlist;
    @JsonProperty("fs_id")
    private Long fsId;
    @JsonProperty("server_mtime")
    @JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serverMtime;
    @JsonProperty("server_atime")
    private Integer serverAtime;
    @JsonProperty("server_ctime")
    private Integer serverCtime;
    @JsonProperty("oper_id")
    private Integer operId;
    @JsonProperty("local_mtime")
    private Integer localMtime;
    @JsonProperty("md5")
    private String md5;
    @JsonProperty("share")
    private Integer share;
    @JsonProperty("path")
    private String path;
    @JsonProperty("local_ctime")
    private Integer localCtime;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("server_filename")
    private String serverFilename;
    @JsonProperty("isdir")
    private Integer isdir;
}
