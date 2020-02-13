package com.bmwu.logistics.utils;

import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/23
 */
public class XStreamNameCoder extends XmlFriendlyNameCoder {
    public XStreamNameCoder() {
        super("_-", "_");
    }
}
