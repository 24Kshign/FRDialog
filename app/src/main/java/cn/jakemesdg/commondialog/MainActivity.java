package cn.jakemesdg.commondialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

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
        final FRDialog dialog = new FRDialog.SelectedBuilder(this)
                .title("温馨提示")
                .fromBottom()
                .checkMode(FRDialog.SelectedBuilder.CheckMode.MULTI)
                .message("这是recyclerView的dialog")
                .addDatas("测试1", "测试2", "测试3", "测试4", "测试5", "测试6", "测试7", "测试8", "测试9", "测试10", "测试11", "测试12")
                .negative("取消", new DialogClickListener<FRDialog.SelectedBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.SelectedBuilder dialogBuilder, View clickedView) {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .positive("确定", new DialogClickListener<FRDialog.SelectedBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.SelectedBuilder dialogBuilder, View clickedView) {
                        List<FRDialog.SelectedBuilder.ISelect> selected = dialogBuilder.getSelectedDatas();
                        StringBuilder builder = new StringBuilder();
                        if (selected != null) {
                            for (FRDialog.SelectedBuilder.ISelect iSelect : selected) {
                                if (iSelect != null) {
                                    builder.append(iSelect.getDesc());
                                    builder.append(",\n");
                                }
                            }
                        }
                        Toast.makeText(MainActivity.this, "选择了  >> \n" + builder, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .checkListener(new FRDialog.SelectedBuilder.OnCheckChangeListener() {
                    @Override
                    public void onCheckChange(FRDialog.SelectedBuilder.ISelect select, int position, boolean checked) {
                        if (select != null) {
                            Toast.makeText(MainActivity.this, select.getDesc() + (checked ? "  选择" : "  取消选择"), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    private void showFromBottomDialog() {
        final FRDialog dialog = new FRDialog.CommonBuilder(this)
                .contentView(R.layout.dialog_from_bottom)
                .fullWidth()
                .fromBottom()
                .addClick(R.id.dfb_tv_take_photo, new DialogClickListener<FRDialog.CommonBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.CommonBuilder dialogBuilder, View clickedView) {
                        Toast.makeText(MainActivity.this, "点击了拍照", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .addClick(R.id.dfb_tv_gallery, new DialogClickListener<FRDialog.CommonBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.CommonBuilder dialogBuilder, View clickedView) {
                        Toast.makeText(MainActivity.this, "点击了相册", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .addClick(R.id.dfb_tv_cancel, new DialogClickListener<FRDialog.CommonBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.CommonBuilder dialogBuilder, View clickedView) {
                        return true;
                    }
                })
                .show();
    }

    private void showMDDialog() {
        FRDialog dialog = new FRDialog.MDBuilder(this)
                .title("温馨提示")
                .message("1.文字文字我是文字文字文字我是文字文字文字我是文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
                .negative("取消", new DialogClickListener<FRDialog.MDBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.MDBuilder dialogBuilder, View clickedView) {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .positive("确定", new DialogClickListener<FRDialog.MDBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.MDBuilder dialogBuilder, View clickedView) {
                        Toast.makeText(MainActivity.this, "confirm", Toast.LENGTH_SHORT).show();
                        return false;
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
                .addClick(R.id.dcu_tv_cancel, new DialogClickListener<FRDialog.CommonBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.CommonBuilder dialogBuilder, View clickedView) {
                        return true;
                    }
                })
                .addClick(R.id.dcu_tv_confirm, new DialogClickListener<FRDialog.CommonBuilder>() {
                    @Override
                    public boolean onClick(FRDialog dialog, FRDialog.CommonBuilder dialogBuilder, View clickedView) {
                        Toast.makeText(MainActivity.this, "点击了是", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .defaultAnim()
                .show();
    }
}
