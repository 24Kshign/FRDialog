package cn.jake.share.frdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import cn.jake.share.frdialog.R;


/**
 * Created by jack on 2018/1/22
 */

public class FRDialog extends Dialog {

    FRDialogController controller;

    FRDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        controller = new FRDialogController(this, getWindow());
    }

    public <T extends View> T getView(int viewId) {
        return controller.getView(viewId);
    }

    public void setText(int id, CharSequence charSequence) {
        controller.setText(id, charSequence);
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        controller.setOnClickListener(id, onClickListener);
    }

    public void setMaterialDesignPositiveListener(View.OnClickListener onClickListener) {
        controller.setMaterialDesignPositiveListener(R.id.dialog_material_tv_confirm, onClickListener);
    }

    public void setMaterialDesignNegativeListener(View.OnClickListener onClickListener) {
        controller.setMaterialDesignNegativeListener(R.id.dialog_material_tv_cancel, onClickListener);
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
            mParams.mLayoutRes = layoutRes;
            mParams.mContentView = null;
            return this;
        }

        //设置dialog布局
        public CommonBuilder setContentView(View view) {
            mParams.mLayoutRes = 0;
            mParams.mContentView = view;
            return this;
        }

        //设置文字（dialog上任何一个控件）
        public CommonBuilder setText(int id, CharSequence charSequence) {
            mParams.mTextArray.put(id, charSequence);
            return this;
        }

        //设置点击事件（dialog上任何一个控件）
        public CommonBuilder setOnClickListener(int id, View.OnClickListener onClickListener) {
            mParams.mClickListenerArray.put(id, onClickListener);
            return this;
        }
    }

    /**
     * MD模式的Builder，只用来设置MD模式
     */
    public static class MDBuilder extends FRBaseDialogBuilder<MDBuilder> {

        public MDBuilder(Context context) {
            this(context, R.style.dialog);
        }

        public MDBuilder(Context context, int themeResId) {
            super(context, themeResId);
            mParams.mLayoutRes = R.layout.dialog_material;
            mParams.mIsMaterialDesign = true;
        }

        //设置MD效果dialog的头部
        public MDBuilder setTitle(CharSequence charSequence) {
            mParams.mMaterialDesignTitle = charSequence;
            return this;
        }

        //设置MD效果dialog内容
        public MDBuilder setMessage(CharSequence charSequence) {
            mParams.mMaterialDesignContent = charSequence;
            return this;
        }

        //设置MD效果dialog取消和确认键文字
        public MDBuilder setNegativeAndPositive(CharSequence... charSequence) {
            if (charSequence.length > 0) {
                mParams.mNegativeContent = charSequence[0];
            }
            if (charSequence.length > 1) {
                mParams.mPositiveContent = charSequence[1];
            }
            return this;
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