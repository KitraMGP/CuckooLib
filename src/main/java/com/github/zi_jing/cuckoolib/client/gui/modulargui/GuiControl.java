package com.github.zi_jing.cuckoolib.client.gui.modulargui;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import com.github.zi_jing.cuckoolib.client.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class GuiControl {

  public final int id;
  protected final EnumControlType type;
  protected final ModularGuiScreen gui;
  /**
   * 控件的<strong>相对</strong>横坐标。<br>
   * <br>
   * This control's <strong>relative</strong> X coordinate.
   *
   * @see GuiControl#getX
   */
  protected int x;
  /**
   * 控件的<strong>相对</strong>纵坐标。<br>
   * <br>
   * This control's <strong>relative</strong> Y coordinate.
   *
   * @see GuiControl#getY
   */
  protected int y;

  protected int width = 0;
  protected int height = 0;

  private GuiControl(int id, ModularGuiScreen gui, EnumControlType type) {
    this.id = id;
    this.gui = gui;
    this.type = type;
  }

  private static void throwArgumentException(String s) {
    CuckooLib.getLogger().error("An error occurred when rendering Gui, " + s + ".");
    throw new IllegalArgumentException(s);
  }

  public final EnumControlType getType() {
    return this.type;
  }

  public void draw(int mouseX, int mouseY, float partialTicks, ModularGuiScreen gui) {}

  public void init() {}

  public void remove() {}

  public final boolean isMouseOn() {
    if (this.width == 0 || this.height == 0) return false;
    int mouseX = ClientUtils.getMouseX();
    int mouseY = ClientUtils.getMouseY();
    return mouseX >= this.x
        && mouseX <= this.x + this.width
        && mouseY >= this.y
        && mouseY <= this.y + this.height;
  }

  protected final int getId() {
    return id;
  }

  /**
   * 设置控件的<strong>相对</strong>位置，必须在{@link ModularGuiScreen#initGui}中调用。<br>
   * <br>
   * Sets the control's <strong>relative</strong> position, it must be called in {@link
   * ModularGuiScreen#initGui}.
   *
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void setPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * 获取控件的<strong>相对</strong>横坐标。<br>
   * <br>
   * Gets this control's <strong>relative</strong> X coordinate.
   *
   * @return 横坐标
   * @see GuiControl#x
   */
  public int getX() {
    return this.x;
  }

  /**
   * 获取控件的<strong>相对</strong>纵坐标。<br>
   * <br>
   * Gets this control's <strong>relative</strong> Y coordinate.
   *
   * @return 纵坐标
   * @see GuiControl#y
   */
  public int getY() {
    return this.y;
  }

  public enum EnumControlType {
    BUTTON("button", 0),
    PROGRESSBAR("progressbar", 1),
    SCROLLBAR("scrollbar", 2),
    INPUTBOX("inputbox", 3),
    CHECKBOX("checkbox", 4);

    final String name;
    final int index;

    EnumControlType(String name, int index) {
      this.name = name;
      this.index = index;
    }

    public int getIndex() {
      return this.index;
    }

    public String getName() {
      return this.name;
    }
  }

  /**
   * 它用来创建适用于一个Gui的控件。<br>
   * <br>
   * It is used to create controls for a Gui.
   */
  public static class ControlFactory {
    private final ModularGuiScreen gui;
    private int nextId = 0;

    public ControlFactory(ModularGuiScreen gui) {
      this.gui = gui;
    }

    public Button createButton(
        int width, int height, int u, int v, int u2, int v2, Consumer<ModularGuiScreen> onClick) {
      return (Button)
          gui.addControl(new Button(nextId++, width, height, u, v, u2, v2, onClick, this.gui));
    }

    public ProgressBar createProgressBar(int width, int height) {
      return (ProgressBar) gui.addControl(new ProgressBar(nextId++, width, height, this.gui));
    }

    // TODO more controls
  }

  public static class Button extends GuiControl {

    protected final int u;
    protected final int v;
    protected final int u2;
    protected final int v2;
    protected final Consumer<ModularGuiScreen> func;
    protected String text = "";
    protected int color = 0;
    protected net.minecraft.client.gui.GuiButton buttonMc;

    protected Button(
        int id,
        int width,
        int height,
        int u,
        int v,
        int u2,
        int v2,
        Consumer<ModularGuiScreen> onClick,
        ModularGuiScreen gui) {
      super(id, gui, EnumControlType.BUTTON);
      this.x = 0;
      this.y = 0;
      this.width = width;
      this.height = height;
      this.u = u;
      this.v = v;
      this.u2 = u2;
      this.v2 = v2;
      this.func = onClick;
    }

    @Override
    public void init() {

      boolean mouseOn = this.isMouseOn();

      this.buttonMc =
          new net.minecraft.client.gui.GuiButton(id, x, y, width, height, text) {

            @Override
            public void drawButton(
                @Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
              if (this.visible) {

                int x = gui.getOffsetX() + getX();
                int y = gui.getOffsetY() + getY();

                GLUtils.pushMatrix();
                GLUtils.enableBlend();
                GLUtils.normalBlend();
                GLUtils.color4f(1f, 1f, 1f, 1f);
                GLUtils.bindTexture(gui.getTexture());
                if (mouseOn) {
                  Gui.drawModalRectWithCustomSizedTexture(
                      x, y, u2, v2, width, height, gui.getTextureWidth(), gui.getTextureHeight());
                } else {
                  Gui.drawModalRectWithCustomSizedTexture(
                      x, y, u, v, width, height, gui.getTextureWidth(), gui.getTextureHeight());
                }

                if (!text.equals("")) {
                  if (mouseOn) {
                    GLUtils.drawCenteredString(
                        text, x + width / 2, y + (this.height - 8) / 2, Colors.MOUSEON_TEXT);
                  } else {
                    GLUtils.drawCenteredString(
                        text, x + width / 2, y + (this.height - 8) / 2, color);
                  }
                }
                GLUtils.disableBlend();
                GLUtils.popMatrix();
              }
            }
          };

      gui.getButtonList().add(buttonMc);
    }

    @Override
    public void remove() {
      gui.getButtonList().remove(this.getId());
    }

    public void setText(String text, int color) {
      this.text = text;
      this.color = color;
    }

    @Override
    public void setPos(int x, int y) {
      super.setPos(x, y);
      this.buttonMc.x = x + gui.getOffsetX();
      this.buttonMc.y = y + gui.getOffsetY();
    }
  }

  public static class ProgressBar extends GuiControl {

    /** 范围/Range: [0,100] */
    private int progress = 0;

    protected ProgressBar(int id, int width, int height, ModularGuiScreen gui) {
      super(id, gui, EnumControlType.PROGRESSBAR);
      this.x = 0;
      this.y = 0;
      this.width = width;
      this.height = height;
    }

    public int getProgress() {
      return progress;
    }

    /**
     * 设置进度，进度必须为0-100的整数。
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
      if (progress < 0 || progress > 100) throwArgumentException("progress must be in [0,100]");
      this.progress = progress;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks, ModularGuiScreen gui) {
      super.draw(mouseX, mouseY, partialTicks, gui);
      int x = getX() + gui.getOffsetX();
      int y = getY() + gui.getOffsetY();
      GLUtils.drawRect(x, y, x + width, y + height, 0xFFFFFFFF);
      if (progress > 0) {
        GLUtils.drawRect(
            x + 1,
            y + 1,
            x + (int) ((float) (progress) / 100F * (width - 1)),
            y + height - 1,
            0xFF00FF00);
      }
    }
  }

  // TODO  ScrollBar
  public static class ScrollBar extends GuiControl {

    public final int direction;

    /**
     * @param id id
     * @param type the orientation of the scrollbar, 0 is horizontal, 1 is vertical
     * @param gui The Gui it belongs to
     */
    protected ScrollBar(int id, int type, ModularGuiScreen gui) {
      super(id, gui, EnumControlType.SCROLLBAR);
      if (type == 0 || type == 1) {
        this.direction = type;
      } else {
        throwArgumentException("scroll bar type can only be 0 or 1");
        direction = 0; // 永远不会执行
      }
    }
  }
}
