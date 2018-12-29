package cn.jakemesdg.commondialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.jake.share.frdialog.dialog.FRDialog;
import cn.jake.share.frdialog.recyclerview.FRBaseDialogAdapter;
import cn.jake.share.frdialog.recyclerview.FRBaseDialogViewHolder;
import cn.jakemesdg.commondialog.service.DialogService;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int RC_OVERLAY = 0x01101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListener();
    }

    private void initListener() {
        findViewById(R.id.am_common_dialog).setOnClickListener(this);
        findViewById(R.id.am_md_dialog).setOnClickListener(this);
        findViewById(R.id.am_from_bottom_dialog).setOnClickListener(this);
        findViewById(R.id.am_recyclerview_dialog).setOnClickListener(this);
        findViewById(R.id.am_start_service).setOnClickListener(this);
        findViewById(R.id.am_stop_service).setOnClickListener(this);
        findViewById(R.id.am_offset_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.am_common_dialog:
                showCommonDialog();
                break;
            case R.id.am_md_dialog:
                showMDDialog();
                break;
            case R.id.am_from_bottom_dialog:
                showFromBottomDialog();
                break;
            case R.id.am_recyclerview_dialog:
                showRecyclerViewDialog();
                break;
            case R.id.am_start_service:
                // 在Service中弹出dialog需要适配
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                    openOverlaySettings();
                } else {
                    startService(new Intent(this, DialogService.class));
                }
                break;
            case R.id.am_stop_service:
                stopService(new Intent(this, DialogService.class));
                break;
            case R.id.am_offset_dialog:
                startActivity(new Intent(this, DialogOffsetActivity.class));
                break;
        }
    }

    private void showRecyclerViewDialog() {
        List<Object> mDataList = new ArrayList<>();
        mDataList.add(new TestDataBean("张三", "2018-09-11 14:00"));
        mDataList.add(new TestDataBean("李四", "2018-09-11 11:00"));
        mDataList.add(new TestDataBean("王五", "2018-09-11 12:00"));
        mDataList.add(new TestDataBean("李四", "2018-09-11 13:00"));
        mDataList.add(new TestDataBean("张三", "2018-09-11 16:00"));
        mDataList.add(new TestDataBean("王五", "2018-09-11 15:00"));


        final FRDialog dialog = new FRDialog.RecyclerViewBuilder(this)
                .setLayoutManager(new LinearLayoutManager(MainActivity.this))
                .setAdapter(new FRBaseDialogAdapter<TestDataBean>(MainActivity.this) {

                    @Override
                    protected int getLayoutRes() {
                        return R.layout.item_test;
                    }

                    @Override
                    protected void convert(FRBaseDialogViewHolder holder, TestDataBean dataBean, int position, List<Object> payloads) {
                        holder.setImageResource(R.id.it_iv_image, R.mipmap.ic_launcher_round);
                        holder.setText(R.id.it_tv_title, dataBean.getName());
                        holder.setText(R.id.it_tv_time, dataBean.getTime());
                    }
                }).setDataList(mDataList)
                .addDialogFooter(R.layout.layout_footer)
                .addRecyclerViewHeader(R.layout.layout_header)
                .setHeightRatio(0.5)
                .setOnClickListener(R.id.lf_tv_cancel, view -> true)
                .setOnClickListener(R.id.lf_tv_confirm, view -> {
                    Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                    return false;
                })
                .show();
    }

    private void showFromBottomDialog() {
        final FRDialog dialog = new FRDialog.CommonBuilder(this)
                .setContentView(R.layout.dialog_from_bottom)
                .setFullWidth()
                .setFromBottom()
                .show();

        dialog.setOnClickListener(R.id.dfb_tv_take_photo, view -> {
            Toast.makeText(MainActivity.this, "点击了拍照", Toast.LENGTH_SHORT).show();
            return false;
        });

        dialog.setOnClickListener(R.id.dfb_tv_gallery, view -> {
            Toast.makeText(MainActivity.this, "点击了相册", Toast.LENGTH_SHORT).show();
            return false;
        });

        dialog.setOnClickListener(R.id.dfb_tv_cancel, view -> true);
    }

    private void showMDDialog() {
        FRDialog dialog = new FRDialog.MDBuilder(this)
                .setMessage("1.文字文字我是文字文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
                .setTitle("温馨提示")
                .setNegativeContentAndListener("否", null)
                .setNegativeTextColor(ContextCompat.getColor(this, R.color.c999999))
                .setPositiveTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setPositiveContentAndListener("是", view -> true).show();
    }

    private void showCommonDialog() {
        FRDialog dialog = new FRDialog.CommonBuilder(this)
                .setContentView(R.layout.dialog_common)
                .setCancelableOutside(false)
                .setText(R.id.dcu_tv_cancel, "否")
                .setText(R.id.dcu_tv_confirm, "是")
                .setText(R.id.dcu_tv_title, "温馨提示")
                .setText(R.id.dcu_tv_content, "1.文字文字我是文字文字文字！")
                .setDefaultAnim()
                .show();

        dialog.setText(R.id.dcu_tv_confirm, "确定");

        dialog.setImageDrawable(R.id.dcu_iv_image, ContextCompat.getDrawable(this, R.mipmap.ic_launcher));

        dialog.setOnClickListener(R.id.dcu_tv_cancel, view -> {
            Toast.makeText(MainActivity.this, "点击了否", Toast.LENGTH_SHORT).show();
            return true;
        });

        dialog.setOnClickListener(R.id.dcu_tv_confirm, v -> {
            if (TextUtils.isEmpty(dialog.getContentById(R.id.dcu_et_input))) {
                Toast.makeText(MainActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                return false;
            }
            Toast.makeText(MainActivity.this, dialog.getContentById(R.id.dcu_et_input), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void openOverlaySettings() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                //打开权限申请页面
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, RC_OVERLAY);
            } catch (ActivityNotFoundException e) {
                Log.e("TAG", "ActivityNotFoundException--->" + e.getMessage());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_OVERLAY:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        startService(new Intent(this, DialogService.class));
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, DialogService.class));
    }
}