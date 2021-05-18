package com.vlip.app.network;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vlip.app.BaseApplication;
import com.vlip.kit.ToastUtils;

public class WechatLogin {

    private static volatile WechatLogin mLogin;

    public static WechatLogin getInstance() {
        if (mLogin == null) {
            synchronized (WechatLogin.class) {
                if (mLogin == null) {
                    mLogin = new WechatLogin();
                }
            }
        }
        return mLogin;
    }

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxf56649a440610b3a";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    public WechatLogin() {
        regToWx();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(BaseApplication.getInstance(), APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

    }

    public  void login() {
//        if (!api.isWXAppInstalled()) {
//            Toast.makeText(mContext, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
//            return;
//        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test_neng";
        boolean res=api.sendReq(req);
        ToastUtils.showToast(res+"");
    }
}
