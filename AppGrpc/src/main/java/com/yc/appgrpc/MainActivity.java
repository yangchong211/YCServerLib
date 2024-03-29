package com.yc.appgrpc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yc.roundcorner.view.RoundTextView;
import com.yc.toastutils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private RoundTextView tvRpc1;
    private RoundTextView tvRpc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpc_main);
        tvRpc1 = findViewById(R.id.tv_rpc_1);
        tvRpc2 = findViewById(R.id.tv_rpc_2);
        tvRpc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreeterActivity.startActivity(MainActivity.this);
            }
        });
        tvRpc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showRoundRectToast("效率测试");
                new ProtoJsonTest.JsonProtoTest().gson();
                new ProtoJsonTest.JsonProtoTest().protoTest();
                new ProtoJsonTest.JsonProtoTest().testSearchRequest();
            }
        });
    }
}