package com.vlip.app;

/**
 * 常量
 *
 * @author zm
 */
public interface Constants {

    boolean TEST = true;

    String BASE_URL = "https://134.175.188.57:8444/app/api/v1/";
    String IMAGE_URL = "https://134.175.188.57:8445/upload/files/";

//    String TEST_BASE_URL = "http://1.116.52.75:84/";
    String TEST_BASE_URL = "http://219.139.35.25:5785/";
//    String TEST_BASE_URL = "http://219.139.35.25:5785/";
    String TEST_IMAGE_URL = "http://192.168.43.55:8082/upload/files/";

    String INTENT_KEY1 = "INTENT_KEY1";

    String INTENT_KEY2 = "INTENT_KEY2";

    String SP_USER_INFO = "SP_USER_INFO";


    String WEB_ABOUT_PATH = "me/aboutUs";
    String WEB_HELP_PATH = "me/help";
    String WEB_SERVICE_PATH = "me/service";

    int LIMIT = 20;

    static String getBaseUrl() {
        if (TEST) {
            return TEST_BASE_URL;
        } else {
            return BASE_URL;
        }
    }

    static String getImageUrl() {
        if (TEST) {
            return TEST_IMAGE_URL;
        } else {
            return IMAGE_URL;
        }
    }

}
