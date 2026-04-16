package test;

import static org.junit.Assert.*;
import de.tilman_neumann.util.StringUtil;
import org.junit.Test;

public class StringUtilTest{
    /// test StringUtil.repeat
    /**
            Partitions
            •	s: null, empty, normal string
            •	n: n <= 0, n = 1, n > 1
            Boundary Values
            •	n = -1, 0, 1
     */
    @Test
    public void testRepeatNormal() {
        assertEquals("ababab", StringUtil.repeat("ab", 3));
    }

    @Test
    public void testRepeatOne() {
        assertEquals("a", StringUtil.repeat("a", 1));
    }

    @Test
    public void testRepeatNull() {
        assertNull(StringUtil.repeat(null, 5));
    }

    @Test
    public void testRepeatZero() {
        assertNull(StringUtil.repeat("abc", 0));
    }

    @Test
    public void testRepeatLessThanZero() {
        assertNull(StringUtil.repeat("abc", -1));
    }

    @Test
    public void testRepeatEmpty() {
        assertEquals("", StringUtil.repeat("", 5));
    }

    ///---------------------------------------------------------------------------------------------
    /// test StringUtil.formatLeft
    /**
            Partitions
        •	s: null, shorter, equal, longer
        •	mask: null, empty, normal
        Boundary Values
        •	s.length = mask.length
        •	s.length = mask.length ± 1
     */
    @Test
    public void testFormatLeftNullString() {
        assertEquals("123", StringUtil.formatLeft(null, "123"));
    }
    @Test
    public void testFormatLeftNullMask() {
        assertEquals("abc", StringUtil.formatLeft("abc", null));
    }
    @Test
    public void testFormatLeftShorter() {
        assertEquals("abc456", StringUtil.formatLeft("abc", "123456"));
    }
    @Test
    public void testFormatLeftLonger() {
        assertEquals("abcdef", StringUtil.formatLeft("abcdef", "123"));
    }
    @Test
    public void testFormatLeftEqual() {
        assertEquals("abc", StringUtil.formatLeft("abc", "123"));
    }
    @Test
    public void testFormatLeftEmptyString() {
        assertEquals("123", StringUtil.formatLeft("", "123"));
    }

    @Test
    public void testFormatLeftEmptyMask() {
        assertEquals("abc", StringUtil.formatLeft("abc", ""));
    }
    @Test
    public void testFormatLeftBothNull() {
        assertEquals("", StringUtil.formatLeft(null, null));
    }
    @Test
    public void testFormatLeftBoundaryPlusOne() {
        assertEquals("a2", StringUtil.formatLeft("a", "12"));
    }
    @Test
    public void testFormatLeftBoundaryMinusOne() {
        assertEquals("ab", StringUtil.formatLeft("ab", "1"));
    }
    ///--------------------------------------------------------------------------------------------------
    /// test StringUtil.formatRight
    @Test
    public void testFormatRightShorter() {
        assertEquals("123abc", StringUtil.formatRight("abc", "123456"));
    }
    @Test
    public void testFormatRightLonger() {
        assertEquals("abcdef", StringUtil.formatRight("abcdef", "123"));
    }
    @Test
    public void testFormatRightEqual() {
        assertEquals("abc", StringUtil.formatRight("abc", "123"));
    }
    @Test
    public void testFormatRightNullString() {
        assertEquals("123", StringUtil.formatRight(null, "123"));
    }
    @Test
    public void testFormatRightEmptyString() {
        assertEquals("123", StringUtil.formatRight("", "123"));
    }
    @Test
    public void testFormatRightNullMask() {
        assertEquals("abc", StringUtil.formatRight("abc", null));
    }
    @Test
    public void testFormatRightEmptyMask() {
        assertEquals("abc", StringUtil.formatRight("abc", ""));
    }
    @Test
    public void testFormatRightBoundaryPlusOne() {
        assertEquals("1a", StringUtil.formatRight("a", "12"));
    }
    @Test
    public void testFormatRightBoundaryMinusOne() {
        assertEquals("ab", StringUtil.formatRight("ab", "1"));
    }

}