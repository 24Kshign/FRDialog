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
import android.view.WindowManager;

import cn.jake.share.frdialog.R;


/**
 * Created by jack on 2018/1/22
 */

public class FRDialog extends Dialog {

    FRDialogBuilder mBuilder;
    View rootView;

    FRDialog(@NonNull Context context) {
        this(context, 0);
    }

    FRDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    void attachBuilder(FRDialogBuilder builder) {
        mBuilder = builder;
        DialogLayoutParams p = builder.mLayoutParamsWrapper;
        DialogLayoutParams setUpParams = builder.onGenerateDialogLayoutParams();
        if (setUpParams != null) {
            setUpParams.copy(p);
        } else {
            setUpParams = p;
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
        builder.onInitView(rootView, setUpParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams windowLP = window.getAttributes();
        windowLP.width = mBuilder.mLayoutParamsWrapper.mWidth;
        window.setAttributes(windowLP);
    }

    @Override
    public void show() {
        super.show();
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


    public static class MDBuilder extends FRBaseMessageDialogBuilder<MDBuilder> {

        public MDBuilder(Context context) {
            super(context);
        }

        public MDBuilder(Context context, int themeId) {
            super(context, themeId);
            contentView(R.layout.dialog_material);
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

        public MDBuilder positiveText(CharSequence negativeText){
            return setText(R.id.dialog_material_tv_confirm, negativeText);
        }

        public MDBuilder negativeTextColor(){

        }

        //设置MD效果dialog取消和确认键文字颜色
        public MDBuilder setNegativeAndPositiveTextColor(Integer... colors) {
            if (colors.length > 0) {
                mParams.mNegativeTextColor = colors[0];
            }
            if (colors.length > 1) {
                mParams.mPositiveTextColor = colors[1];
            }
            return this;
        }

        //设置MD效果dialog确认键点击事件
        public MDBuilder setPositiveListener(View.OnClickListener onClickListener) {
            mParams.mPositiveListener = onClickListener;
            return this;
        }

        //设置MD效果dialog取消键点击事件（默认不设置的效果为弹窗消失）
        public MDBuilder setNegativeListener(View.OnClickListener onClickListener) {
            mParams.mNegativeListener = onClickListener;
            return this;
        }
    }


}