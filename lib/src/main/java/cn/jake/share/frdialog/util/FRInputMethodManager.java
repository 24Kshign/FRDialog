package cn.jake.share.frdialog.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by jack
 * Date：2018/7/19 下午2:13
 * Desc：
 */
public class FRInputMethodManager {


    public static InputMethodManager getInputMethodManager(Context context) {
        if (null == context) {
            throw new NullPointerException("the context should not be null");
        }
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // show

    public static void showInputMethod(Context context, View view) {
        if (null == view) {
            return;
        }
        InputMethodManager inputMethodManager = getInputMethodManager(context);
        if (null == inputMethodManager) {
            return;
        }
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void showInputMethod(View view) {
        if (null == view) {
            return;
        }
        showInputMethod(view.getContext(), view);
    }

    public static void showInputMethod(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // hide

    public static void hideSoftInput(Context context, View view) {
        if (null == view) {
            return;
        }
        IBinder token = view.getWindowToken();
        if (null == token) {
            return;
        }
        InputMethodManager inputMethodManager = getInputMethodManager(context);
        if (null == inputMethodManager) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftInput(Dialog dialog, View view) {
        hideSoftInput(dialog.getContext(), view);
    }

    public static void hideSoftInput(View view) {
        if (null == view) {
            return;
        }
        hideSoftInput(view.getContext(), view);
    }

    public static void hideSoftInput(Activity activity) {
        hideSoftInput(activity, activity.getCurrentFocus());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // toggle

    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager = getInputMethodManager(context);
        if (null == inputMethodManager) {
            return;
        }
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //isShowing
    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom != 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void autoHideSoftInput(Activity activity, MotionEvent event) {
        View focusView = activity.getCurrentFocus();

        if (null == focusView) {
            return;
        }

        if (isAutoHideSoftInput(focusView, event)) {
            FRInputMethodManager.hideSoftInput(activity, focusView);
        }
    }

    public static void autoHideSoftInput(Dialog dialog, MotionEvent event) {
        View focusView = dialog.getCurrentFocus();

        if (null == focusView) {
            return;
        }

        if (isAutoHideSoftInput(focusView, event)) {
            FRInputMethodManager.hideSoftInput(dialog, focusView);
        }
    }

    public static boolean isAutoHideSoftInput(View view, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        if (!(view instanceof EditText)) {
            return false;
        }

        float x = event.getX();
        float y = event.getY();

        int[] location = {0, 0};
        view.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];
        int bottom = top + view.getHeight();
        int right = left + view.getWidth();
        if (left <= x && x < right && top <= y && y < bottom) {
            // 点击事件在EditText的区域里
            return false;
        }

        return true;
    }
}
