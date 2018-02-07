package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import cn.jake.share.frdialog.R;

/**
 * Created by 大灯泡 on 2018/1/27.
 */
public abstract class FRDialogBuilder<T extends FRDialogBuilder> {
    protected FRDialog mFRDialog;
    protected Context mContext;
    //点击返回键是否dismiss
    protected boolean mCancelable = true;
    //点击外部是否dismiss
    protected boolean mCancelableOutside = true;
    private int themeId;
    //布局和布局Id
    protected int mContentViewResid;
    protected View mContentView;

    protected FRDialog.DialogLayoutParams mLayoutParamsWrapper;

    protected DialogInterface.OnDismissListener mOnDismissListener;
    protected DialogInterface.OnCancelListener mOnCancelListener;
    protected DialogInterface.OnKeyListener mOnKeyListener;

    public FRDialogBuilder(Context context) {
        this(context, 0);
    }

    public FRDialogBuilder(Context context, int themeId) {
        mContext = context;
        this.themeId = themeId;
        mLayoutParamsWrapper = new FRDialog.DialogLayoutParams();
    }

    public T contentView(int contentViewID) {
        this.mContentViewResid = contentViewID;
        return castReturn();
    }

    public T contentView(View contentView) {
        this.mContentView = contentView;
        return castReturn();
    }

    protected T contentViewInternal(int contentViewID) {
        this.mContentViewResid = contentViewID;
        return castReturn();
    }

    protected T contentViewInternal(View contentView) {
        this.mContentView = contentView;
        return castReturn();
    }

    public T fullWidth() {
        return width(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public T width(int width) {
        mLayoutParamsWrapper.mWidth = width;
        return castReturn();
    }

    public T height(int height) {
        mLayoutParamsWrapper.mHeight = height;
        return castReturn();
    }

    public T fromBottom() {
        mLayoutParamsWrapper.mAnimation = R.style.dialog_from_bottom_anim;
        mLayoutParamsWrapper.mGravity = Gravity.BOTTOM;
        return castReturn();
    }

    public T defaultAnim() {
        mLayoutParamsWrapper.mAnimation = R.style.default_dialog_anim;
        return castReturn();
    }

    public T animation(int animation) {
        mLayoutParamsWrapper.mAnimation = animation;
        return castReturn();
    }

    public T dontAnim() {
        mLayoutParamsWrapper.mAnimation = -1;
        //set to default
        mLayoutParamsWrapper.mGravity = Gravity.CENTER;
        return castReturn();
    }

    public T cancelListener(DialogInterface.OnCancelListener l) {
        mOnCancelListener = l;
        return castReturn();
    }

    //设置OnDismissListener监听
    public T dismissListener(DialogInterface.OnDismissListener l) {
        mOnDismissListener = l;
        return castReturn();
    }

    //设置OnKeyListener监听
    public T setOnKeyListener(DialogInterface.OnKeyListener l) {
        mOnKeyListener = l;
        return castReturn();
    }

    //设置点击返回键是否消失Dialog
    public T cancelable(boolean isCancelable) {
        mCancelable = isCancelable;
        return castReturn();
    }

    //设置点击dialog以外的区域是否消失Dialog
    public T cancelableOutside(boolean isCancelableOutside) {
        mCancelableOutside = isCancelableOutside;
        return castReturn();
    }

    public FRDialog create() {
        if (mFRDialog == null) {
            mFRDialog = new FRDialog(mContext, themeId);
        }
        mFRDialog.attachBuilder(this);
        return mFRDialog;
    }

    public FRDialog show() {
        if (mFRDialog != null) {
            mFRDialog.show();
        } else {
            create().show();
        }
        return mFRDialog;
    }


    T castReturn() {
        return (T) this;
    }

    void reset() {
        mCancelable = true;
        mCancelableOutside = true;
        mOnDismissListener = null;
        mOnCancelListener = null;
        mOnKeyListener = null;
    }

    boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    //-----------------------------------------abstract-----------------------------------------

    void onAttachDialog(FRDialog dialog) {

    }

    @Nullable
    abstract FRDialog.DialogLayoutParams onGenerateDialogLayoutParams();

    abstract void onInitView(@NonNull View contentView, @NonNull FRDialog.DialogLayoutParams params);


}
