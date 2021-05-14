package cn.edsmall.productsku;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;


import com.bumptech.glide.Glide;

import java.util.List;

import cn.edsmall.productsku.bean.Product;
import cn.edsmall.skulibrary.bean.SkuAttribute;
import cn.edsmall.skulibrary.bean.SkuJsonEntity;
import cn.edsmall.skulibrary.utils.NumberUtils;
import cn.edsmall.skulibrary.view.OnSkuListener;
import cn.edsmall.skulibrary.view.SkuSelectScrollView;

public class ProductSkuDialog extends Dialog {
    private Context mContext;
    private Button submit;
    private SkuSelectScrollView skuScrollView;
    private TextView mSelectedSkuInfo, mSellingPrice, mQuantity, mMinus, mPlus, mMoq, mTvTitle, mProductPrice;
    private ImageView mSkuPic;
    private EditText mSkuInput;
    //选中的sku
    private SkuJsonEntity selectedSku;
    private OnSelectSkuListener mOnSelectSkuListener;
    private Product mProduct;
    private String priceFormat;
    private String stockQuantityFormat;

    public ProductSkuDialog(@NonNull Context context) {
        this(context, R.style.CommonBottomDialogStyle);
        this.mContext = context;
    }

    public ProductSkuDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView();
        initListener();
    }


    private void initView() {
        setContentView(cn.edsmall.skulibrary.R.layout.dialog_product_sku);
        submit = findViewById(R.id.btn_submit);
        skuScrollView = findViewById(R.id.scroll_sku_list);
        mSelectedSkuInfo = findViewById(R.id.tv_sku_info);
        mSellingPrice = findViewById(R.id.tv_sku_selling_price);
        mQuantity = findViewById(R.id.tv_sku_quantity);
        mSkuPic = findViewById(R.id.iv_sku_logo);
        mSkuInput = findViewById(R.id.et_sku_quantity_input);
        mMinus = findViewById(R.id.btn_sku_quantity_minus);
        mPlus = findViewById(R.id.btn_sku_quantity_plus);
        mMoq = findViewById(R.id.tv_moq);
        mTvTitle = findViewById(R.id.tv_product_title);
        mProductPrice = findViewById(R.id.tv_sku_selling_price_unit);
    }

    private void initListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSelectSkuListener.onSelectSku(selectedSku, 1);
            }
        });
        mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = mSkuInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > selectedSku.getMoq()) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    mSkuInput.setText(newQuantity);
                    mSkuInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = mSkuInput.getText().toString();
                if (TextUtils.isEmpty(quantity) || selectedSku == null) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < selectedSku.getStock() || selectedSku.getIsSale() == 1) {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    mSkuInput.setText(newQuantity);
                    mSkuInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });
        skuScrollView.setSkuListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                selectedSku = null;
                mMoq.setText("最低起订量:---");
                String firstUnselectedAttributeName = skuScrollView.getFirstUnelectedAttributeName();
                mSelectedSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
                Glide.with(mContext).load(mProduct.getSkuJson().get(0).getMainImg()).into(mSkuPic);
                mSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(mProduct.getSalePrice())));
                mQuantity.setText(String.format(stockQuantityFormat, mProduct.getStock()));
                submit.setEnabled(false);
                String quantity = mSkuInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName = skuScrollView.getFirstUnelectedAttributeName();
                mSelectedSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
            }

            @Override
            public void onSkuSelected(SkuJsonEntity sku) {
                selectedSku = sku;
                List<SkuAttribute> attributeList = selectedSku.getSkuSpecs();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("　");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append("\"" + attribute.getName() + "\"");
                }
                mSelectedSkuInfo.setText("已选：" + builder.toString());
                Glide.with(mContext).load(selectedSku.getMainImg()).into(mSkuPic);
                mSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getSalePrice())));
                mQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStock()));
                submit.setEnabled(true);
                mSkuInput.setText(String.valueOf(selectedSku.getMoq()));
                mMoq.setText("最低起订量:" + selectedSku.getMoq());
                String quantity = mSkuInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });

    }

    public void setData(Product product, OnSelectSkuListener onSelectSkuListener) {
        this.mOnSelectSkuListener = onSelectSkuListener;
        this.mProduct = product;
        priceFormat = mContext.getString(R.string.comm_price_format);
        stockQuantityFormat = mContext.getString(R.string.product_detail_sku_stock);
        initSetSku();
        updateQuantityOperator(1);
    }

    private void initSetSku() {
        mTvTitle.setText(mProduct.getSubTitle());
        skuScrollView.setInitSkuList(mProduct.getSkuJson());

        for (SkuJsonEntity sku : mProduct.getSkuJson()) {
            if ((sku.getStock() >= sku.getMoq() || sku.getIsSale() == 1) && sku.getSaleable() == 1) {
                selectedSku = sku;
                // 选中第一个sku
                skuScrollView.setSelectedSku(selectedSku);
                Glide.with(mContext).load(selectedSku.getMainImg()).into(mSkuPic);
                mSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getSalePrice())));
                mQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStock()));
                mSkuInput.setText(String.valueOf(selectedSku.getMoq()));
                mMoq.setText("最低起订量:" + selectedSku.getMoq());
//            binding.btnSubmit.setEnabled(selectedSku.getStockQuantity() > 0);
                List<SkuAttribute> attributeList = selectedSku.getSkuSpecs();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("　");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append("\"" + attribute.getName() + "\"");
                }
                mSelectedSkuInfo.setText("已选：" + builder.toString());
                return;
            }
        }
        Glide.with(mContext).load(mProduct.getSkuJson().get(0).getMainImg()).into(mSkuPic);
        mSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(mProduct.getSalePrice())));
        mQuantity.setText(String.format(stockQuantityFormat, mProduct.getStock()));
        submit.setEnabled(false);
        skuScrollView.clearAllLayoutStatus();
        mSelectedSkuInfo.setText("请选择：" + mProduct.getSkuJson().get(0).getSkuSpecs().get(0).getGroupName());
        //去第一个来做默认选中
       /* SkuJsonEntity firstSku = mProduct.getSkuJson().get(0);
        if (firstSku.getStock() >= firstSku.getMoq()||firstSku.getIsSale()==1 && firstSku.getSaleable() == 1) {
            selectedSku = firstSku;
            // 选中第一个sku
            skuScrollView.setSelectedSku(selectedSku);
            Glide.with(mContext).load(selectedSku.getMainImg()).into(mSkuPic);
            mSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getSalePrice())));
            mQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStock()));
            mSkuInput.setText(String.valueOf(selectedSku.getMoq()));
            mMoq.setText("最低起订量:"+selectedSku.getMoq());
           //090binding.btnSubmit.setEnabled(selectedSku.getStockQuantity() > 0);
            List<SkuAttribute> attributeList = selectedSku.getSkuSpecs();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("　");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append("\"" + attribute.getName() + "\"");
            }
            mSelectedSkuInfo.setText("已选：" + builder.toString());
        } else {
            Glide.with(mContext).load(mProduct.getSkuJson().get(0).getMainImg()).into(mSkuPic);
            mSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(mProduct.getSalePrice())));
            mQuantity.setText(String.format(stockQuantityFormat, mProduct.getStock()));
            submit.setEnabled(false);
            mSelectedSkuInfo.setText("请选择：" + mProduct.getSkuJson().get(0).getSkuSpecs().get(0).getGroupName());
        }*/
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
            mMinus.setEnabled(false);
            mPlus.setEnabled(false);
            mSkuInput.setEnabled(false);
        } else {
            if (newQuantity <= selectedSku.getMoq()) {
                if (selectedSku.getMoq() == selectedSku.getStock()) {
                    mMinus.setEnabled(false);
                    mPlus.setEnabled(false);
                } else {
                    mMinus.setEnabled(false);
                    mPlus.setEnabled(true);
                }
            } else if (newQuantity >= selectedSku.getStock() && selectedSku.getIsSale() == 0) {
                mMinus.setEnabled(true);
                mPlus.setEnabled(false);
            } else {
                mMinus.setEnabled(true);
                mPlus.setEnabled(true);
            }
            mSkuInput.setEnabled(true);
        }
    }

    /**
     * 提供给调用这实现接口
     */
    public interface OnSelectSkuListener {
        void onSelectSku(SkuJsonEntity sku, int quantity);
    }
}
