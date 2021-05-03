package com.vlip.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 站点
 */
public class Site {
    public float lat;
    public float lon;
    public String title;
    public String subTitle;
    public String phone;


    public static List<Site> list() {
        ArrayList<Site> list = new ArrayList<>();
        Site site1 = new Site();
        site1.title = "育才小区";
        site1.subTitle = "育才路九巷17号附近";
        site1.lat = 30.38627f;
        site1.lon = 111.462478f;
        list.add(site1);
        Site site2 = new Site();
        site2.title = "雅思国际广场";
        site2.subTitle = "湖北省宜昌市宜都市长江大道63号";
        site2.lat = 30.386744f;
        site2.lon = 111.456764f;
        list.add(site2);
        Site site3 = new Site();
        site3.title = "宜都一中";
        site3.subTitle = "湖北省宜昌市宜都市园林大道93号";
        site3.lat = 30.381406f;
        site3.lon = 111.462302f;
        list.add(site3);
        return list;
    }
}
