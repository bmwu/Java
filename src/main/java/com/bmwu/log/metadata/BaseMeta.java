package com.bmwu.log.metadata;

/**
 * Created by michael on 8/24/17.
 */
public class BaseMeta {
    protected Long timestamp = 0L;
    protected String id = "";
    protected String rid = "";
    protected String key = "";


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.getTimestamp() != null) {
            result.append("\"timestamp\":" + this.getTimestamp() + ",");
        }
        if (this.getId() != null) {
            result.append("\"id\":\"" + this.getId() + "\",");
        }
        if (this.getRid() != null) {
            result.append("\"rid\":\"" + this.getRid() + "\",");
        }
        if (this.getKey() != null) {
            result.append("\"key\":\"" + this.getKey() + "\"");
        }
        return result.toString();
    }
}
