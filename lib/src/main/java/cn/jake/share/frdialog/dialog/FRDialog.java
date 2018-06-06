package cn.jake.share.frdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.jake.share.frdialog.R;
import cn.jake.share.frdialog.interfaces.FRDialogClickListener;
import cn.jake.share.frdialog.interfaces.FRDialogTextChangeListener;
import cn.jake.share.frdialog.util.StringUtil;


/**
 * Created by jack on 2018/1/22
 */

public class FRDialog extends Dialog {

    private FRDialogViewHelper dialogViewHelper;

    FRDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public <T extends View> T getView(int viewId) {
        return dialogViewHelper.getView(viewId);
    }

    public void setText(int id, CharSequence charSequence) {
        dialogViewHelper.setText(id, charSequence);
    }

    public void setVisibleOrGone(int id, boolean isVisible) {
        dialogViewHelper.setVisibleOrGone(id, isVisible);
    }

    public void setOnClickListener(int id, FRDialogClickListener onClickListener) {
        dialogViewHelper.setOnDialogClickListener(id, onClickListener);
    }

    public void addTextChangedListener(int id, FRDialogTextChangeListener frDialogTextChangeListener) {
        dialogViewHelper.addTextChangedListener(id, frDialogTextChangeListener);
    }

    void attach(FRBaseDialogBuilder baseBuilder) {
        if (null != baseBuilder.mContentView) {
            dialogViewHelper = new FRDialogViewHelper(baseBuilder.mContentView);
        }
        if (null == dialogViewHelper) {
            throw new IllegalArgumentException("the xml layout of dialog should not be null");
        }
        setContentView(dialogViewHelper.getContentView());
        dialogViewHelper.setDialog(this);
        setCanceledOnTouchOutside(baseBuilder.mCancelableOutside);
        setCancelable(baseBuilder.mCancelable);
        if (null != baseBuilder.mOnCancelListener) {
            setOnCancelListener(baseBuilder.mOnCancelListener);
        }
        if (null != baseBuilder.mOnDismissListener) {
            setOnDismissListener(baseBuilder.mOnDismissListener);
        }
        if (null != baseBuilder.mOnKeyListener) {
            setOnKeyListener(baseBuilder.mOnKeyListener);
        }

        Window window = getWindow();
        if (null != window) {
            window.setGravity(baseBuilder.mGravity);
            if (baseBuilder.mAnimation != 0) {
                window.setWindowAnimations(baseBuilder.mAnimation);
            }
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (baseBuilder.mContext.getResources().getDisplayMetrics().widthPixels * baseBuilder.mWidthOffset);
            lp.height = baseBuilder.mHeight;
            window.setAttributes(lp);
        }
    }

    FRDialogViewHelper getDialogViewHelper() {
        return dialogViewHelper;
    }

    public static class CommonBuilder extends FRBaseDialogBuilder<CommonBuilder> {

        public CommonBuilder(Context context) {
            this(context, R.style.dialog);
        }

        public CommonBuilder(Context context, int themeResId) {
            super(context, themeResId);
        }

        //设置dialog布局文件
        public CommonBuilder setContentView(@LayoutRes int layoutRes) {
            if (layoutRes != 0) {
                setContentView(LayoutInflater.from(getContext()).inflate(layoutRes, null));
            }
            return this;
        }

        //设置dialog布局
        public CommonBuilder setContentView(View view) {
            mContentView = view;
            return this;
        }
    }

    public static class MDBuilder extends FRBaseDialogBuilder<MDBuilder> {

        private CharSequence mNegativeContent;  //MD风格的取消按钮
        private FRDialogClickListener mNegativeListener;  //MD风格取消按钮的点击事件

        public MDBuilder(Context context) {
            this(context, R.style.dialog);
        }

        public MDBuilder(Context context, int themeResId) {
            super(context, themeResId);
            mContentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_material, null);
        }

        //设置MD效果dialog的头部
        public MDBuilder setTitle(CharSequence charSequence) {
            mTextArray.put(R.id.dialog_material_tv_title, charSequence);
            return this;
        }

        //设置MD效果dialog内容
        public MDBuilder setMessage(CharSequence charSequence) {
            mTextArray.put(R.id.dialog_material_tv_content, charSequence);
            return this;
        }

        public MDBuilder setNegativeContentAndListener(CharSequence charSequence, FRDialogClickListener onClickListener) {
            mNegativeContent = charSequence;
            mNegativeListener = onClickListener;
            mTextArray.put(R.id.dialog_material_tv_cancel, charSequence);
            mClickListenerArray.put(R.id.dialog_material_tv_cancel, onClickListener);
            return this;
        }

        public MDBuilder setPositiveContentAndListener(CharSequence charSequence, FRDialogClickListener onClickListener) {
            mTextArray.put(R.id.dialog_material_tv_confirm, charSequence);
            mClickListenerArray.put(R.id.dialog_material_tv_confirm, onClickListener);
            return this;
        }

        //设置MD效果dialog取消和确认键文字颜色
        public MDBuilder setNegativeTextColor(int color) {
            mTextColorArray.put(R.id.dialog_material_tv_cancel, color);
            return this;
        }

        //设置MD效果dialog取消和确认键文字颜色
        public MDBuilder setNegativeTextColor(ColorStateList color) {
            mTextColorStateListArray.put(R.id.dialog_material_tv_cancel, color);
            return this;
        }

        //设置MD效果dialog取消和确认键文字颜色
        public MDBuilder setPositiveTextColor(int color) {
            mTextColorArray.put(R.id.dialog_material_tv_confirm, color);
            return this;
        }

        //设置MD效果dialog取消和确认键文字颜色
        public MDBuilder setPositiveTextColor(ColorStateList color) {
            mTextColorStateListArray.put(R.id.dialog_material_tv_confirm, color);
            return this;
        }

        @Override
        protected boolean attachView() {
            if (!StringUtil.isEmpty(StringUtil.valueOf(mNegativeContent)) && null==mNegativeListener){
                getView(R.id.dialog_material_tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                });
            }
            return super.attachView();
        }
    }
}