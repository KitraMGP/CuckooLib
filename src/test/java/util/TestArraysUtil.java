package util;

import com.github.zi_jing.cuckoolib.util.ArraysUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArraysUtil {
  @Test
  public void testConcat() {
    String[] input1 = {"abc", "def", "ghi"};
    String[] input2 = {"str1", "str2", "str3"};
    String[] input3 = {"str4"};
    String[] answer = {"abc", "def", "ghi", "str1", "str2", "str3", "str4"};
    Assertions.assertArrayEquals(answer, ArraysUtil.concat(input1, input2, input3));
  }
}
