package com.bmwu.spring;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by michael on 7/16/17.
 */
public class JavaCollection {

    private List addressList;
    private Set addressSet;
    private Map addressMap;
    private Properties addressProperties;

    public List getAddressList() {
        System.out.println("List Elements :"  + addressList);
        return addressList;
    }

    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public Set getAddressSet() {
        System.out.println("Set Elements :"  + addressSet);
        return addressSet;
    }

    public void setAddressSet(Set addressSet) {
        this.addressSet = addressSet;
    }

    public Map getAddressMap() {
        System.out.println("Map Elements :"  + addressMap);
        return addressMap;
    }

    public void setAddressMap(Map addressMap) {
        this.addressMap = addressMap;
    }

    public Properties getAddressProperties() {
        System.out.println("Property Elements :"  + addressProperties);
        return addressProperties;
    }

    public void setAddressProperties(Properties addressProperties) {
        this.addressProperties = addressProperties;
    }
}
