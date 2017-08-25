package com.bmwu.log.metadata;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by michael on 8/24/17.
 */
public class RequestMetaBase {

    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private Long field5;
    /**客户端IP*/
    private String field6;
    private Map map1;
    private Map map2;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public Long getField5() {
        return field5;
    }

    public void setField5(Long field5) {
        this.field5 = field5;
    }

    public String getField6() {
        return field6;
    }

    public void setField6(String field6) {
        this.field6 = field6;
    }

    public Map getMap1() {
        return map1;
    }

    public void setMap1(Map map1) {
        this.map1 = map1;
    }

    public Map getMap2() {
        return map2;
    }

    public void setMap2(Map map2) {
        this.map2 = map2;
    }

    protected static final Gson gson = new Gson();

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.getField1() != null) {
            result.append("\"field1\":\"" + this.getField1() + "\",");
        }
        if (this.getField2() != null) {
            result.append("\"field2\":\"" + this.getField2() + "\",");
        }
        if (this.getField3() != null) {
            result.append("\"field3\":\"" + this.getField3() + "\",");
        }
        if (this.getField4() != null) {
            result.append("\"field4\":\"" + this.getField4() + "\",");
        }
        if (this.getField5() != null) {
            result.append("\"field5\":" + this.getField5() + ",");
        }
        if (this.getField6() != null) {
            result.append("\"field6\":\"" + this.getField6() + "\",");
        }
        if (this.getMap1() != null) {
            result.append("\"map1\":" + gson.toJson(this.getMap1()) + ",");
        }
        if (this.getMap2() != null) {
            result.append("\"map2\":" + gson.toJson(this.getMap2()) + "\",");
        }

        if (result.toString().endsWith(",")) {
            return result.substring(0, result.length() -1);
        }
        return result.toString();
    }
}
