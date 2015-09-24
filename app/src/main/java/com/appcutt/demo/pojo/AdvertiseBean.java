package com.appcutt.demo.pojo;

/**
 * Created by ouyangjinmiao on 5/6/15.
 */
public class AdvertiseBean {

    public String value;

    // 跳转到大类的子分类中用
    public String subID;

    public String photoURL;

    public String linkURL;

    public String description;

    public String name;

    public double radio; // 广告高宽比，用来决定广告的展示高度，同一位置广告的高宽比应该一致

    // 打开方式
    public int openType = -1;

    @Override
    public boolean equals(Object o) {
        AdvertiseBean newData = (AdvertiseBean) o;
        return newData.value.equals(value) && openType == newData.openType;
    }

}
