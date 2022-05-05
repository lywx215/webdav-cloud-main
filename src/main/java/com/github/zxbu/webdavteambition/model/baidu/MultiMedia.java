package com.github.zxbu.webdavteambition.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.zxbu.webdavteambition.util.LongToDateDeserializer;
import com.github.zxbu.webdavteambition.util.DateToLongSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@Data
public class MultiMedia {
    @JsonProperty("category")
    private Integer category;
    @JsonProperty("dlink")
    private String dlink;
    @JsonProperty("fs_id")
    private Long fsId;
    @JsonProperty("isdir")
    private Integer isdir;
    @JsonProperty("local_ctime")
    //@JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LongToDateDeserializer.class)
    private Date localCtime;
    @JsonProperty("local_mtime")
    //@JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LongToDateDeserializer.class)
    private Date localMtime;
    @JsonProperty("md5")
    private String md5;
    @JsonProperty("path")
    private String path;
    @JsonProperty("server_ctime")
    //@JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LongToDateDeserializer.class)
    private Date serverCtime;
    @JsonProperty("server_filename")
    private String serverFilename;
    @JsonProperty("server_mtime")
    //@JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = DateToLongSerializer.class)
    @JsonDeserialize(using = LongToDateDeserializer.class)
    private Date serverMtime;
    @JsonProperty("size")
    private Integer size;
}
