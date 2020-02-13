package com.bmwu.comparator;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bmwu.hz
 * @date 2018-05-24
 */
@Data
public class Completion implements Serializable {

    private Integer matchedCount;

    private Integer warehouseIndex;
}
