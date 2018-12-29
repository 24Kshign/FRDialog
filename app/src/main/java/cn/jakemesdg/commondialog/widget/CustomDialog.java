package cn.jakemesdg.commondialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cn.jakemesdg.commondialog.R;

/**
 * Created by manji
 * Date：2018/12/29 4:00 PM
 * Desc：
 */
public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context, R.style.transparent_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_offsetx);
    }

    @Override
    public void show() {
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.x = dp2px(101);
            lp.y = dp2px(51);
            dialogWindow.setAttributes(lp);
        }
        super.show();
    }

    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics());
    }
}
