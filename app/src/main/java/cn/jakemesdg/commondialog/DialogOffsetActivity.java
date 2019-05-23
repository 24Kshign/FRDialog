package cn.jakemesdg.commondialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.jake.share.frdialog.dialog.FRDialog;

/**
 * Created by manji
 * Date：2018/12/29 11:32 AM
 * Desc：
 */
public class DialogOffsetActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_offset);

        findViewById(R.id.ado_btn_offsetx).setOnClickListener(v -> {
            showOffsetXDialog();
        });

        findViewById(R.id.ado_btn_offsety).setOnClickListener(v -> {
            showOffsetYDialog();
        });
    }

    private void showOffsetXDialog() {
        FRDialog dialog = new FRDialog.CommonBuilder(this, R.style.transparent_dialog)
                .setContentView(R.layout.dialog_offsetx)
                .setGravity(Gravity.LEFT | Gravity.TOP)
                .setOnClickListener(R.id.do_tv_praised, view -> {
                    Toast.makeText(DialogOffsetActivity.this, "赞", Toast.LENGTH_SHORT).show();
                    return true;
                })
                .setOnClickListener(R.id.do_tv_comment, view -> {
                    Toast.makeText(DialogOffsetActivity.this, "评论", Toast.LENGTH_SHORT).show();
                    return true;
                })
                .setOffsetX(dp2px(100))
                .setOffsetY(dp2px(51))
                .show();
    }

    private void showOffsetYDialog() {
        FRDialog dialog = new FRDialog.CommonBuilder(this, R.style.transparent_dialog)
                .setContentView(R.layout.dialog_offsety)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.TOP | Gravity.LEFT)
                .setOnClickListener(R.id.do_ll_main, view -> true)
                .setOffsetY(dp2px(50)).show();
    }

    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}