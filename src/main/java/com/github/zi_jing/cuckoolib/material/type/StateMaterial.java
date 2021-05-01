package com.github.zi_jing.cuckoolib.material.type;

import com.github.zi_jing.cuckoolib.material.MatterState;

public class StateMaterial extends Material {
    /**
     * meltingPoint &lt; boilingPoint &lt; plasmaPoint, 均使用热力学温度, 负数代表不存在该形态(如meltingPoint =
     * -1代表不存在液态), 若首个非负数据为0则不存在固态
     */
    public float meltingPoint = -1, boilingPoint = -1, plasmaPoint = -1;

    public StateMaterial(int id, String name, int color) {
        super(id, name, color);
    }

    public StateMaterial(
            int id, String name, int color, float meltingPoint, float boilingPoint, float plasmaPoint) {
        this(id, name, color);
        this.setPoint(meltingPoint, boilingPoint, plasmaPoint);
    }

    public void setPoint(float meltingPoint, float boilingPoint, float plasmaPoint) {
        if (!this.validatePoint(meltingPoint, boilingPoint, plasmaPoint)) {
            throw new IllegalArgumentException(
                    "The matter state points [ "
                            + meltingPoint
                            + ", "
                            + boilingPoint
                            + ", "
                            + plasmaPoint
                            + " ] is invalied");
        }
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.plasmaPoint = plasmaPoint;
    }

    public boolean validatePoint(float meltingPoint, float boilingPoint, float plasmaPoint) {
        float[] num = new float[3];
        int idx = 0;
        if (meltingPoint >= 0) {
            num[idx++] = meltingPoint;
        }
        if (boilingPoint >= 0) {
            num[idx++] = boilingPoint;
        }
        if (plasmaPoint >= 0) {
            num[idx++] = plasmaPoint;
        }
        for (int i = 1; i < idx; i++) {
            if (num[i] < num[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public boolean existState(MatterState state) {
        switch (state) {
            case SOLID:
                return this.meltingPoint != 0 && this.boilingPoint != 0 && this.plasmaPoint != 0;
            case LIQUID:
                return this.meltingPoint >= 0;
            case GAS:
                return this.boilingPoint >= 0;
            case PLASMA:
                return this.plasmaPoint >= 0;
            default:
                return false;
        }
    }
}
