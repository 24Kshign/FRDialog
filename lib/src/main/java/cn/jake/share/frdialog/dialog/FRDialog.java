package cn.jake.share.frdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.jake.share.frdialog.R;
import cn.jake.share.frdialog.image.CommonImageLoader;
import cn.jake.share.frdialog.interfaces.FRDialogClickListener;
import cn.jake.share.frdialog.interfaces.FRDialogTextChangeListener;
import cn.jake.share.frdialog.recyclerview.FRBaseDialogAdapter;
import cn.jake.share.frdialog.recyclerview.wrap.WrapRecyclerAdapter;
import cn.jake.share.frdialog.recyclerview.wrap.WrapRecyclerView;
import cn.jake.share.frdialog.util.FRInputMethodManager;
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

    public void setText(@IdRes int id, CharSequence charSequence) {
        dialogViewHelper.setText(id, charSequence);
    }

    public void setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        dialogViewHelper.setImageBitmap(viewId, bitmap);
    }

    public void setImageDrawable(@IdRes int viewId, Drawable drawable) {
        dialogViewHelper.setImageDrawable(viewId, drawable);
    }

    public void setImagePath(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        dialogViewHelper.setImagePath(viewId, commonImageLoader);
    }

    public void setVisibleOrGone(@IdRes int id, boolean isVisible) {
        dialogViewHelper.setVisibleOrGone(id, isVisible);
    }

    public void setOnClickListener(@IdRes int id, FRDialogClickListener onClickListener) {
        dialogViewHelper.setOnDialogClickListener(id, onClickListener);
    }

    public void addTextChangedListener(@IdRes int id, FRDialogTextChangeListener frDialogTextChangeListener) {
        dialogViewHelper.addTextChangedListener(id, frDialogTextChangeListener);
    }

    public String getContentById(@IdRes int id) {
        return dialogViewHelper.getContentById(id);
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
    }

    /**
     * 点击dialog中除EditText以外的区域隐藏软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        FRInputMethodManager.autoHideSoftInput(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    FRDialogViewHelper getDialogViewHelper() {
        return dialogViewHelper;
    }

    public static class CommonBuilder extends FRBaseDialogBuilder {

        public CommonBuilder(Context context) {
            this(context, R.style.dialog);
        }

        public CommonBuilder(Context context, int themeResId) {
            super(context, themeResId);
        }

        //设置dialog布局文件
        public CommonBuilder setContentView(@LayoutRes int layoutRes) {
            if (layoutRes != 0) {
                setContentView(LayoutInflater.from(mContext).inflate(layoutRes, null));
            }
            return this;
        }

        //设置dialog布局
        public CommonBuilder setContentView(View view) {
            mContentView = view;
            return this;
        }
    }

    public static class MDBuilder extends FRBaseDialogBuilder {

        private CharSequence mNegativeContent;  //MD风格的取消按钮
        private FRDialogClickListener mNegativeListener;  //MD风格取消按钮的点击事件

        public MDBuilder(Context context) {
            this(context, R.style.dialog);
        }

        public MDBuilder(Context context, int themeResId) {
            super(context, themeResId);
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_material, null);
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
            if (!StringUtil.isEmpty(StringUtil.valueOf(mNegativeContent)) && null == mNegativeListener) {
                mDialogViewHelper.setOnDialogClickListener(R.id.dialog_material_tv_cancel
                        , new FRDialogClickListener() {
                            @Override
                            public boolean onDialogClick(View view) {
                                return true;
                            }
                        });
            }
            return super.attachView();
        }
    }

    public static class RecyclerViewBuilder extends FRBaseDialogBuilder {

        private WrapRecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;
        private FRBaseDialogAdapter mDialogAdapter;
        private List<View> mViewHeaders;
        private List<View> mViewFooters;
        private List<Object> mDataList;

        public RecyclerViewBuilder(Context context) {
            this(context, R.style.dialog);
        }

        public RecyclerViewBuilder(Context context, int themeResId) {
            super(context, themeResId);
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_recyclerview, null);
            mViewHeaders = new ArrayList<>();
            mViewFooters = new ArrayList<>();
        }

        public RecyclerViewBuilder setAdapter(FRBaseDialogAdapter dialogAdapter) {
            mDialogAdapter = dialogAdapter;
            return this;
        }

        public RecyclerViewBuilder setDataList(List<Object> dataList) {
            mDataList = dataList;
            return this;
        }

        public RecyclerViewBuilder setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            mLayoutManager = layoutManager;
            return this;
        }

        public RecyclerViewBuilder addDialogHeader(@LayoutRes int layoutRes) {
            ((ViewGroup) mContentView).addView(LayoutInflater.from(mContext).inflate(layoutRes
                    , (ViewGroup) mContentView, false), 0);
            return this;
        }

        public RecyclerViewBuilder addDialogHeader(View viewHeader) {
            ((ViewGroup) mContentView).addView(viewHeader, 0);
            return this;
        }

        public RecyclerViewBuilder addDialogFooter(View viewFooter) {
            ((ViewGroup) mContentView).addView(viewFooter);
            return this;
        }

        public RecyclerViewBuilder addDialogFooter(@LayoutRes int layoutRes) {
            ((ViewGroup) mContentView).addView(LayoutInflater.from(mContext).inflate(layoutRes
                    , (ViewGroup) mContentView, false));
            return this;
        }

        public RecyclerViewBuilder addRecyclerViewHeader(@LayoutRes int layoutRes) {
            mViewHeaders.add(LayoutInflater.from(mContext).inflate(layoutRes, (ViewGroup) mContentView
                    , false));
            return this;
        }

        public RecyclerViewBuilder addRecyclerViewHeader(View viewHeader) {
            mViewHeaders.add(viewHeader);
            return this;
        }

        public RecyclerViewBuilder addRecyclerViewFooter(View viewFooter) {
            mViewFooters.add(viewFooter);
            return this;
        }

        public RecyclerViewBuilder addRecyclerViewFooter(@LayoutRes int layoutRes) {
            mViewFooters.add(LayoutInflater.from(mContext).inflate(layoutRes, (ViewGroup) mContentView, false));
            return this;
        }

        @Override
        protected boolean attachView() {
            if (null != mDialogAdapter) {
                WrapRecyclerAdapter wrapRecyclerAdapter = new WrapRecyclerAdapter(mDialogAdapter);
                if (null != mDataList) {
                    mDialogAdapter.setDataList(mDataList);
                }
                if (null == mLayoutManager) {
                    mLayoutManager = new LinearLayoutManager(mContext);
                }
                mRecyclerView = getView(R.id.dr_recyclerview);
                mRecyclerView.setLayoutManager(mLayoutManager);
                for (int i = 0; i < mViewHeaders.size(); i++) {
                    wrapRecyclerAdapter.addHeaderView(mViewHeaders.get(i));
                }
                for (int i = 0; i < mViewFooters.size(); i++) {
                    wrapRecyclerAdapter.addFooterView(mViewFooters.get(i));
                }
                mRecyclerView.setAdapter(wrapRecyclerAdapter);
            }
            return super.attachView();
        }
    }
}