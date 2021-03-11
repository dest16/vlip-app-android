package com.vlip.app.kit;

import android.content.res.AssetManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.vlip.app.BaseApplication;
import com.vlip.app.Constants;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Member;
import com.vlip.app.bean.SpecificationItem;
import com.vlip.app.bean.SpecificationValue;
import com.vlip.app.network.GlideApp;
import com.vlip.kit.BigDecimalUtils;
import com.vlip.kit.FastjsonUtils;
import com.vlip.kit.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AppUtils {

    public static String fromRMB(String text) {
        BigDecimal decimal = new BigDecimal(10);
        String value = decimal.pow(4).toPlainString();
        return BigDecimalUtils.mul(text, value);
    }

    public static String toRMB(String text) {
        BigDecimal decimal = new BigDecimal(10);
        String value = decimal.pow(4).toPlainString();
        return BigDecimalUtils.div(text, value, 2);
    }

    public static String toRMBFormat(String text) {
        return "￥" + toRMB(text);
    }

    public static void saveMember(Member member) {
        SPUtils.putJSONCache(BaseApplication.getInstance(), Constants.SP_USER_INFO, FastjsonUtils.toString(member));
    }

    public static Member getMember() {
        String json = SPUtils.getJSONCache(BaseApplication.getInstance(), Constants.SP_USER_INFO);
        if (!TextUtils.isEmpty(json)) {
            return FastjsonUtils.toObject(json, Member.class);
        } else {
            return null;
        }
    }

    public static boolean isLogin() {
        return getMember() != null;
    }

    public static String formatPhone(String phone) {
        int position = phone.indexOf(")");
        if (position >= 0) {
            phone = phone.substring(position);
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    public static String getSelectSpecValue(String specificationValue) {
        if (specificationValue == null) return "";
        List<SpecificationValue> specValueList = FastjsonUtils.toList(specificationValue, SpecificationValue.class);
        StringBuilder specValues = new StringBuilder();
        assert specValueList != null;
        for (SpecificationValue item : specValueList) {
            specValues.append(",");
            specValues.append(item.value);
        }
        return specValues.substring(1);
    }

    public static String getSelectSpecItem(String specificationItem) {
        if (specificationItem == null) return "";
        List<SpecificationItem> specValueList = FastjsonUtils.toList(specificationItem, SpecificationItem.class);
        StringBuilder specValues = new StringBuilder();
        assert specValueList != null;
        for (SpecificationItem item : specValueList) {
            if (item.options != null && item.options.size() > 0) {
                specValues.append(",");
                specValues.append(item.name);
            }
        }
        return specValues.substring(1);
    }

    public static boolean isValidateMobile(String phone) {
        String regex = "1[34578]([0-9]){9}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static void exitLogin() {
        Observable.empty()
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        BaseApplication.getInstance().getCartDao().deleteAll();
                        SPUtils.putJSONCache(BaseApplication.getInstance(), Constants.SP_USER_INFO, "");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        EventBus.getDefault().post(new Event.LoginEvent());
                        EventBus.getDefault().post(new Event.CartEvent());
                    }
                })
                .subscribe();
    }

    public static int getColor(int colorId) {
        return ContextCompat.getColor(BaseApplication.getInstance(), colorId);
    }

    public static void loadImage(String url, ImageView image){
        GlideApp.with(BaseApplication.getInstance()).load(Constants.getImageUrl() + url).centerCrop().into(image);
    }

    public static String readAssetsText(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = BaseApplication.getInstance().getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
