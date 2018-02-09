package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import cn.jake.share.frdialog.R;

/**
 * Created by jack on 2018/2/7
 */

public class FRBaseDialogBuilder<T extends FRBaseDialogBuilder> {

    FRDialogController.FRDialogParams mParams;

    public FRBaseDialogBuilder(Context context, int themeResId) {
        mParams = new FRDialogController.FRDialogParams(context, themeResId);
    }

    //设置dialog宽度全屏
    public T setFullWidth() {
        mParams.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        return (T) this;
    }

    //设置dialog宽高
    public T setWidthAndHeight(int width, int height) {
        mParams.mWidth = width;
        mParams.mHeight = height;
        return (T) this;
    }

    //设置dialog从底部弹出
    public T setFromBottom(boolean isAnimation) {
        if (isAnimation) {
            mParams.mAnimation = R.style.dialog_from_bottom_anim;
        }
        mParams.mGravity = Gravity.BOTTOM;
        return (T) this;
    }

    //设置dialog默认动画
    public T setDefaultAnim() {
        mParams.mAnimation = R.style.default_dialog_anim;
        return (T) this;
    }

    //设置dialog其他动画
    public T setAnimation(int animation) {
        mParams.mAnimation = animation;
        return (T) this;
    }

    //设置OnCancelListener监听
    public T setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mParams.mOnCancelListener = onCancelListener;
        return (T) this;
    }

    //设置OnDismissListener监听
    public T setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mParams.mOnDismissListener = onDismissListener;
        return (T) this;
    }

    //设置OnKeyListener监听
    public T setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mParams.mOnKeyListener = onKeyListener;
        return (T) this;
    }

    //设置点击返回键是否消失Dialog
    public T setCancelable(boolean isCancelable) {
        mParams.mCancelable = isCancelable;
        return (T) this;
    }

    //设置点击dialog以外的区域是否消失Dialog
    public T setCancelableOutside(boolean isCancelableOutside) {
        mParams.mCancelableOutside = isCancelableOutside;
        return (T) this;
    }

    public <VIEW extends View> VIEW getView(int viewId) {
        return mParams.getView(viewId);
    }

    public FRDialog create() {
        FRDialog dialog = new FRDialog(mParams.mContext, mParams.mThemeResId);
        mParams.apply(dialog.controller);
        dialog.setCanceledOnTouchOutside(mParams.mCancelableOutside);
        dialog.setCancelable(mParams.mCancelable);
        if (null != mParams.mOnCancelListener) {
            dialog.setOnCancelListener(mParams.mOnCancelListener);
        }
        if (null != mParams.mOnDismissListener) {
            dialog.setOnDismissListener(mParams.mOnDismissListener);
        }
        if (null != mParams.mOnKeyListener) {
            dialog.setOnKeyListener(mParams.mOnKeyListener);
        }
        return dialog;
    }

    public FRDialog show() {
        final FRDialog dialog = create();
        dialog.show();
        return dialog;
    }
}
