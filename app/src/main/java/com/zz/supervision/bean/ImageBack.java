package com.zz.supervision.bean;

public class ImageBack {
    String id;
    String path;
    String base64 ;
    String modelId;
    String type;

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getId() {
        return id;
    }

    public String getBase64() {
        return base64;
    }

    public String getModelId() {
        return modelId;
    }

    public String getType() {
        return type;
    }
}
