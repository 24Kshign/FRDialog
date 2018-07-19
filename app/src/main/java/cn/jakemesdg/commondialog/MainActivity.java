package cn.jakemesdg.commondialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import cn.jake.share.frdialog.dialog.FRDialog;

public class MainActivity extends Activity implements View.OnClickListener {


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
        }
    }

    private void showRecyclerViewDialog() {
        Toast.makeText(this, "敬请期待...", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(MainActivity.this, dialog.getContentById(R.id.dcu_et_input), Toast.LENGTH_SHORT).show();
            return false;
        });
    }
}