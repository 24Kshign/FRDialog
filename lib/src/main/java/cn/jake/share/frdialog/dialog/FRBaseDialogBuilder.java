package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.jake.share.frdialog.R;
import cn.jake.share.frdialog.image.CommonImageLoader;
import cn.jake.share.frdialog.interfaces.FRDialogClickListener;

/**
 * Created by jack on 2018/2/7
 */

public class FRBaseDialogBuilder<BUILDER extends FRBaseDialogBuilder> {

    Context mContext;
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
    SparseArray<CharSequence> mTextArray = new SparseArray<>();  //dialog布局上的文案
    SparseIntArray mTextColorArray = new SparseIntArray();  //dialog布局上的文案颜色
    SparseArray<ColorStateList> mTextColorStateListArray = new SparseArray<>();  //dialog布局上的文案颜色
    SparseArray<FRDialogClickListener> mClickListenerArray = new SparseArray<>(); //dialog上控件的点击事件

    /**
     * 图片
     */
    SparseArray<Drawable> mImageDrawableArray = new SparseArray<>();
    SparseArray<Bitmap> mImageBitmapArray = new SparseArray<>();
    SparseArray<CommonImageLoader> mImageCommonImageLoaderArray = new SparseArray<>();

    public double mWidthOffset = 0.9;  //dialog宽度占屏幕宽度的比例
    public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int mAnimation; //dialog动画
    public int mGravity = Gravity.CENTER;  //dialog位置

    FRDialogViewHelper mDialogViewHelper;
    private FRDialog mDialog;

    public FRBaseDialogBuilder(Context context, int themeResId) {
        this.mContext = context;
        this.mThemeResId = themeResId;
    }

    //设置dialog宽度全屏
    public BUILDER setFullWidth() {
        mWidthOffset = 1;
        return builder();
    }

    //设置dialog宽度比例
    public BUILDER setWidthOffset(double widthOffset) {
        mWidthOffset = widthOffset;
        return builder();
    }

    public BUILDER setHeight(int height) {
        mHeight = height;
        return builder();
    }

    //设置dialog从底部弹出
    public BUILDER setFromBottom() {
        mAnimation = R.style.dialog_from_bottom_anim;
        mGravity = Gravity.BOTTOM;
        return builder();
    }

    //设置dialog默认动画
    public BUILDER setDefaultAnim() {
        mAnimation = R.style.default_dialog_anim;
        return builder();
    }

    //设置dialog其他动画
    public FRBaseDialogBuilder setAnimation(int animation) {
        mAnimation = animation;
        return this;
    }

    //设置OnCancelListener监听
    public BUILDER setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
        return builder();
    }

    //设置OnDismissListener监听
    public BUILDER setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return builder();
    }

    //设置OnKeyListener监听
    public BUILDER setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mOnKeyListener = onKeyListener;
        return builder();
    }

    //设置点击返回键是否消失Dialog
    public BUILDER setCancelable(boolean isCancelable) {
        mCancelable = isCancelable;
        return builder();
    }

    //设置点击dialog以外的区域是否消失Dialog
    public BUILDER setCancelableOutside(boolean isCancelableOutside) {
        mCancelableOutside = isCancelableOutside;
        return builder();
    }

    public BUILDER setText(int id, CharSequence charSequence) {
        mTextArray.put(id, charSequence);
        return builder();
    }

    public BUILDER setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        mImageBitmapArray.put(viewId, bitmap);
        return builder();
    }

    public BUILDER setImageDrawable(@IdRes int viewId, Drawable drawable) {
        mImageDrawableArray.put(viewId, drawable);
        return builder();
    }

    public BUILDER setImagePath(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        mImageCommonImageLoaderArray.put(viewId, commonImageLoader);
        return builder();
    }

    public BUILDER setOnClickListener(int id, FRDialogClickListener onClickListener) {
        mClickListenerArray.put(id, onClickListener);
        return builder();
    }

    private BUILDER builder() {
        return (BUILDER) this;
    }

    public <VIEW extends View> VIEW getView(int viewId) {
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

    protected boolean attachView() {
        /**
         * 文字，颜色
         */
        for (int i = 0; i < mTextArray.size(); i++) {
            mDialogViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
        }
        for (int i = 0; i < mTextColorArray.size(); i++) {
            mDialogViewHelper.setTextColor(mTextColorArray.keyAt(i), mTextColorArray.valueAt(i));
        }
        for (int i = 0; i < mTextColorStateListArray.size(); i++) {
            mDialogViewHelper.setTextColor(mTextColorStateListArray.keyAt(i), mTextColorStateListArray.valueAt(i));
        }

        /**
         * 点击事件
         */
        for (int i = 0; i < mClickListenerArray.size(); i++) {
            mDialogViewHelper.setOnDialogClickListener(mClickListenerArray.keyAt(i), mClickListenerArray.valueAt(i));
        }

        /**
         * 图片
         */
        for (int i = 0; i < mImageBitmapArray.size(); i++) {
            mDialogViewHelper.setImageBitmap(mImageBitmapArray.keyAt(i), mImageBitmapArray.valueAt(i));
        }
        for (int i = 0; i < mImageDrawableArray.size(); i++) {
            mDialogViewHelper.setImageDrawable(mImageDrawableArray.keyAt(i), mImageDrawableArray.valueAt(i));
        }
        for (int i = 0; i < mImageCommonImageLoaderArray.size(); i++) {
            mDialogViewHelper.setImagePath(mImageCommonImageLoaderArray.keyAt(i), mImageCommonImageLoaderArray.valueAt(i));
        }
        return true;
    }
}