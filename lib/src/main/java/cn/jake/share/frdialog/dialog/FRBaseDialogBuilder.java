package cn.jake.share.frdialog.dialog;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.IdRes;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import cn.jake.share.frdialog.R;
import cn.jake.share.frdialog.image.CommonImageLoader;
import cn.jake.share.frdialog.interfaces.FRDialogClickListener;

/**
 * Created by jack on 2018/2/7
 */

public class FRBaseDialogBuilder {

    Context mContext;
    private int mThemeResId;  //dialog主题
    boolean mCancelable = true;  //点击返回键是否dismiss
    boolean mCancelableOutside = true;  //点击外部是否dismiss
    /**
     * dialog监听事件
     */
    DialogInterface.OnDismissListener mOnDismissListener;
    DialogInterface.OnCancelListener mOnCancelListener;
    DialogInterface.OnKeyListener mOnKeyListener;
    /**
     * 布局和布局Id
     */
    View mContentView;
    SparseArray<CharSequence> mTextArray = new SparseArray<>();  //dialog布局上的文案
    SparseIntArray mTextColorArray = new SparseIntArray();  //dialog布局上的文案颜色
    SparseArray<ColorStateList> mTextColorStateListArray = new SparseArray<>();  //dialog布局上的文案颜色
    SparseArray<FRDialogClickListener> mClickListenerArray = new SparseArray<>(); //dialog上控件的点击事件

    /**
     * 图片
     */
    private SparseArray<Drawable> mImageDrawableArray = new SparseArray<>();
    private SparseArray<Bitmap> mImageBitmapArray = new SparseArray<>();
    private SparseArray<CommonImageLoader> mImageCommonImageLoaderArray = new SparseArray<>();

    private double mWidthRatio = 0;  //dialog宽度占屏幕宽度的比例
    private double mHeightRatio = 0;  //dialog高度占屏幕高度的比例
    private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mAnimation; //dialog动画
    private int mGravity = Gravity.CENTER;  //dialog位置
    private boolean isInService;
    private int mOffsetX;      //dialog在X轴的偏移量（Gravity需要设置Left）
    private int mOffsetY;      //dialog在Y轴的偏移量（Gravity需要设置Top）

    FRDialogViewHelper mDialogViewHelper;
    private FRDialog mDialog;

    FRBaseDialogBuilder(Context context, int themeResId) {
        //判断 dialog 是否在 Service 中弹出，需要适配一下
        if (context instanceof Service) {
            isInService = true;
        }
        this.mContext = context;
        this.mThemeResId = themeResId;
    }

    //设置dialog宽度全屏
    public FRBaseDialogBuilder setFullWidth() {
        mWidthRatio = 1;
        return this;
    }

    //设置dialog宽度全屏
    public FRBaseDialogBuilder setFullHeight() {
        mHeightRatio = 1;
        return this;
    }

    //设置dialog宽度比例
    public FRBaseDialogBuilder setWidthRatio(double widthRatio) {
        mWidthRatio = widthRatio;
        return this;
    }

    //设置dialog宽度比例
    public FRBaseDialogBuilder setHeightRatio(double heightRatio) {
        mHeightRatio = heightRatio;
        return this;
    }

    public FRBaseDialogBuilder setHeight(int height) {
        mHeight = height;
        return this;
    }

    public FRBaseDialogBuilder setWidth(int width) {
        mWidth = width;
        return this;
    }

    public FRBaseDialogBuilder setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    public FRBaseDialogBuilder setOffsetX(int offsetX) {
        mOffsetX = offsetX;
        return this;
    }

    public FRBaseDialogBuilder setOffsetY(int offsetY) {
        mOffsetY = offsetY;
        return this;
    }

    //设置dialog从底部弹出
    public FRBaseDialogBuilder setFromBottom() {
        mAnimation = R.style.dialog_from_bottom_anim;
        mGravity = Gravity.BOTTOM;
        return this;
    }

    //设置dialog默认动画
    public FRBaseDialogBuilder setDefaultAnim() {
        mAnimation = R.style.default_dialog_anim;
        return this;
    }

    //设置dialog其他动画
    public FRBaseDialogBuilder setAnimation(int animation) {
        mAnimation = animation;
        return this;
    }

    //设置OnCancelListener监听
    public FRBaseDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
        return this;
    }

    //设置OnDismissListener监听
    public FRBaseDialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    //设置OnKeyListener监听
    public FRBaseDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mOnKeyListener = onKeyListener;
        return this;
    }

    //设置点击返回键是否消失Dialog
    public FRBaseDialogBuilder setCancelable(boolean isCancelable) {
        mCancelable = isCancelable;
        return this;
    }

    //设置点击dialog以外的区域是否消失Dialog
    public FRBaseDialogBuilder setCancelableOutside(boolean isCancelableOutside) {
        mCancelableOutside = isCancelableOutside;
        return this;
    }

    public FRBaseDialogBuilder setText(int id, CharSequence charSequence) {
        mTextArray.put(id, charSequence);
        return this;
    }

    public FRBaseDialogBuilder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        mImageBitmapArray.put(viewId, bitmap);
        return this;
    }

    public FRBaseDialogBuilder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        mImageDrawableArray.put(viewId, drawable);
        return this;
    }

    public FRBaseDialogBuilder setImagePath(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        mImageCommonImageLoaderArray.put(viewId, commonImageLoader);
        return this;
    }

    public FRBaseDialogBuilder setOnClickListener(int id, FRDialogClickListener onClickListener) {
        mClickListenerArray.put(id, onClickListener);
        return this;
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
        if (null == mDialog) {
            create();
        }
        mDialog.show();
        setWindowStyle();
        return mDialog;
    }

    private void setWindowStyle() {
        Window window = mDialog.getWindow();
        if (null != window) {
            window.setGravity(mGravity);
            if (isInService) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Objects.requireNonNull(window).setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY - 1);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(window).setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                }
            }
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }

            WindowManager.LayoutParams lp = window.getAttributes();

            if (mWidthRatio != 0) {
                lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * mWidthRatio);
            } else {
                lp.width = mWidth;
            }
            if (mHeightRatio != 0) {
                lp.height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * mHeightRatio);
            } else {
                lp.height = mHeight;
            }
            if (mOffsetX > 0) {
                lp.x = mOffsetX;
            }
            if (mOffsetY > 0) {
                lp.y = mOffsetY;
            }
            window.setAttributes(lp);
        }
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