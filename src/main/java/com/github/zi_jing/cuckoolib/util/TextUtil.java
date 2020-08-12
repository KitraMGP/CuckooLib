package com.github.zi_jing.cuckoolib.util;

import com.github.zi_jing.cuckoolib.client.render.GLUtils;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {
  /**
   * 将一个字符串按一定的宽度限制分成若干行(忽略样式标识)
   *
   * @param str 输入的字符串
   * @param limit 宽度限制(像素)
   * @return 一个List，每行一个字符串
   */
  public static List<String> trimStringToWidthWithoutStyleMarks(String str, int limit) {
    List<String> lines = new ArrayList<>();
    String line = "";
    for (char c : str.toCharArray()) {
      if (GLUtils.getStringWidth(line) >= limit) {
        lines.add(new String(line));
        line = "";
      }
      line += c;
    }
    if (!line.isEmpty()) {
      lines.add(new String(line));
    }
    return lines;
  }

  /**
   * 用于将一个字符串中的所有样式标记都完整应用到每个字符上...<br>
   * 示例：<br>
   * 输入：<br>
   * {@code "§dTest§0§lString§rFormat"}<br>
   * 输出：<br>
   * {@code "§dT§de§ds§dt§0§lS§0§lt§0§lr§0§li§0§ln§0§lg§rFormat"}<br>
   *
   * @param str 输入的字符串
   * @return 输出的字符串
   */
  public static String expandTextStyleMark(String str) {
    char styleMark = '§';
    int index;
    boolean colored = false;
    char currentColor = 'f';
    boolean random = false; // k
    boolean bold = false; // l
    boolean strikethrough = false; // m
    boolean underlined = false; // n
    boolean italic = false; // o
    String allStyles = "0123456789abcdefklmnor";
    String colors = "0123456789abcdef";
    String tmp = new String(str);
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      index = tmp.indexOf(styleMark);
      if (index == -1) { // 没有任何样式代码
        if (tmp.isEmpty()) { // 所有字符都处理完毕了，直接结束
          break;
        } else {
          for (char c : tmp.toCharArray()) { // 给剩余字符附上样式并结束
            if (colored) {
              output.append(styleMark);
              output.append(currentColor);
            }
            if (random) {
              output.append(styleMark);
              output.append('k');
            }
            if (bold) {
              output.append(styleMark);
              output.append('l');
            }
            if (strikethrough) {
              output.append(styleMark);
              output.append('m');
            }
            if (underlined) {
              output.append(styleMark);
              output.append('n');
            }
            if (italic) {
              output.append(styleMark);
              output.append('o');
            }
            output.append(c);
          }
          break;
        }
      }
      if (index != 0) { // 有样式代码但是不在开头，要先把前面的字符附上已有的样式
        for (char c : tmp.toCharArray()) {
          if (c == styleMark) break;
          if (colored) {
            output.append(styleMark);
            output.append(currentColor);
          }
          if (random) {
            output.append(styleMark);
            output.append('k');
          }
          if (bold) {
            output.append(styleMark);
            output.append('l');
          }
          if (strikethrough) {
            output.append(styleMark);
            output.append('m');
          }
          if (underlined) {
            output.append(styleMark);
            output.append('n');
          }
          if (italic) {
            output.append(styleMark);
            output.append('o');
          }
          output.append(c);
          tmp = tmp.substring(1);
        }
        continue;
      }
      if (index == tmp.length()) break;
      index++;
      if (allStyles.indexOf(tmp.charAt(index)) == -1) { // 样式代码无效，保持原样
        output.append(tmp, 0, 2);
        tmp = tmp.substring(2);
        continue;
      }
      // 正式开始处理样式
      // 字符的转移放到最后
      if (colors.indexOf(tmp.charAt(index)) != -1) { // 颜色代码
        colored = true;
        currentColor = tmp.charAt(index);
      } else if (tmp.charAt(index) == 'r') { // §r
        colored = false;
        currentColor = 'f';
        random = false;
        bold = false;
        strikethrough = false;
        underlined = false;
        italic = false;
      } else { // 其他样式代码
        switch (tmp.charAt(index)) {
          case 'k':
            random = true;
            break;
          case 'l':
            bold = true;
            break;
          case 'm':
            strikethrough = true;
            break;
          case 'n':
            underlined = true;
            break;
          case 'o':
            italic = true;
            break;
          default:
            break; // 不可能执行
        }
      }
      // 转移字符
      tmp = tmp.substring(2);
    }
    return output.toString();
  }
}
