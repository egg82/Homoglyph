package ninja.egg82.homoglyph;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StandardTests {
    @Test
    public void testEquals() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.equals("test", "test"));
        Assertions.assertFalse(helper.equals("test", "me"));

        Assertions.assertFalse(helper.equals("", "test"));
        Assertions.assertFalse(helper.equals("test", ""));

        Assertions.assertFalse(helper.equals(null, "test"));
        Assertions.assertFalse(helper.equals("test", null));

        Assertions.assertTrue(helper.equals(null, null));
    }

    @Test
    public void testStartsWith() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.startsWith("hello, world!", "hello"));
        Assertions.assertFalse(helper.startsWith("hello, world!", "me"));

        Assertions.assertFalse(helper.startsWith("", "test"));
        Assertions.assertTrue(helper.startsWith("test", ""));

        Assertions.assertFalse(helper.startsWith(null, "test"));
        Assertions.assertFalse(helper.startsWith("test", null));

        Assertions.assertFalse(helper.startsWith(null, null));
    }

    @Test
    public void testEndsWith() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.endsWith("hello, world!", "world!"));
        Assertions.assertFalse(helper.endsWith("hello, world!", "me"));

        Assertions.assertFalse(helper.endsWith("", "test"));
        Assertions.assertTrue(helper.endsWith("test", ""));

        Assertions.assertFalse(helper.endsWith(null, "test"));
        Assertions.assertFalse(helper.endsWith("test", null));

        Assertions.assertFalse(helper.endsWith(null, null));
    }

    @Test
    public void testIndexOf() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals(0, helper.indexOf("hello, world!", "hello"));
        Assertions.assertEquals(7, helper.indexOf("hello, world!", "world!"));
        Assertions.assertEquals(-1, helper.indexOf("hello, world!", "me"));

        Assertions.assertEquals(-1, helper.indexOf("", "test"));
        Assertions.assertEquals(0, helper.indexOf("test", ""));

        Assertions.assertEquals(-1, helper.indexOf(null, "test"));
        Assertions.assertEquals(-1, helper.indexOf("test", null));

        Assertions.assertEquals(-1, helper.indexOf(null, null));

        Assertions.assertEquals(4, helper.indexOf("abcdabcd", "abcd", 1));
    }

    @Test
    public void testLastIndexOf() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals(0, helper.lastIndexOf("hello, world!", "hello"));
        Assertions.assertEquals(7, helper.lastIndexOf("hello, world!", "world!"));
        Assertions.assertEquals(-1, helper.lastIndexOf("hello, world!", "me"));

        Assertions.assertEquals(-1, helper.lastIndexOf("", "test"));
        Assertions.assertEquals(3, helper.lastIndexOf("test", ""));
        Assertions.assertEquals(2, helper.lastIndexOf("test", "", -1));

        Assertions.assertEquals(-1, helper.lastIndexOf(null, "test"));
        Assertions.assertEquals(-1, helper.lastIndexOf("test", null));

        Assertions.assertEquals(-1, helper.lastIndexOf(null, null));

        Assertions.assertEquals(4, helper.lastIndexOf("abcdabcd", "abcd"));
        Assertions.assertEquals(0, helper.lastIndexOf("abcdabcd", "abcd", 6));
        Assertions.assertEquals(0, helper.lastIndexOf("abcdabcd", "abcd", -1));
    }

    @Test
    public void testContains() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.contains("hello, world!", "hello"));
        Assertions.assertTrue(helper.contains("hello, world!", "world!"));
        Assertions.assertFalse(helper.contains("hello, world!", "me"));

        Assertions.assertFalse(helper.contains("", "test"));
        Assertions.assertTrue(helper.contains("test", ""));

        Assertions.assertFalse(helper.contains(null, "test"));
        Assertions.assertFalse(helper.contains("test", null));

        Assertions.assertFalse(helper.contains(null, null));
    }
}
