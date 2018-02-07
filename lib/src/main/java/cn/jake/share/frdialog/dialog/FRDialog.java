package cn.jake.share.frdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import cn.jake.share.frdialog.R;
import cn.jake.share.frdialog.dialog.interfaces.DialogClickListener;


/**
 * Created by jack on 2018/1/22
 */

public class FRDialog extends Dialog {

    FRDialogBuilder mBuilder;
    View rootView;
    private boolean delaySetContentView;
    private DialogLayoutParams setupDialogLayoutParams;

    FRDialog(@NonNull Context context) {
        this(context, 0);
    }

    FRDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    void attachBuilder(FRDialogBuilder builder) {
        mBuilder = builder;
        DialogLayoutParams p = builder.mLayoutParamsWrapper;
        setupDialogLayoutParams = builder.onGenerateDialogLayoutParams();
        if (setupDialogLayoutParams != null) {
            setupDialogLayoutParams.copy(p);
        } else {
            setupDialogLayoutParams = p;
        }
        if (getWindow() == null) {
            delaySetContentView = true;
        }
        builder.onAttachDialog(this);
        if (builder.mContentView == null) {
            if (builder.mContentViewResid == 0) {
                throw new NullPointerException("contentView not found,have you set contentView.please check has called #contentView(int) or #contentView(View)");
            } else {
                rootView = View.inflate(getContext(), builder.mContentViewResid, null);
            }
        } else {
            rootView = builder.mContentView;
        }
        builder.onInitView(rootView, setupDialogLayoutParams);
        if (!delaySetContentView) {
            setContentView(rootView);
            setUpWindow(getWindow());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        if (delaySetContentView) {
            setContentView(rootView);
            setUpWindow(window);
            delaySetContentView = false;
        }
    }

    protected void setUpWindow(Window window) {
        if (window == null || setupDialogLayoutParams == null) return;
        window.setWindowAnimations(setupDialogLayoutParams.mAnimation);
        window.setGravity(setupDialogLayoutParams.mGravity);
        window.setLayout(setupDialogLayoutParams.mWidth, setupDialogLayoutParams.mHeight);
    }

    @Override
    public void show() {
        super.show();
    }

    public <V extends View> V getView(int viewId) {
        return rootView == null ? null : (V) rootView.findViewById(viewId);
    }

    //=============================================================params

    public static class DialogLayoutParams {
        //dialog宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //dialog高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //dialog动画
        public int mAnimation;
        //dialog位置
        public int mGravity = Gravity.CENTER;

        public DialogLayoutParams() {

        }

        public DialogLayoutParams(DialogLayoutParams params) {
            copy(params);
        }


        void copy(DialogLayoutParams p) {
            mWidth = p == null ? ViewGroup.LayoutParams.WRAP_CONTENT : p.mWidth;
            mHeight = p == null ? ViewGroup.LayoutParams.WRAP_CONTENT : p.mHeight;
            mAnimation = p == null ? 0 : p.mAnimation;
            mGravity = p == null ? Gravity.CENTER : p.mGravity;
        }
    }

    //=============================================================builder

    public static class CommonBuilder extends FRBaseMessageDialogBuilder<CommonBuilder> {

        public CommonBuilder(Context context) {
            super(context);
        }

        public CommonBuilder(Context context, int themeId) {
            super(context, themeId);
        }

        @Override
        void onViewInit(@Nullable View view, @NonNull DialogLayoutParams params) {

        }

        @Nullable
        @Override
        DialogLayoutParams onGenerateDialogLayoutParams() {
            return null;
        }
    }

    public static class MDBuilder extends FRBaseMessageDialogBuilder<MDBuilder> {

        public MDBuilder(Context context) {
            super(context);
            contentViewInternal(R.layout.dialog_material);
        }

        public MDBuilder(Context context, int themeId) {
            super(context, themeId);
            contentViewInternal(R.layout.dialog_material);
        }

        @Deprecated
        @Override
        public MDBuilder contentView(View contentView) {
            //do nothing
            return castReturn();
        }

        @Deprecated
        @Override
        public MDBuilder contentView(int contentViewID) {
            //do nothing
            return castReturn();
        }

        @Nullable
        @Override
        DialogLayoutParams onGenerateDialogLayoutParams() {
            return null;
        }

        @Override
        void onViewInit(@Nullable View view, @NonNull DialogLayoutParams params) {

        }

        //设置MD效果dialog的头部
        public MDBuilder title(CharSequence charSequence) {
            return setText(R.id.dialog_material_tv_title, charSequence);
        }

        //设置MD效果dialog内容
        public MDBuilder message(CharSequence charSequence) {
            return setText(R.id.dialog_material_tv_content, charSequence);
        }

        public MDBuilder negativeText(CharSequence negativeText) {
            return setText(R.id.dialog_material_tv_cancel, negativeText);
        }

        public MDBuilder positiveText(CharSequence negativeText) {
            return setText(R.id.dialog_material_tv_confirm, negativeText);
        }

        public MDBuilder negativeTextColor(int negativeColor) {
            return setColor(R.id.dialog_material_tv_cancel, negativeColor);
        }

        public MDBuilder positiveTextColor(int negativeColor) {
            return setColor(R.id.dialog_material_tv_confirm, negativeColor);
        }

        public MDBuilder negativeClick(DialogClickListener l) {
            return addClick(R.id.dialog_material_tv_cancel, l);
        }

        public MDBuilder positiveClick(DialogClickListener l) {
            return addClick(R.id.dialog_material_tv_cancel, l);
        }

        public MDBuilder negative(CharSequence text, DialogClickListener l) {
            return setText(R.id.dialog_material_tv_cancel, text).addClick(R.id.dialog_material_tv_cancel, l);
        }

        public MDBuilder positive(CharSequence text, DialogClickListener l) {
            return setText(R.id.dialog_material_tv_confirm, text).addClick(R.id.dialog_material_tv_confirm, l);
        }
    }


}