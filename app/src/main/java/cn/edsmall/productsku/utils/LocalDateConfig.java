package cn.edsmall.productsku.utils;

import android.content.res.AssetManager;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edsmall.productsku.bean.Product;


/**
 * 本地假数据
 */
public class LocalDateConfig {
    private static Product sProduct;
    public  static Product getsProduct(){
        if (sProduct==null){
            String content = parseFile("product.json");
            sProduct=  new Gson().fromJson(content, new TypeToken<Product>() {
            }.getType());
        }
        return sProduct;
    }
    private static String parseFile(String fileName) {
        AssetManager assets = AppGlobals.getApplication().getResources().getAssets();
        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            stream = assets.open(fileName);
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
