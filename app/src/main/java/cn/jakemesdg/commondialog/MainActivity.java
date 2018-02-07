package cn.jakemesdg.commondialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.jake.share.frdialog.dialog.FRDialog;
import cn.jake.share.frdialog.dialog.interfaces.DialogClickListener;

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
        Toast.makeText(this, "正在开发中，敬请期待", Toast.LENGTH_SHORT).show();
    }

    private void showFromBottomDialog() {
        final FRDialog dialog = new FRDialog.CommonBuilder(this)
                .contentView(R.layout.dialog_from_bottom)
                .fullWidth()
                .fromBottom()
                .addClick(R.id.dfb_tv_take_photo, new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        Toast.makeText(MainActivity.this, "点击了拍照", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .addClick(R.id.dfb_tv_gallery, new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        Toast.makeText(MainActivity.this, "点击了相册", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .addClick(R.id.dfb_tv_cancel, new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        return true;
                    }
                })
                .show();
    }

    private void showMDDialog() {
        FRDialog dialog = new FRDialog.MDBuilder(this)
                .fullWidth()
                .fromBottom()
                .title("温馨提示")
                .message("1.文字文字我是文字文字文字我是文字文字文字我是文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
                .negative("取消", new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .positive("确定", new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        Toast.makeText(MainActivity.this, "confirm", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .create();
        dialog.show();
    }

    private void showCommonDialog() {
        final FRDialog dialog = new FRDialog.CommonBuilder(this)
                .contentView(R.layout.dialog_common)
                .setText(R.id.dcu_tv_cancel, "否")
                .setText(R.id.dcu_tv_confirm, "是")
                .setText(R.id.dcu_tv_title, "温馨提示")
                .setText(R.id.dcu_tv_content, "1.文字文字我是文字文字文字我是文字文字文字我是文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
                .addClick(R.id.dcu_tv_cancel, new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        return true;
                    }
                })
                .addClick(R.id.dcu_tv_confirm, new DialogClickListener() {
                    @Override
                    public boolean onClick(FRDialog dialog, View clickedView) {
                        Toast.makeText(MainActivity.this, "点击了是", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .defaultAnim()
                .show();
    }
}
