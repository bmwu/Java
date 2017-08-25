package com.bmwu.log.metadata;

/**
 * Created by michael on 8/24/17.
 */
public class LogData<T> {
    BaseMeta meta;

    T business;

    String logType;

    public BaseMeta getMeta() {
        return meta;
    }

    public void setMeta(BaseMeta meta) {
        this.meta = meta;
    }

    public T getBusiness() {
        return business;
    }

    public void setBusiness(T business) {
        this.business = business;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("\"meta\":{" + this.getMeta().toString() + "},");
        result.append("\"business\":{" + this.getBusiness().toString() + "}");
        if (this.getLogType() != null) {
            result.append(",\"logType\":" + this.getLogType());
        }
        result.append("}");
        return result.toString();
    }
}

