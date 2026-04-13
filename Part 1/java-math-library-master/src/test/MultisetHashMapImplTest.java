package test;
import static org.junit.Assert.*;
import org.junit.Test;
import de.tilman_neumann.util.Multiset_HashMapImpl;

public class MultisetHashMapImplTest {
    /// Test add (2 functions)
    @Test
    public void testAdd() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a");
        assertEquals(1, (int)set.get("a"));

        set.add("a");
        assertEquals(2, (int)set.get("a"));
    }
    @Test
    public void testAddWithMultiplicity() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 3);
        assertEquals(3, (int)set.get("a"));

        set.add("a", 0);
        assertEquals(3, (int)set.get("a"));

        set.add("a", -2);
        assertEquals(3, (int)set.get("a"));
    }
    @Test
    public void testAddNegativeMultBug() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);

        int old = set.add("a", -5);

        assertEquals(0, old); // fails (returns 2)
    }
    ///-----------------------------------------------------------------------------------
    /// Test addAll (3 functions)
    @Test
    public void testAddAllMultiset_Null() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);

        set.addAll((Multiset_HashMapImpl<String>) null);

        // should remain unchanged
        assertEquals(2, (int) set.get("a"));
    }
    @Test
    public void testAddAllMultiset_Empty() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();

        set.add("a", 2);
        set.addAll(other);

        assertEquals(2, (int) set.get("a"));
    }
    @Test
    public void testAddAllMultiset_Normal() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();

        set.add("a", 2);
        other.add("a", 3);
        other.add("b", 1);

        set.addAll(other);

        assertEquals(5, (int) set.get("a")); // merged
        assertEquals(1, (int) set.get("b")); // new element
    }
    @Test
    public void testAddAllCollection_Empty() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        java.util.List<String> list = new java.util.ArrayList<>();

        set.addAll(list);

        assertEquals(0, set.totalCount());
    }
    @Test
    public void testAddAllCollection_Normal() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        java.util.List<String> list = java.util.Arrays.asList("a", "a", "b");

        set.addAll(list);

        assertEquals(2, (int) set.get("a"));
        assertEquals(1, (int) set.get("b"));
    }
    @Test
    public void testAddAllArray_Null() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();

        set.addAll((String[]) null);

        assertEquals(0, set.totalCount());
    }
    @Test
    public void testAddAllArray_Normal() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        String[] arr = {"a", "b", "a"};

        set.addAll(arr);

        assertEquals(2, (int) set.get("a"));
        assertEquals(1, (int) set.get("b"));
    }
    ///-----------------------------------------------------------------------------------
    /// Test remove & removeAll (3 functions)
    @Test
    public void testRemove() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);
        set.remove("a");
        assertEquals(1, (int)set.get("a"));
    }
    @Test
    public void testRemoveEmpty() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);
        set.remove("a");
        set.remove("a");
        assertNull(set.get("a"));
        set.remove("a");
        assertNull(set.get("a"));
    }
    @Test
    public void testRemoveWithMultiplicity() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 5);
        set.remove("a",3);
        assertEquals(2, (int)set.get("a"));
        set.remove("a",2);
        assertNull(set.get("a"));
        set.remove("a",2);
        assertNull(set.get("a"));
    }
    @Test
    public void testRemoveAll() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 3);
        set.removeAll("a");
        assertNull(set.get("a"));
    }
    @Test
    public void testRemoveNegativeMultBug() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);

        set.remove("a", -1);

        assertEquals(2, (int)set.get("a")); // fails → becomes 3
    }
    ///---------------------------------------------------------------------------------------
    /// Test intersect (1 function)
    /**
        •	other = null  empty
        •	No overlap  empty
        •	Partial overlap  min multiplicity
     */

    @Test
    public void testIntersectOvelap() {
        Multiset_HashMapImpl<String> set1 = new Multiset_HashMapImpl<>();
        Multiset_HashMapImpl<String> set2 = new Multiset_HashMapImpl<>();

        set1.add("a", 3);
        set2.add("a", 1);

        Multiset_HashMapImpl<String> result = (Multiset_HashMapImpl<String>) set1.intersect(set2);
        assertEquals(1, (int)result.get("a"));
    }
    @Test
    public void testIntersectNoOverlap() {
        Multiset_HashMapImpl<String> set1 = new Multiset_HashMapImpl<>();
        Multiset_HashMapImpl<String> set2 = new Multiset_HashMapImpl<>();

        set1.add("a", 3);
        set2.add("b", 1);

        Multiset_HashMapImpl<String> result = (Multiset_HashMapImpl<String>) set1.intersect(set2);
        assertNull(result.get("a"));
    }
    ///------------------------------------------------------------------------------------------
    /// Test totalCount
    /**
     * Empty  0
     * Multiple elements  correct sum
     */
    @Test
    public void testTotalCount() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        assertEquals(0, set.totalCount());
        set.add("a", 2);
        set.add("b", 3);
        assertEquals(5, set.totalCount());
    }
    ///---------------------------------------------------------------------------------------------
    /// test toList
    @Test
    public void testToList_Empty() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();

        assertTrue(set.toList().isEmpty());
    }
    @Test
    public void testToList_SingleElement() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a");

        assertEquals(1, set.toList().size());
        assertTrue(set.toList().contains("a"));
    }
    @Test
    public void testToList_RepeatedElement() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 3);

        assertEquals(3, set.toList().size());
    }
    @Test
    public void testToList_MultipleElements() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);
        set.add("b", 1);

        java.util.List<String> list = set.toList();

        assertEquals(3, list.size());
        assertEquals(2, java.util.Collections.frequency(list, "a"));
        assertEquals(1, java.util.Collections.frequency(list, "b"));
    }
    ///--------------------------------------------------------------------------------------------
    /// test toString
    @Test
    public void testToString_Empty() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();

        assertEquals("{}", set.toString());
    }
    @Test
    public void testToString_SingleElement() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a");

        String result = set.toString();
        assertTrue(result.contains("a"));
    }
    @Test
    public void testToString_RepeatedElement() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 3);

        String result = set.toString();
        assertTrue(result.contains("a^3"));
    }
    @Test
    public void testToString_MultipleElements() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a", 2);
        set.add("b", 1);

        String result = set.toString();

        assertTrue(result.contains("a^2"));
        assertTrue(result.contains("b"));
    }
    @Test
    public void testToString_NoTrailingComma() {
        Multiset_HashMapImpl<String> set = new Multiset_HashMapImpl<>();
        set.add("a");

        String result = set.toString();

        assertFalse(result.contains(", }"));
    }

    ///--------------------------------------------------------------------------------------------
    /// test equal
    /**
        •	null --> false
        •	Different type --> false
        •	Same elements different order --> true
        •	Different multiplicity --> false
     */
    @Test
    public void testEquals() {
        Multiset_HashMapImpl<String> set1 = new Multiset_HashMapImpl<>();
        Multiset_HashMapImpl<String> set2 = new Multiset_HashMapImpl<>();

        set1.add("a", 2);
        assertFalse(set1.equals(set2));

        set2.add("a", 2);
        assertTrue(set1.equals(set2));

        set1.add("b",3);
        set2.add("b",1);
        assertFalse(set1.equals(set2));


    }
}
