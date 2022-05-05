package com.github.zxbu.webdavteambition.model.baidu;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Dlink {
    private Integer category;
    private String dlink;
    private String filename;
    private Long fsId;
    private Integer isdir;
    private String md5;
    private Long operId;
    private String path;
    private Integer serverCtime;
    private Integer serverMtime;
    private Integer size;
}
