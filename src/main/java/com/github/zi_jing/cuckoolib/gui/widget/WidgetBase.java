package com.github.zi_jing.cuckoolib.gui.widget;

import com.github.zi_jing.cuckoolib.gui.ISyncedWidgetList;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.util.math.Vector2i;

public abstract class WidgetBase implements IWidget {
    protected ModularGuiInfo guiInfo;
    protected ISyncedWidgetList widgetList;
    protected Vector2i size, position;
    protected int id;
    protected boolean isEnable;

    public WidgetBase(Vector2i size, Vector2i position) {
        this.size = size == null ? Vector2i.ORIGIN : size;
        this.position = position == null ? Vector2i.ORIGIN : position;
        this.isEnable = true;
    }

    @Override
    public ModularGuiInfo getGuiInfo() {
        return this.guiInfo;
    }

    @Override
    public void setGuiInfo(ModularGuiInfo info) {
        this.guiInfo = info;
    }

    @Override
    public ISyncedWidgetList getWidgetList() {
        return this.widgetList;
    }

    @Override
    public void setWidgetList(ISyncedWidgetList list) {
        this.widgetList = list;
    }

    @Override
    public int getWidgetId() {
        return this.id;
    }

    @Override
    public void setWidgetId(int id) {
        this.id = id;
    }

    @Override
    public Vector2i getSize() {
        return this.size;
    }

    @Override
    public void setSize(Vector2i size) {
        this.size = size;
    }

    @Override
    public Vector2i getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
    }

    @Override
    public boolean isEnable() {
        return this.isEnable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.isEnable = enable;
    }
}