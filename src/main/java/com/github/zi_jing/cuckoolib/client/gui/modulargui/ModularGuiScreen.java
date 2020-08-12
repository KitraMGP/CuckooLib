package com.github.zi_jing.cuckoolib.client.gui.modulargui;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import com.github.zi_jing.cuckoolib.client.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * GuiScreen基类<br>
 * <br>
 * GuiScreen Base Class
 */
public class ModularGuiScreen extends GuiScreen implements IModularGui {
  /**
   * 这个Map用来存储所有的控件，键为控件ID，值为控件的实例。<br>
   * <br>
   * This map is used to store all Gui controls. The key is the control ID and the value is the
   * control object.
   */
  private final ControlMap controls = new ControlMap(this);
  /**
   * 它用来决定在Gui被打开是是否强制调小游戏界面缩放设置。<br>
   * <br>
   * It is used to decide whether or not to force the interface zoom factor to be turned down when
   * the Gui is opened.
   *
   * @see ModularGuiScreen#ModularGuiScreen
   * @see ModularGuiScreen#onGuiClosed
   */
  private final boolean forceChangeGuiScale;
  /**
   * 它用来决定Gui是否有自定义背景，如果它为true, <code>drawDefaultBackground()</code> 方法将不会被自动执行。<br>
   * <br>
   * It is used to determine whether the Gui has a custom background If it is true, the <code>
   * drawDefaultBackground()</code> method will not be executed automaticly.
   */
  private final boolean hasCustomBackground;

  private final boolean doesGuiPauseGame;
  /**
   * 存储玩家打开Gui前的界面缩放设置，以便于在玩家关闭Gui时恢复到原来的设置。<br>
   * <br>
   * Store the interface zoom settings before the player opens the Gui, so as to restore the
   * original interface size when the player closes the Gui.
   */
  private final int lastGuiScale;
  /**
   * 这个实例用来创建控件。<br>
   * This object is used to create Controls.<br>
   * <br>
   * 例子/Example: <br>
   * <code>controlFactory.createButton(...);</code><br>
   * <br>
   * <strong>注意/Warning: <br>
   * 要使控件被添加进Gui中，必须使用<code>this.addControl(...);
   * </code>来添加。</strong><br>
   * <br>
   * If you want to add a control to this Gui, you must use <code>this.addControl(...);</code>.
   */
  public GuiControl.ControlFactory controlFactory = new GuiControl.ControlFactory(this);

  private ResourceLocation TEXTURE = new ResourceLocation("");
  private int nextId = 0;
  /**
   * 这里的窗口宽度不是游戏窗口宽度，是Gui的宽度。<br>
   * <br>
   * The window width here is not the width of the game window, but the width of the Gui interface.
   */
  private int windowWidth;
  /**
   * 这里的窗口高度不是游戏窗口高度，是Gui的高度。<br>
   * <br>
   * The window height here is not the height of the game window, but the height of the Gui
   * interface.
   */
  private int windowHeight;

  private int texWidth;
  private int texHeight;
  private int offsetX;
  private int offsetY;
  /**
   * 在<code>drawScreen()</code>中的代码被执行之前调用。<br>
   * <br>
   * Will be called before the code in <code>drawScreen()</code> is executed.
   *
   * @see ModularGuiScreen#drawScreen
   * @see ModularGuiScreen#setPreRenderFunction
   */
  private Consumer<ModularGuiScreen> preRenderFunction = (gui) -> {};

  private String title = "";

  /**
   * 构造一个MetaGuiScreen实例。<br>
   * <br>
   * Construct a new instance of MetaGuiScreen.
   *
   * @param type See {@link ModularGuiConstants}
   */
  public ModularGuiScreen(int type) {
    super();
    this.checkWorldAndResolution();
    this.forceChangeGuiScale =
        (type & ModularGuiConstants.GUI_FORCE_CHANGE_SCALE)
            == ModularGuiConstants.GUI_FORCE_CHANGE_SCALE;
    this.hasCustomBackground =
        (type & ModularGuiConstants.GUI_HAS_CUSTOM_BACKGROUND)
            == ModularGuiConstants.GUI_HAS_CUSTOM_BACKGROUND;
    this.doesGuiPauseGame =
        (type & ModularGuiConstants.GUI_NOT_PAUSE_GAME) != ModularGuiConstants.GUI_NOT_PAUSE_GAME;
    lastGuiScale = Minecraft.getMinecraft().gameSettings.guiScale;
    if (forceChangeGuiScale) Minecraft.getMinecraft().gameSettings.guiScale = 3;
  }

  /**
   * 抛出IllegalArgumentException并输出报错信息。<br>
   * <br>
   * Throws an IllegalArgumentException and prints this error message.
   */
  private static void throwArgumentException(String message) {
    CuckooLib.getLogger().error("An error occurred when rendering Gui, " + message + ".");
    throw new IllegalArgumentException(message);
  }

  /**
   * 绘制整个Gui。<br>
   * <br>
   * Draws the whole Gui.
   */
  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.checkWorldAndResolution();
    GLUtils.pushMatrix();
    GLUtils.enableBlend();
    GLUtils.normalBlend();
    offsetX = (this.width - this.windowWidth) / 2;
    offsetY = (this.height - this.windowHeight) / 2;
    this.preRenderFunction.accept(this);
    GLUtils.disableBlend();
    GLUtils.popMatrix();

    if (!this.hasCustomBackground) {
      this.drawDefaultBackground();
    }

    GLUtils.pushMatrix();
    GLUtils.enableBlend();
    GLUtils.normalBlend();
    GLUtils.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    if (!TEXTURE.toString().equals("minecraft:")) {
      this.mc.getTextureManager().bindTexture(TEXTURE);
      Gui.drawModalRectWithCustomSizedTexture(
          offsetX, offsetY, 0, 0, windowWidth, windowHeight, texWidth, texHeight);
    }
    GLUtils.drawCenteredString(
        title, offsetX + windowWidth / 2, offsetY + 10, Colors.DEFAULT_BLACK);
    // this.fontRenderer.drawString(title, offsetX + windowWidth / 2 -
    // this.fontRenderer.getStringWidth(title) / 2, offsetY + 10, Colors.DEFAULT_BLACK, false);
    GLUtils.disableBlend();
    GLUtils.popMatrix();
    for (int key : this.controls.keySet()) {
      this.controls.get(key).draw(mouseX, mouseY, partialTicks, this);
    }
    super.drawScreen(mouseX, mouseY, partialTicks);
  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  protected void mouseClickMove(
      int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
    super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
  }

  @Override
  protected void actionPerformed(@Nonnull GuiButton button) throws IOException {
    for (Map.Entry<Integer, GuiControl> entry : controls.entrySet()) {
      if (entry.getValue() instanceof GuiControl.Button) {
        if (button == ((GuiControl.Button) entry.getValue()).buttonMc) {
          ((GuiControl.Button) entry.getValue()).func.accept(this);
        }
      }
    }
    super.actionPerformed(button);
  }

  /**
   * 在Gui被打开或游戏窗口大小改变时被调用。<br>
   * <br>
   * Called when this Gui is opened or the game window resized.
   */
  @Override
  public void initGui() {
    this.checkWorldAndResolution();
    offsetX = (this.width - this.windowWidth) / 2;
    offsetY = (this.height - this.windowHeight) / 2;
    for (Map.Entry<Integer, GuiControl> entry :
        controls.entrySet()) { // 因为mc会在initGui()之前清空buttonList，所以重新加一遍
      if (entry.getValue() instanceof GuiControl.Button) {
        this.buttonList.add(((GuiControl.Button) entry.getValue()).buttonMc);
      }
    }
  }

  @Override
  public void updateScreen() {
    this.checkWorldAndResolution();
    super.updateScreen();
  }

  @Override
  public void onGuiClosed() {
    if (forceChangeGuiScale) {
      Minecraft.getMinecraft().gameSettings.guiScale = lastGuiScale;
    }
    super.onGuiClosed();
  }

  @Override
  public void drawBackground(int tint) {
    super.drawBackground(tint);
  }

  @Override
  public boolean doesGuiPauseGame() {
    return this.doesGuiPauseGame;
  }

  @Override
  public ControlMap getControls() {
    return this.controls;
  }

  /**
   * 设置<code>drawScreen()</code>中的代码被执行前调用的函数，通常用于绘制自定义Gui。<br>
   * <br>
   * Sets the function that will be called before the code in <code>drawScreen()</code> is executed,
   * it is usually used to draw custom Gui.
   *
   * @param func 函数
   * @see ModularGuiScreen#preRenderFunction
   * @see ModularGuiScreen#drawScreen
   */
  public void setPreRenderFunction(Consumer<ModularGuiScreen> func) {
    this.preRenderFunction = func;
  }

  /**
   * 获取这个Gui的贴图位置。如果没有设置贴图，将会返回一个空的ResourceLocation (<code>new ResourceLocation("minecraft:")</code>
   * )<br>
   * <br>
   * Gets the texture location of this Gui. If the texture isn't set, it will return a empty
   * ResourceLocation(<code>new ResourceLocation("minecraft:")</code>)
   *
   * @return 贴图位置
   * @see ModularGuiScreen#TEXTURE
   * @see ModularGuiScreen#setTexture
   */
  public ResourceLocation getTexture() {
    return TEXTURE;
  }

  /**
   * 设置这个Gui的贴图位置。如果不设置，贴图将不会被绘制。<br>
   * <br>
   * Sets the texture location of this Gui. If you don't set the texture, the texture won't be
   * drawn.
   *
   * @param texture 贴图位置
   * @see ModularGuiScreen#setPreRenderFunction
   * @see ModularGuiScreen#drawScreen
   */
  public void setTexture(ResourceLocation texture) {
    if (texture == null) throwArgumentException("texture can't be null");
    TEXTURE = texture;
  }

  public int getTextureWidth() {
    return this.texWidth;
  }

  public int getTextureHeight() {
    return this.texHeight;
  }

  /**
   * 获取界面的实际宽度。<br>
   * <br>
   * Gets the real width of this Gui.
   *
   * @return 贴图实际宽度(像素)
   */
  public int getWindowWidth() {
    return windowWidth;
  }

  /**
   * 获取界面的实际高度。<br>
   * <br>
   * Gets the real height of this Gui.
   *
   * @return 贴图实际高度(像素)
   */
  public int getWindowHeight() {
    return windowHeight;
  }

  /**
   * 设置界面的实际大小，<strong>不建议在构造函数/initGui()之外使用。</strong><br>
   * <br>
   * Sets the real width of this Gui, <strong>it's not recommended to use this outside of the
   * constructor/initGui().</strong>
   *
   * @param width 宽度(像素)
   * @param height 高度(像素)
   */
  public void setWindowSize(int width, int height) {
    if (width <= 0 || height <= 0) {
      throwArgumentException("width and height must be positive");
    }
    this.windowWidth = width;
    this.windowHeight = height;
  }

  /**
   * 获取Gui左上角的横坐标。<br>
   * <br>
   * Gets the abscissa of the top left corner of Gui.
   *
   * @return 左上角横坐标(像素)
   */
  public int getOffsetX() {
    return offsetX;
  }

  /**
   * 获取Gui左上角的纵坐标。<br>
   * <br>
   * Gets the vertical coordinate of the top left corner of Gui.
   *
   * @return 左上角纵坐标(像素)
   */
  public int getOffsetY() {
    return offsetY;
  }

  public void setOffset(int x, int y) {
    this.offsetX = x;
    this.offsetY = y;
  }

  /**
   * 指定贴图大小，<strong>不建议在构造函数/initGui()之外使用。</strong><br>
   * <br>
   * Appoints the texture size, <strong>it's not recommended to use this outside of the
   * constructor/initGui().</strong>
   *
   * @param width 贴图宽度(像素)
   * @param height 贴图高度(像素)
   */
  public void setTextureSize(int width, int height) {
    if (width <= 0 || height <= 0) {
      throwArgumentException("texture width and texture height must be positive");
    }
    this.texWidth = width;
    this.texHeight = height;
  }

  /**
   * 获取这个Gui的标题。<br>
   * <br>
   * Gets the title of this Gui.
   *
   * @return 标题
   * @see ModularGuiScreen#setTitle
   */
  public String getTitle() {
    return title;
  }

  /**
   * 设置显示在界面上边中间的标题，<strong>不支持多行</strong>。<br>
   * <br>
   * Set the title of the interface, which is displayed in the middle of the upper part of the
   * interface by default, <strong>doesn't support multi line yet</strong>.
   *
   * @param title 标题
   * @see ModularGuiScreen#drawScreen
   */
  public void setTitle(String title) {
    if (title == null) throwArgumentException("title can't be null");
    this.title = title;
  }

  /**
   * 获取Gui在被打开时是否会强制修改MC界面缩放设置。<br>
   * <br>
   * Gets whether the Gui will force the interface scaling settings to be modified when it is
   * opened.
   *
   * @return forceChangeGuiScale
   * @see ModularGuiScreen#forceChangeGuiScale
   * @see ModularGuiScreen#ModularGuiScreen
   * @see ModularGuiScreen#onGuiClosed
   */
  public boolean whetherToChangeGuiScaleWhenOpening() {
    return forceChangeGuiScale;
  }

  public boolean getHasCustomBackground() {
    return hasCustomBackground;
  }

  /**
   * 仅供内部使用。<br>
   * <br>
   * Internal use only.
   *
   * @return buttonList
   */
  protected List<GuiButton> getButtonList() {
    return this.buttonList;
  }

  @Override
  public GuiControl addControl(GuiControl control) {
    this.controls.put(nextId++, control);
    return control;
  }

  @Override
  public void removeControl(GuiControl control) {
    this.controls.remove(control.getId());
  }

  /** */
  // TODO
  private void checkWorldAndResolution() {
    if (this.mc == null) {
      this.mc = Minecraft.getMinecraft();
      this.width = ClientUtils.getDisplayWidth();
      this.height = ClientUtils.getDisplayHeight();
    }
  }
}
