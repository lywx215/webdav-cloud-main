package com.github.zxbu.webdavteambition.model.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.zxbu.webdavteambition.model.FileType;

import java.util.Date;
import java.util.Objects;

public class TFile {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private String fileCata;
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastOpTime;
    private String md5;
    private String mediaType;
    private String name;
    private String rev;
    private Long size;
    private String starLabel;
    //文件夹选项
    private Long fileCount;
    private Long fileListSize;
    private String parentId;
    private String type;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFileCata() {
        return fileCata;
    }

    public void setFileCata(String fileCata) {
        this.fileCata = fileCata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLastOpTime() {
        return lastOpTime;
    }

    public void setLastOpTime(Date lastOpTime) {
        this.lastOpTime = lastOpTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getStarLabel() {
        return starLabel;
    }

    public void setStarLabel(String starLabel) {
        this.starLabel = starLabel;
    }

    public Long getFileCount() {
        return fileCount;
    }

    public void setFileCount(Long fileCount) {
        this.fileCount = fileCount;
    }

    public Long getFileListSize() {
        return fileListSize;
    }

    public void setFileListSize(Long fileListSize) {
        this.fileListSize = fileListSize;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
