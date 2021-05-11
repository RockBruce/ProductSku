package cn.edsmall.skulibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cn.edsmall.skulibrary.R;
import cn.edsmall.skulibrary.utils.ScreenUtils;


/**
 *
 */

public class SkuItemView extends androidx.appcompat.widget.AppCompatTextView {
    private String attributeValue;

    public SkuItemView(Context context) {
        super(context);
        init(context);
    }

    public SkuItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SkuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.drawable.sku_item_selector);
        setTextColor(getResources().getColorStateList(R.color.sku_item_text_selector));
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
        setSingleLine();
        setGravity(Gravity.CENTER);
        setPadding(ScreenUtils.dp2PxInt(context, 10), 0, ScreenUtils.dp2PxInt(context, 10), 0);

        setMinWidth(ScreenUtils.dp2PxInt(context, 45));
        setMaxWidth(ScreenUtils.dp2PxInt(context, 200));
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        setText(attributeValue);
    }
}
