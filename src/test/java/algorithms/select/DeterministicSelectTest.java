package algorithms.select;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    public void testSelectMiddle() {
        int[] arr = {7, 10, 4, 3, 20, 15};
        assertEquals(7, DeterministicSelect.select(arr, 2));
    }

    @Test
    public void testSelectSmallest() {
        int[] arr = {7, 10, 4, 3, 20, 15};
        assertEquals(3, DeterministicSelect.select(arr, 0));
    }

    @Test
    public void testSelectLargest() {
        int[] arr = {7, 10, 4, 3, 20, 15};
        assertEquals(20, DeterministicSelect.select(arr, arr.length - 1));
    }

    @Test
    public void testSingleElement() {
        int[] arr = {5};
        assertEquals(5, DeterministicSelect.select(arr, 0));
    }

    @Test
    public void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(null, 0));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{}, 0));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1, 2, 3}, -1));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1, 2, 3}, 5));
    }
}
