package com.dongnao.douyin.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dongnao.douyin.R;
import com.dongnao.douyin.camera.record.MediaRecorder;
import com.dongnao.douyin.camera.widget.DouyinView;
import com.dongnao.douyin.camera.widget.RecordButton;
import com.dongnao.douyin.video.VideoActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    DouyinView douyinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermision();

    }

    private void setContentView() {
        setContentView(R.layout.activity_main);
        douyinView = findViewById(R.id.douyinView);
        RecordButton recordButton = findViewById(R.id.btn_record);
        recordButton.setOnRecordListener(new RecordButton.OnRecordListener() {
            /**
             * 开始录制
             */
            @Override
            public void onRecordStart() {
                douyinView.startRecord();
            }
            /**
             * 停止录制
             */
            @Override
            public void onRecordStop() {
                douyinView.stopRecord();
            }
        });
        RadioGroup radioGroup = findViewById(R.id.rg_speed);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * 选择录制模式
             * @param group
             * @param checkedId
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_extra_slow: //极慢
                        douyinView.setSpeed(DouyinView.Speed.MODE_EXTRA_SLOW);
                        break;
                    case R.id.rb_slow:
                        douyinView.setSpeed(DouyinView.Speed.MODE_SLOW);
                        break;
                    case R.id.rb_normal:
                        douyinView.setSpeed(DouyinView.Speed.MODE_NORMAL);
                        break;
                    case R.id.rb_fast:
                        douyinView.setSpeed(DouyinView.Speed.MODE_FAST);
                        break;
                    case R.id.rb_extra_fast: //极快
                        douyinView.setSpeed(DouyinView.Speed.MODE_EXTRA_FAST);
                        break;
                }
            }
        });

        ((CheckBox)findViewById(R.id.beauty)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                douyinView.enableBeauty(isChecked);
            }
        });

        ((CheckBox)findViewById(R.id.bigEye)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                douyinView.enableBigEye(isChecked);
            }
        });
        ((CheckBox)findViewById(R.id.stick)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                douyinView.enableStick(isChecked);
            }
        });
        /**
         * 录制完成
         */
        douyinView.setOnRecordFinishListener(new MediaRecorder.OnRecordFinishListener() {
            @Override
            public void onRecordFinish(final String path) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                        intent.putExtra("path",path);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void requestPermision() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                return;
            }
            else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                return;
            }
        }
        setContentView();
    }
    volatile boolean granted=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
             granted=true;
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" +
                        grantResults[i]);
                if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    granted=false;
                    break;
                }
            }
            if(granted){
                setContentView();
            }else{
                Toast.makeText(this,"不给权限无法使用哦！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            douyinView.switchCamera();
        }
        return super.onTouchEvent(event);
    }
}
