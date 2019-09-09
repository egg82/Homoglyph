package ninja.egg82.homoglyph;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomoglyphTest {
    @Test
    public void testASCII() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals("TEST", helper.toASCII("ＴＥＳＴ"));
        Assertions.assertEquals("test", helper.toASCII("ｔｅｓｔ"));
    }

    @Test
    public void testEquals() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.equals("ＴＥＳＴ", "TEST"));
        Assertions.assertFalse(helper.equals("ＴＥＳＴ", "test"));
        Assertions.assertTrue(helper.equals("ｔｅｓｔ", "test"));
        Assertions.assertFalse(helper.equals("ｔｅｓｔ", "TEST"));
    }

    @Test
    public void testStartsWith() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.startsWith("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "HELLO"));
        Assertions.assertFalse(helper.startsWith("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "hello"));
        Assertions.assertTrue(helper.startsWith("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "hello"));
        Assertions.assertFalse(helper.startsWith("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "HELLO"));
    }

    @Test
    public void testEndsWith() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.endsWith("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "WORLD!"));
        Assertions.assertFalse(helper.endsWith("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "world!"));
        Assertions.assertTrue(helper.endsWith("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "world!"));
        Assertions.assertFalse(helper.endsWith("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "WORLD!"));
    }

    @Test
    public void testIndexOf() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals(0, helper.indexOf("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "HELLO"));
        Assertions.assertEquals(7, helper.indexOf("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "WORLD!"));
        Assertions.assertEquals(0, helper.indexOf("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "hello"));
        Assertions.assertEquals(7, helper.indexOf("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "world!"));
    }

    @Test
    public void testLastIndexOf() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertEquals(4, helper.lastIndexOf("ＡＢＣＤＡＢＣＤ", "ABCD"));
        Assertions.assertEquals(0, helper.lastIndexOf("ＡＢＣＤＡＢＣＤ", "ABCD", -1));
        Assertions.assertEquals(4, helper.lastIndexOf("ａｂｃｄａｂｃｄ", "abcd"));
        Assertions.assertEquals(0, helper.lastIndexOf("ａｂｃｄａｂｃｄ", "abcd", -1));
    }

    @Test
    public void testContains() throws IOException {
        HomoglyphHelper helper = HomoglyphHelper.create();

        Assertions.assertTrue(helper.contains("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "WORLD!"));
        Assertions.assertFalse(helper.contains("ＨＥＬＬＯ，　ＷＯＲＬＤ！", "world!"));
        Assertions.assertTrue(helper.contains("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "world!"));
        Assertions.assertFalse(helper.contains("ｈｅｌｌｏ‚ㅤｗｏｒｌｄǃ", "WORLD!"));
    }
}
