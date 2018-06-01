package com.bmwu.comparator;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author bmwu.hz
 * @date 2018-05-24
 */
public class CompletionComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        Integer matchedCount1 = ((Completion)o1).getMatchedCount();
        Integer matchedCount2 = ((Completion)o2).getMatchedCount();

        // 命中的商品多的优先级高
        if (matchedCount1 < matchedCount2) {
            return 1;
        } else if (Objects.equals(matchedCount1, matchedCount2)) {
            Integer warehouseIndex1 = ((Completion)o1).getWarehouseIndex();
            Integer warehouseIndex2 = ((Completion)o2).getWarehouseIndex();
            // index小的优先级高
            if (warehouseIndex1 > warehouseIndex2) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
