package com.dongnao.douyin.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.dongnao.douyin.R;
import com.dongnao.douyin.camera.record.MediaRecorder;
import com.dongnao.douyin.camera.widget.DouyinView;
import com.dongnao.douyin.camera.widget.RecordButton;
import com.dongnao.douyin.video.VideoActivity;

public class MainActivity extends AppCompatActivity {
    DouyinView douyinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            douyinView.switchCamera();
        }
        return super.onTouchEvent(event);
    }
}
