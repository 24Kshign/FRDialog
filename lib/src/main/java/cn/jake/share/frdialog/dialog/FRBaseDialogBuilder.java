package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;

import cn.jake.share.frdialog.R;
import cn.jake.share.frdialog.interfaces.FRDialogClickListener;

/**
 * Created by jack on 2018/2/7
 */

public class FRBaseDialogBuilder<T extends FRBaseDialogBuilder> {

    public Context mContext;
    public int mThemeResId;  //dialog主题
    public boolean mCancelable = true;  //点击返回键是否dismiss
    public boolean mCancelableOutside = true;  //点击外部是否dismiss
    /**
     * dialog监听事件
     */
    public DialogInterface.OnDismissListener mOnDismissListener;
    public DialogInterface.OnCancelListener mOnCancelListener;
    public DialogInterface.OnKeyListener mOnKeyListener;
    /**
     * 布局和布局Id
     */
    public View mContentView;
    public SparseArray<CharSequence> mTextArray = new SparseArray<>();  //dialog布局上的文案
    public SparseIntArray mTextColorArray = new SparseIntArray();
    public SparseArray<FRDialogClickListener> mClickListenerArray = new SparseArray<>(); //dialog上控件的点击事件
    public double mWidthOffset = 0.9;  //dialog宽度占屏幕宽度的比例
    public int mAnimation; //dialog动画
    public int mGravity = Gravity.CENTER;  //dialog位置

    private FRDialogViewHelper mDialogViewHelper;
    private FRDialog mDialog;

    public FRBaseDialogBuilder(Context context, int themeResId) {
        this.mContext = context;
        this.mThemeResId = themeResId;
    }

    //设置dialog宽度全屏
    public T setFullWidth() {
        mWidthOffset = 1;
        return (T) this;
    }

    //设置dialog宽高
    public T setWidthOffset(double width) {
        mWidthOffset = width;
        return (T) this;
    }

    //设置dialog从底部弹出
    public T setFromBottom() {
        mAnimation = R.style.dialog_from_bottom_anim;
        mGravity = Gravity.BOTTOM;
        return (T) this;
    }

    //设置dialog默认动画
    public T setDefaultAnim() {
        mAnimation = R.style.default_dialog_anim;
        return (T) this;
    }

    //设置dialog其他动画
    public T setAnimation(int animation) {
        mAnimation = animation;
        return (T) this;
    }

    //设置OnCancelListener监听
    public T setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
        return (T) this;
    }

    //设置OnDismissListener监听
    public T setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return (T) this;
    }

    //设置OnKeyListener监听
    public T setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mOnKeyListener = onKeyListener;
        return (T) this;
    }

    //设置点击返回键是否消失Dialog
    public T setCancelable(boolean isCancelable) {
        mCancelable = isCancelable;
        return (T) this;
    }

    //设置点击dialog以外的区域是否消失Dialog
    public T setCancelableOutside(boolean isCancelableOutside) {
        mCancelableOutside = isCancelableOutside;
        return (T) this;
    }

    public T setText(int id, CharSequence charSequence) {
        mTextArray.put(id, charSequence);
        return (T) this;
    }

    public T setOnClickListener(int id, FRDialogClickListener onClickListener) {
        mClickListenerArray.put(id, onClickListener);
        return (T) this;
    }

    public <T extends View> T getView(int viewId) {
        if (null != mDialogViewHelper) {
            return mDialogViewHelper.getView(viewId);
        }
        return null;
    }

    public FRDialog create() {
        if (null == mDialog) {
            mDialog = new FRDialog(mContext, mThemeResId);
            mDialog.attach(this);
            mDialogViewHelper = mDialog.getDialogViewHelper();
            attachView();
        }
        return mDialog;
    }

    public FRDialog show() {
        if (null != mDialog) {
            mDialog.show();
        } else {
            create().show();
        }
        return mDialog;
    }

    public FRDialog getDialog() {
        return mDialog;
    }

    public Context getContext() {
        return mContext;
    }

    protected boolean attachView() {
        for (int i = 0; i < mTextArray.size(); i++) {
            mDialogViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
        }
        for (int i = 0; i < mClickListenerArray.size(); i++) {
            mDialogViewHelper.setOnDialogClickListener(mClickListenerArray.keyAt(i), mClickListenerArray.valueAt(i));
        }
        for (int i = 0; i < mTextColorArray.size(); i++) {
            mDialogViewHelper.setTextColor(mTextColorArray.keyAt(i), mTextColorArray.valueAt(i));
        }
        return true;
    }
}