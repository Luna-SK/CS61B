package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> test1 = new AListNoResizing<>();
        BuggyAList<Integer> test2 = new BuggyAList<>();
        test1.addLast(4);
        test2.addLast(4);
        test1.addLast(5);
        test2.addLast(5);
        test1.addLast(6);
        test2.addLast(6);
        assertEquals(test1.size(), test2.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(test1.removeLast(), test2.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                // System.out.println("L.addLast(" + randVal + ")");
                // System.out.println("B.addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                // System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                // getLast
                if (L.size() != 0 && B.size() != 0) {
                    // System.out.println("L.getLast(): " + L.getLast());
                    // System.out.println("B.getLast(): " + B.getLast());
                    assertEquals(L.getLast(), B.getLast());
                }
            } else if (operationNumber == 3) {
                // addLast
                if (L.size() != 0 && B.size() != 0) {
                    int temp1 = L.removeLast();
                    int temp2 = B.removeLast();
                    // System.out.println("L.removeLast(): " + temp1);
                    // System.out.println("B.removeLast(): " + temp2);
                    assertEquals(temp1, temp2);
                }
            }
        }
    }
}
