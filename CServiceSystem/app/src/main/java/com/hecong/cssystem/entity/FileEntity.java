package com.hecong.cssystem.entity;

public class FileEntity {

    /**
     * type : text/plain
     * size : 178
     * name : USERNAME.txt
     * fileUrl : FgEjdz1hH31cMdaQxhmpSAF2VsR7.txt
     */

    private String type;
    private int size;
    private String name;
    private String fileUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
