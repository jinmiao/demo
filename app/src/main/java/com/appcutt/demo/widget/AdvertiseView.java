package com.appcutt.demo.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.appcutt.demo.pojo.AdvertiseBean;

/**
 * Created by ouyangjinmiao on 5/6/15.
 */
public class AdvertiseView extends ImageView implements View.OnClickListener {

    private AdvertiseBean mData;

    public AdvertiseView(Context context) {

        super(context);

        setScaleType(ImageView.ScaleType.CENTER_CROP);

        setOnClickListener(this);
    }

    public void setData(AdvertiseBean data) {
        this.mData = data;
    }

    @Override
    public void onClick(View v) {

        if (mData == null) {
            return;
        }

//        AbsJumpEntity.JumpEntityInfo jumpEntityInfo = new AbsJumpEntity.JumpEntityInfo();
//        jumpEntityInfo.value = mData.value;
//        jumpEntityInfo.subId = mData.subID;
//        jumpEntityInfo.name = mData.name;
//        jumpEntityInfo.description = mData.description;
//        jumpEntityInfo.jumpType = mData.openType;
//        jumpEntityInfo.startArea = "BANNER";
//
//        AbsJumpEntity jumpEntity = JumpEntityFactory.createJumpEntity(getContext(), jumpEntityInfo);
//        if (jumpEntity != null) {
//            jumpEntity.jumpFromAD();
//        }

    }
}
