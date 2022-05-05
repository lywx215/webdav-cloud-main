package com.github.zxbu.webdavteambition.model.result;

import java.util.List;

public class TFileListResult<T> {
    private List<T> fileList;
    private List<T> folderList;
    private Integer count;
    private Integer fileListSize;

    public List<T> getFileList() {
        return fileList;
    }

    public void setFileList(List<T> fileList) {
        this.fileList = fileList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFileListSize() {
        return fileListSize;
    }

    public void setFileListSize(Integer fileListSize) {
        this.fileListSize = fileListSize;
    }

    public List<T> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<T> folderList) {
        this.folderList = folderList;
    }
}
