package cn.edsmall.skulibrary.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edsmall.skulibrary.bean.SkuAttribute;
import cn.edsmall.skulibrary.bean.SkuJsonEntity;
import cn.edsmall.skulibrary.utils.ViewUtils;
import cn.edsmall.skulibrary.widget.SkuMaxHeightScrollView;

public class SkuSelectScrollView extends SkuMaxHeightScrollView implements SkuItemLayout.OnSkuItemSelectListener {
    private List<SkuJsonEntity> skuList;
    private LinearLayout skuContainerLayout;
    private List<SkuAttribute> selectedAttributeList;  // 存放当前属性选中信息
    private OnSkuListener listener;                    // sku选中状态回调接口

    public SkuSelectScrollView(Context context) {
        super(context);
        init(context, null);
    }


    public SkuSelectScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setFillViewport(true);
        setOverScrollMode(OVER_SCROLL_NEVER);
        skuContainerLayout = new LinearLayout(context, attrs);
        skuContainerLayout.setId(ViewUtils.generateViewId());
        skuContainerLayout.setOrientation(LinearLayout.VERTICAL);
        skuContainerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(skuContainerLayout);
    }

    /**
     * 设置监听接口
     *
     * @param listener {@link OnSkuListener}
     */
    public void setSkuListener(OnSkuListener listener) {
        this.listener = listener;
    }

    public void setInitSkuList(List<SkuJsonEntity> skuList) {
        this.skuList = skuList;
        selectedAttributeList = new LinkedList<>();
        //清空sku容器里面所有的视图
        skuContainerLayout.removeAllViews();
        // 获取分组的sku集合，key是属性groupName(例如颜色) value是name(例如白色)
        Map<String, List<String>> dataMap = getSkuGroupByName(skuList);
        //创建sku属性，
        Iterator<Map.Entry<String, List<String>>> iterator = dataMap.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            //构建sku视图
            SkuItemLayout itemLayout = new SkuItemLayout(getContext());
            itemLayout.setId(ViewUtils.generateViewId());
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            itemLayout.buildItemLayout(index++, entry.getKey(), entry.getValue());
            itemLayout.setListener(this);
            skuContainerLayout.addView(itemLayout);
            // 初始状态下，所有属性信息设置为空
            selectedAttributeList.add(new SkuAttribute(entry.getKey(), ""));
            //设置所有的规格都不可以选中
            clearAllLayoutStatus();
            // 设置是否可点击
            optionLayoutEnableStatus();
            // 设置选中状态
            optionLayoutSelectStatus();
        }
    }

    /**
     * 将sku根据属性名进行分组
     * Key：颜色 value：[白色，红色，黑色]
     *
     * @param list
     * @return 如{ "颜色": {"白色", "红色", "黑色"}, "尺寸": {"M", "L", "XL", "XXL"}}
     */
    private Map<String, List<String>> getSkuGroupByName(List<SkuJsonEntity> list) {
        Map<String, List<String>> dataMap = new LinkedHashMap<>();
        for (SkuJsonEntity sku : list) {
            for (SkuAttribute attribute : sku.getSkuSpecs()) {
                String attributeName = attribute.getGroupName();
                String attributeValue = attribute.getName();
                if (!dataMap.containsKey(attributeName)) {
                    dataMap.put(attributeName, new LinkedList<String>());
                }
                List<String> valueList = dataMap.get(attributeName);
                if (!valueList.contains(attributeValue)) {
                    dataMap.get(attributeName).add(attributeValue);
                }
            }
        }
        return dataMap;
    }

    /**
     * 重置所有属性的选中状态
     */
    private void clearAllLayoutStatus() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            itemLayout.clearItemViewStatus();
        }
    }

    /**
     * 设置所有属性的Enable状态，即是否可点击
     */
    private void optionLayoutEnableStatus() {
        int childCount = skuContainerLayout.getChildCount();
        if (childCount <= 1) {
            //只有一种规格
            optionLayoutEnableStatusSingleProperty();
        } else {
            //多种规格组合
            optionLayoutEnableStatusMultipleProperties();
        }
    }

    private void optionLayoutEnableStatusSingleProperty() {
        SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(0);
        // 遍历sku列表
        for (int i = 0; i < skuList.size(); i++) {
            // 属性值是否可点击flag
            SkuJsonEntity sku = skuList.get(i);
            List<SkuAttribute> attributeBeanList = skuList.get(i).getSkuSpecs();
            if ((isCanBuy(sku) > 0 && sku.getSaleable() == 1)) { //核心条件，判断这个规格是否可选
                String attributeValue = attributeBeanList.get(0).getName(); //规格的值
                itemLayout.optionItemViewEnableStatus(attributeValue);
            }
        }
    }

    private void optionLayoutEnableStatusMultipleProperties() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            // 遍历sku列表
            for (int j = 0; j < skuList.size(); j++) {
                // 属性值是否可点击flag
                boolean flag = false;
                SkuJsonEntity sku = skuList.get(j);
                List<SkuAttribute> attributeBeanList = sku.getSkuSpecs();
                // 遍历选中信息列表
                for (int k = 0; k < selectedAttributeList.size(); k++) {
                    // i = k，跳过当前属性，避免多次设置是否可点击
                    if (i == k) continue;
                    // 选中信息为空，则说明未选中，无法判断是否有不可点击的情形，跳过
                    if ("".equals(selectedAttributeList.get(k).getName())) continue;
                    // 选中信息列表中不包含当前sku的属性，则sku组合不存在，设置为不可点击
                    // 库存为0，设置为不可点击
                    if (!selectedAttributeList.get(k).getName().equals(attributeBeanList.get(k).getName()) || sku.getSaleable() == 0 || isCanBuy(sku) == 0) {
                        flag = true;
                        break;
                    }
                }
                // flag 为false时，可点击
                if (!flag) {
                    String attributeValue = attributeBeanList.get(i).getName();
                    itemLayout.optionItemViewEnableStatus(attributeValue);
                }
            }
        }
    }

    /**
     * isSale==0,说明不可0库存销售。此时要根据库存与起订量的比较来确定该商品可不可以购买，当库存大于或等于起订量时，可以购买
     * isSale==1,说明可以0库存销售。相当于该商品可以无限购买
     *
     * @param sku 商品
     * @return 1可以购买，0不可以购买
     */
    private int isCanBuy(SkuJsonEntity sku) {
        if (sku.getIsSale() == 0) {
            if (sku.getStock() >= sku.getMoq()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    /**
     * 设置所有属性的选中状态
     */
    private void optionLayoutSelectStatus() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            itemLayout.optionItemViewSelectStatus(selectedAttributeList.get(i));
        }
    }

    @Override
    public void onSelect(int position, boolean selected, SkuAttribute attribute) {
        if (selected) {
            // 选中，保存选中信息
            selectedAttributeList.set(position, attribute);
        } else {
            // 取消选中，清空保存的选中信息
            selectedAttributeList.get(position).setName("");
        }
        clearAllLayoutStatus();
        // 设置是否可点击
        optionLayoutEnableStatus();
        // 设置选中状态
        optionLayoutSelectStatus();
        if (isSkuSelected()) {
            //选中某个sku
            listener.onSkuSelected(getSelectedSku());
        } else if (selected) {
            //选中某一个属性
            listener.onSelect(attribute);
        } else {
            //取消选中的某一个属性
            listener.onUnselected(attribute);
        }
    }
    /**
     * 获取第一个未选中的属性名
     *
     * @return
     */
    public String getFirstUnelectedAttributeName() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            if (!itemLayout.hasSelected()) {
                return itemLayout.getAttributeName();
            }
        }
        return "";
    }
    /**
     * 获取选中的Sku
     *
     * @return
     */
    private SkuJsonEntity getSelectedSku() {

        // 判断是否有选中的sku
        if (!isSkuSelected()) {
            return null;
        }
        for (SkuJsonEntity sku : skuList) {
            List<SkuAttribute> attributeList = sku.getSkuSpecs();
            // 将sku的属性列表与selectedAttributeList匹配，完全匹配则为已选中sku
            boolean flag = true;
            for (int i = 0; i < attributeList.size(); i++) {
                if (!isSameSkuAttribute(attributeList.get(i), selectedAttributeList.get(i))) {
                    flag = false;
                }
            }
            if (flag) {
                return sku;
            }
        }
        return null;
    }
    /**
     * 设置选中的sku
     *
     * @param sku
     */
    public void setSelectedSku(SkuJsonEntity sku) {
        selectedAttributeList.clear();
        for (SkuAttribute attribute : sku.getSkuSpecs()) {
            selectedAttributeList.add(new SkuAttribute(attribute.getGroupName(), attribute.getName()));
        }
        // 清除所有选中状态
        clearAllLayoutStatus();
        // 设置是否可点击
        optionLayoutEnableStatus();
        // 设置选中状态
        optionLayoutSelectStatus();
    }

    private boolean isSameSkuAttribute(SkuAttribute skuAttribute, SkuAttribute selectedSkuAttribute) {
     return skuAttribute.getGroupName().equals(selectedSkuAttribute.getGroupName())
                && skuAttribute.getName().equals(selectedSkuAttribute.getName());
    }

    /**
     * 是否有sku选中
     *
     * @return false没有选中sku, true选中了sku
     */
    private boolean isSkuSelected() {
        for (SkuAttribute attribute : selectedAttributeList) {
            //发现selectedAttributeList集合中的SkuAttribute对象其中有一个getName()为空
            //说明没有组成一个完整的sku,就当作没有sku选中
            if (TextUtils.isEmpty(attribute.getName())) {
                return false;
            }
        }
        return true;
    }
}
