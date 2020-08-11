package unittest;

import com.github.zi_jing.cuckoolib.client.render.GLUtil;
import com.github.zi_jing.cuckoolib.util.math.ColorRGBA;
import org.junit.Assert;
import org.junit.Test;

public class TestGLUtil {

  @Test
  public void testRgbToHex() {
    int result1 = GLUtil.rgbToHex(102, 204, 255);
    Assert.assertEquals(0x66CCFF, result1);

    int result2 = GLUtil.rgbToHex(0, 0, 0);
    Assert.assertEquals(0, result2);

    int result3 = GLUtil.rgbToHex(255, 255, 255);
    Assert.assertEquals(0xFFFFFF, result3);
  }

  @Test
  public void testHexToRGB() {
    ColorRGBA rgb1 = GLUtil.hexRGBToRGB(0x66CCFF);
    Assert.assertEquals(new ColorRGBA(102, 204, 255), rgb1);

    ColorRGBA rgb2 = GLUtil.hexRGBToRGB(0);
    Assert.assertEquals(new ColorRGBA(0, 0, 0), rgb2);

    ColorRGBA rgb3 = GLUtil.hexRGBToRGB(0xFFFFFF);
    Assert.assertEquals(new ColorRGBA(255, 255, 255), rgb3);
  }

  @Test
  public void testHexToRGBA() {
    ColorRGBA rgb1 = GLUtil.hexRGBAToRGBA(0x66CCFF40);
    Assert.assertEquals(new ColorRGBA(102, 204, 255, 64), rgb1);

    ColorRGBA rgb2 = GLUtil.hexRGBAToRGBA(0x000000);
    Assert.assertEquals(new ColorRGBA(0, 0, 0, 0), rgb2);

    ColorRGBA rgb3 = GLUtil.hexRGBAToRGBA(0xFFFFFFFF);
    Assert.assertEquals(new ColorRGBA(255, 255, 255, 255), rgb3);

    ColorRGBA rgb4 = GLUtil.hexRGBAToRGBA(0x00000040);
    Assert.assertEquals(new ColorRGBA(0, 0, 0, 64), rgb4);

    ColorRGBA rgb5 = GLUtil.hexRGBAToRGBA(0xFFFFFF00);
    Assert.assertEquals(new ColorRGBA(255, 255, 255, 0), rgb5);
  }
}
