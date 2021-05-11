package cn.edsmall.skulibrary.view;


import cn.edsmall.skulibrary.bean.SkuAttribute;
import cn.edsmall.skulibrary.bean.SkuJsonEntity;

/**
 */
public interface OnSkuListener {
    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(SkuAttribute unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(SkuAttribute selectAttribute);

    /**
     * sku选中
     *
     * @param sku
     */
    void onSkuSelected(SkuJsonEntity sku);
}