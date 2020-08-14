package com.hc_android.hc_css.entity;

public class QConfigEntity {

    boolean isAddInput;//是否追加到输入框
    boolean isFoldType = true;//折叠式分组
    boolean isUseCounts;//按使用次数分组
    boolean openTypePick;//打开类型筛选器
    boolean isDisplayCounts;//显示使用次数

    public boolean isAddInput() {
        return isAddInput;
    }

    public void setAddInput(boolean addInput) {
        isAddInput = addInput;
    }

    public boolean isFoldType() {
        return isFoldType;
    }

    public void setFoldType(boolean foldType) {
        isFoldType = foldType;
    }

    public boolean isUseCounts() {
        return isUseCounts;
    }

    public void setUseCounts(boolean useCounts) {
        isUseCounts = useCounts;
    }

    public boolean isOpenTypePick() {
        return openTypePick;
    }

    public void setOpenTypePick(boolean openTypePick) {
        this.openTypePick = openTypePick;
    }

    public boolean isDisplayCounts() {
        return isDisplayCounts;
    }

    public void setDisplayCounts(boolean displayCounts) {
        isDisplayCounts = displayCounts;
    }
}
