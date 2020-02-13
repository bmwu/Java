package com.bmwu.comparator;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author bmwu.hz
 * @date 2018-05-24
 */
public class ComparatorTest {

    @Test
    public void test() {



        Set<Completion> set = genCompletion();
        Completion[] arr = new Completion[set.size()];
        set.toArray(arr);

        Arrays.sort(arr, new CompletionComparator());
        System.out.println("1");

    }

    private Set<Completion> genCompletion() {
        Set<Completion> set = new HashSet<>();
        for (int i = 0; i< 10;i++) {
            Completion completion = new Completion();
            completion.setMatchedCount(new Random().nextInt(10));
            completion.setWarehouseIndex(new Random().nextInt(100));
            set.add(completion);
        }
        return set;
    }
}
