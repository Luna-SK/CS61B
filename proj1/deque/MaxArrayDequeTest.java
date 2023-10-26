package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void integerTest() {
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        MaxArrayDeque<Integer> array = new MaxArrayDeque<>(c);
        array.addLast(1);
        array.addLast(3);
        array.addLast(8);
        array.addLast(11);
        array.addLast(5);
        array.printDeque();
        assertEquals(array.max().intValue(), 11);
    }
}
