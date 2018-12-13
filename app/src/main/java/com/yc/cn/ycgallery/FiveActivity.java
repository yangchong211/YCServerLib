package com.yc.cn.ycgallery;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.yc.cn.ycgallerylib.recyclerView.GalleryRecyclerView;
import com.yc.cn.ycgallerylib.recyclerView.OnGalleryListener;

import java.util.ArrayList;


public class FiveActivity extends AppCompatActivity {

    private static final String TAG = "FiveActivity";
    private GalleryRecyclerView mRecyclerView;
    private ArrayList<Integer> data = new ArrayList<>();
    private TextView tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        tag = findViewById(R.id.tag);
        initRecyclerView();

    }

    @SuppressLint("SetTextI18n")
    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        final SnapAdapter adapter = new SnapAdapter(this);
        adapter.setData(getData());
        tag.setText(1 + "/" + adapter.getData().size());
        mRecyclerView
                //设置滑动速度
                .setFlingSpeed(10000)
                //设置adapter
                .setDataAdapter(adapter)
                //设置选中的索引
                .setSelectedPosition(2)
                //设置横向或者竖向，注意需要限制传入类型
                .setOrientation(RecyclerView.HORIZONTAL)
                //设置滚动监听事件
                .setOnGalleryListener(new OnGalleryListener() {
                    @Override
                    public void onInitComplete() {
                        Log.e(TAG,"onInitComplete初始化完成");
                    }

                    @Override
                    public void onPageRelease(boolean isNext,int position) {
                        Log.e(TAG,"释放的监听，释放位置:"+position +" 下一页:"+isNext);
                        if (isNext){
                            Log.e(TAG,"释放的监听，释放位置:"+position +" 下一页:"+isNext);
                        }else {
                            Log.e(TAG,"释放的监听，释放位置:"+position +" 上一页:"+isNext);
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPageSelected(int position) {
                        Log.e(TAG,"释放的监听，释放位置:"+position +" 当前页:"+position);
                        tag.setText((position + 1) + "/" + adapter.getData().size());
                    }
                })
                //装载
                .setUp();
    }


    private ArrayList<Integer> getData(){
        data.clear();
        TypedArray bannerImage = getResources().obtainTypedArray(R.array.gallery_image);
        for (int i = 0; i < 12 ; i++) {
            int image = bannerImage.getResourceId(i, R.drawable.image1);
            data.add(image);
        }
        bannerImage.recycle();
        return data;
    }



}
