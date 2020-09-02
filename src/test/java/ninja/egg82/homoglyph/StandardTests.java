package ninja.egg82.homoglyph;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StandardTests {
    @Test
    public void testASCII() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals("TEST", helper.toASCII("TEST"));
        Assertions.assertEquals("test", helper.toASCII("test"));
    }

    @Test
    public void testOrderedASCII() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals("TEST", helper.toOrderedASCII("TEST"));
        Assertions.assertEquals("test", helper.toOrderedASCII("test"));

        Assertions.assertEquals("1", helper.toOrderedASCII("１"));
    }

    @Test
    public void testAlphanumeric() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        String A2Z_ASCII = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String a2z_ASCII = "abcdefghijklmnopqrstuvwxyz";
        String z29_ASCII = "0123456789";

        Assertions.assertTrue(helper.equals(A2Z_ASCII, A2Z_ASCII));
        Assertions.assertTrue(helper.equals(a2z_ASCII, a2z_ASCII));
        Assertions.assertTrue(helper.equals(z29_ASCII, z29_ASCII));

        Assertions.assertEquals(A2Z_ASCII, helper.toAlphanumeric(A2Z_ASCII));
        Assertions.assertEquals(a2z_ASCII, helper.toAlphanumeric(a2z_ASCII));
        Assertions.assertEquals(z29_ASCII, helper.toAlphanumeric(z29_ASCII));
    }

    @Test
    public void testStandardCharset() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        String A2Z_ASCII = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String a2z_ASCII = "abcdefghijklmnopqrstuvwxyz";
        String z29_ASCII = "0123456789";
        String symbols_ASCII = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/";

        Assertions.assertTrue(helper.equals(A2Z_ASCII, A2Z_ASCII));
        Assertions.assertTrue(helper.equals(a2z_ASCII, a2z_ASCII));
        Assertions.assertTrue(helper.equals(z29_ASCII, z29_ASCII));
        Assertions.assertTrue(helper.equals(symbols_ASCII, symbols_ASCII));

        Assertions.assertEquals(A2Z_ASCII, helper.toStandardCharset(A2Z_ASCII));
        Assertions.assertEquals(a2z_ASCII, helper.toStandardCharset(a2z_ASCII));
        Assertions.assertEquals(z29_ASCII, helper.toStandardCharset(z29_ASCII));
        Assertions.assertEquals(symbols_ASCII, helper.toStandardCharset(symbols_ASCII));
    }

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
