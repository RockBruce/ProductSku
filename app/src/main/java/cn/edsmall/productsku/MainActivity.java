package cn.edsmall.productsku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import cn.edsmall.productsku.bean.Product;
import cn.edsmall.productsku.utils.LocalDateConfig;
import cn.edsmall.skulibrary.bean.SkuJsonEntity;
import cn.edsmall.skulibrary.view.SkuSelectScrollView;

public class MainActivity extends Activity {
    private TextView sku;
    private ProductSkuDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sku = findViewById(R.id.tv_select_sku);

        sku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductDialog();
            }
        });
        initView();
    }

    private void initView() {
    }

    private void showProductDialog() {
        if (dialog == null) {
            dialog = new ProductSkuDialog(this);
            dialog.setData(LocalDateConfig.getsProduct()/*Product.get(this)*/, new ProductSkuDialog.OnSelectSkuListener() {
                @Override
                public void onSelectSku(SkuJsonEntity sku, int quantity) {

                }
            });

        }
        dialog.show();

    }

}