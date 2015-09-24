package com.appcutt.demo.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.appcutt.demo.R;
import com.appcutt.demo.adapter.HomeAdapter;
import com.appcutt.demo.pojo.AdvertiseBean;
import com.appcutt.demo.request.AbsBusinessRequest;
import com.appcutt.demo.request.AdvertiseRequest;
import com.appcutt.demo.request.HomeRequest;
import com.appcutt.demo.utils.SystemUtil;
import com.appcutt.demo.widget.AdvertiseViewPager;
import com.appcutt.libs.pulltorefresh.PullToRefreshBase;
import com.appcutt.libs.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.appcutt.libs.pulltorefresh.PullToRefreshListView;

import java.util.*;

public class MainActivity extends BaseActivity implements
        OnRefreshListener {

    private Context mContext;

    // 列表
    private PullToRefreshListView mListView;
    private HomeAdapter mAdapter;

    // 列表header
    private ViewGroup mHeaderView;

    // 广告
    private AdvertiseViewPager mADViewPager;
    private List<AdvertiseBean> mAdvertiseBeans;

    private String[] datas = {"哈哈哈", "hahah嘻嘻嘻4545", "huhudhushuhsu", "87878hushuhsushus"};


    private final static int MESSAGE_LOAD_REFRESH = 100;

    private final static int MESSAGE_LOAD_MORE = 101;

    private final static int MESSAGE_LOAD_AD = 102; // 广告

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mContext = this;

        mListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);

        /** 加载header部分的数据：banner, 扫货直播 */
        LayoutInflater inflater = LayoutInflater.from(this);
        mHeaderView = (ViewGroup) inflater.inflate(R.layout.ac_home_header, null);
        mListView.addHeaderView(mHeaderView);

        // 在设置Adapter之前先add header头用于显示广告
        initAD();


        // 下拉刷新
        mListView.setOnRefreshListener(this);

        mAdapter = new HomeAdapter(this, new ArrayList(Arrays.asList(datas)));
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int p = position - mListView.getHeaderViewsCount();

                if (p < 0) {
                    return;
                }

                Toast.makeText(mContext, "headers : "+mListView.getHeaderViewsCount()+", click "+p, Toast.LENGTH_SHORT).show();
            }
        });

        loadData(MESSAGE_LOAD_REFRESH);
    }

    /**
     * 加载数据
     *
     * @param what
     */
    private void loadData(int what) {

        Map<String, String> params = new HashMap<String, String>();
        Message message = mRequestHandler.obtainMessage(what);
        AbsBusinessRequest request = new HomeRequest(this, params, message);
        request.execute();

    }

    @Override
    protected void onSuccess(int what, Object response) {

        Toast.makeText(mContext, "onSuccess", Toast.LENGTH_SHORT).show();

        switch (what) {
            case MESSAGE_LOAD_REFRESH:
            case MESSAGE_LOAD_MORE:

                // Call onRefreshComplete when the list has been refreshed.
                mListView.onRefreshComplete();

                List<String> ds = (ArrayList<String>) response;

                mAdapter.clearData();
                mAdapter.addDatas(ds);

                break;
            case MESSAGE_LOAD_AD:

                AdvertiseRequest.AdvertiseResult result = (AdvertiseRequest.AdvertiseResult)response;

                onLoadAdverseSuccess(result.advertiseBeans);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onFail(int what, int error) {
        super.onFail(what, error);

        Toast.makeText(mContext, "onFail", Toast.LENGTH_SHORT).show();
    }

    /**
     * 广告区域的显示
     */
    private void initAD() {
        // 添加广告条，如果确认没有广告，则不用显示广告条
        mADViewPager = (AdvertiseViewPager) mHeaderView.findViewById(R.id.adviewpager);

        // 加载广告
        loadAdvertise(); // 放到商品数据的后面请求
    }

    /**
     * 加载数据
     *
     * @param what
     */
    private void loadAdvertise() {

        Map<String, String> params = new HashMap<String, String>();
        Message message = mRequestHandler.obtainMessage(MESSAGE_LOAD_AD);
        AbsBusinessRequest request = new AdvertiseRequest(this, params, message);
        request.execute();

    }

    /**
     * 显示广告注意启动轮播
     *
     * @param data
     */
    private void onLoadAdverseSuccess(List<AdvertiseBean> data) {

        if (mADViewPager != null && data != null && data.size() == 0) {
            mADViewPager.setVisibility(View.GONE);
            mAdvertiseBeans = null;
            return;
        }

        mAdvertiseBeans = data;

        AdvertiseViewPager.AdvertisePagerAdapter adAdapter = new AdvertiseViewPager.AdvertisePagerAdapter(
                this, data);
        mADViewPager.setPagerAdapter(adAdapter);
        mADViewPager.setShowIndicator(true);

        // 启动轮播动画
        mADViewPager.setVisibility(View.VISIBLE);
        if (mAdapter != null && mAdapter.getCount() > 0) {
            mADViewPager.startAutoPlay();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        loadData(MESSAGE_LOAD_REFRESH);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            SystemUtil.showExitDialog(this);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
