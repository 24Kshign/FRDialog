package cn.jake.share.frdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        final WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.windowAnimations = setupDialogLayoutParams.mAnimation;
        attrs.gravity = setupDialogLayoutParams.mGravity;
        attrs.width = setupDialogLayoutParams.mWidth;
        attrs.height = setupDialogLayoutParams.mHeight;
        window.setAttributes(attrs);
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

    public static class MDBuilder<B extends MDBuilder> extends FRBaseMessageDialogBuilder<B> {

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
        public B contentView(View contentView) {
            //do nothing
            return castReturn();
        }

        @Deprecated
        @Override
        public B contentView(int contentViewID) {
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
        public B title(CharSequence charSequence) {
            return (B) setText(R.id.dialog_material_tv_title, charSequence);
        }

        //设置MD效果dialog内容
        public B message(CharSequence charSequence) {
            return (B) setText(R.id.dialog_material_tv_content, charSequence);
        }

        public B negativeText(CharSequence negativeText) {
            return (B) setText(R.id.dialog_material_tv_cancel, negativeText);
        }

        public B positiveText(CharSequence positiveText) {
            return (B) setText(R.id.dialog_material_tv_confirm, positiveText);
        }

        public B negativeTextColor(int negativeColor) {
            return (B) setColor(R.id.dialog_material_tv_cancel, negativeColor);
        }

        public B positiveTextColor(int positiveColor) {
            return (B) setColor(R.id.dialog_material_tv_confirm, positiveColor);
        }

        public B negativeClick(DialogClickListener<B> l) {
            return (B) addClick(R.id.dialog_material_tv_cancel, l);
        }

        public B positiveClick(DialogClickListener<B> l) {
            return (B) addClick(R.id.dialog_material_tv_cancel, l);
        }

        public B negative(CharSequence text, DialogClickListener<? extends MDBuilder> l) {
            return (B) setText(R.id.dialog_material_tv_cancel, text).addClick(R.id.dialog_material_tv_cancel, l);
        }

        public B positive(CharSequence text, DialogClickListener<? extends MDBuilder> l) {
            return (B) setText(R.id.dialog_material_tv_confirm, text).addClick(R.id.dialog_material_tv_confirm, l);
        }

        @Override
        B castReturn() {
            return (B) super.castReturn();
        }
    }

    public static class SelectedBuilder extends MDBuilder<SelectedBuilder> {
        protected RecyclerView mRecyclerView;
        protected List<SelectModelWrapper> descs;
        protected int normalDrawableRes = R.drawable.ic_check_normal;
        protected int checkedDrawableRes = R.drawable.ic_check_checked;
        protected CheckMode mCheckMode = CheckMode.SINGLE;
        protected OnCheckChangeListener mOnCheckChangeListener;
        protected InnerAdapter mAdapter;

        public enum CheckMode {
            SINGLE,
            NONE,
            MULTI
        }

        public interface ISelect extends Serializable {
            CharSequence getDesc();
        }

        protected class SelectModelWrapper implements ISelect {
            protected CharSequence desc;
            protected boolean checked;
            protected ISelect target;

            public SelectModelWrapper(CharSequence desc, boolean checked) {
                this.desc = desc;
                this.checked = checked;
            }

            public SelectModelWrapper(ISelect target) {
                this(target, false);
            }

            public SelectModelWrapper(ISelect target, boolean checked) {
                this.target = target;
                this.checked = checked;
            }

            @Override
            public CharSequence getDesc() {
                return target == null ? desc : target.getDesc();
            }
        }


        public SelectedBuilder(Context context) {
            super(context);
            contentViewInternal(R.layout.dialog_recyclerview);
            descs = new ArrayList<>();
        }

        public SelectedBuilder(Context context, int themeId) {
            super(context, themeId);
            contentViewInternal(R.layout.dialog_recyclerview);
            descs = new ArrayList<>();
        }

        @Override
        SelectedBuilder castReturn() {
            return (SelectedBuilder) super.castReturn();
        }

        @Deprecated
        @Override
        public SelectedBuilder contentView(View contentView) {
            //do nothing
            return castReturn();
        }

        @Deprecated
        @Override
        public SelectedBuilder contentView(int contentViewID) {
            //do nothing
            return castReturn();
        }

        @Override
        void onInitView(@NonNull View contentView, @NonNull DialogLayoutParams params) {
            super.onInitView(contentView, params);
            if (mRecyclerView == null) {
                mRecyclerView = contentView.findViewById(R.id.rv_content);
                mRecyclerView.setItemAnimator(null);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            }
            if (mAdapter == null) {
                mAdapter = new InnerAdapter();
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        void onViewInit(@Nullable View view, @NonNull DialogLayoutParams params) {

        }

        @Nullable
        @Override
        DialogLayoutParams onGenerateDialogLayoutParams() {
            return null;
        }

        public SelectedBuilder normalCheckRes(@DrawableRes int res) {
            normalDrawableRes = res;
            return castReturn();
        }

        public SelectedBuilder checkRes(@DrawableRes int res) {
            checkedDrawableRes = res;
            return castReturn();
        }

        public SelectedBuilder addData(ISelect select) {
            return addData(select, false);
        }

        public SelectedBuilder addData(ISelect select, boolean checked) {
            descs.add(new SelectModelWrapper(select, checked));
            return castReturn();
        }

        public SelectedBuilder addData(CharSequence desc) {
            return addData(desc, false);
        }

        public SelectedBuilder addData(CharSequence desc, boolean checked) {
            descs.add(new SelectModelWrapper(desc, checked));
            return castReturn();
        }

        public SelectedBuilder addDatas(List<? extends ISelect> datas) {
            if (datas != null) {
                descs.clear();
                for (ISelect data : datas) {
                    addData(data);
                }
            }
            return castReturn();
        }

        public SelectedBuilder addDatas(CharSequence... datas) {
            for (CharSequence data : datas) {
                addData(data);
            }
            return castReturn();
        }

        public SelectedBuilder checkMode(CheckMode mode) {
            this.mCheckMode = mode;
            return castReturn();
        }

        public SelectedBuilder checkListener(OnCheckChangeListener l) {
            this.mOnCheckChangeListener = l;
            return castReturn();
        }

        public List<ISelect> getSelectedDatas() {
            List<ISelect> result = new ArrayList<>();
            if (descs == null) return result;
            for (SelectModelWrapper desc : descs) {
                if (desc.checked) {
                    result.add(desc.target == null ? desc : desc.target);
                }
            }
            return result;
        }

        protected class InnerAdapter extends RecyclerView.Adapter<InnerViewHolder> {

            @Override
            public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = parent.inflate(parent.getContext(), R.layout.item_select, null);
                return new InnerViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(InnerViewHolder holder, int position) {
                holder.onBindData(position, descs.get(position));
            }

            @Override
            public int getItemCount() {
                return descs == null ? 0 : descs.size();
            }

        }

        protected class InnerViewHolder extends RecyclerView.ViewHolder {
            protected TextView desc;
            protected ImageView check;

            public InnerViewHolder(View itemView) {
                super(itemView);
                desc = itemView.findViewById(R.id.tv_desc);
                check = itemView.findViewById(R.id.iv_check);
                if (mCheckMode == CheckMode.NONE) check.setVisibility(View.GONE);
            }

            public void onBindData(final int position, final SelectModelWrapper data) {
                if (data == null) return;
                if (mCheckMode == CheckMode.NONE && check.getVisibility() != View.GONE) {
                    check.setVisibility(View.GONE);
                } else {
                    if (check.getVisibility() != View.VISIBLE) check.setVisibility(View.VISIBLE);
                }
                desc.setText(data.getDesc());
                check.setImageResource(data.checked ? checkedDrawableRes : normalDrawableRes);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (mCheckMode) {
                            case NONE:
                            case SINGLE:
                                for (SelectModelWrapper selectModelWrapper : descs) {
                                    if (selectModelWrapper != data) {
                                        selectModelWrapper.checked = false;
                                    }
                                }
                                break;
                        }
                        onCheck(position, data, !data.checked);
                        if (mOnCheckChangeListener != null) {
                            mOnCheckChangeListener.onCheckChange(data.target == null ? data : data.target, position, data.checked);
                        }
                    }
                });
            }

            void onCheck(int position, SelectModelWrapper data, boolean checked) {
                if (data.checked != checked) {
                    data.checked = checked;
                }
                mAdapter.notifyDataSetChanged();
            }

        }

        public interface OnCheckChangeListener {
            void onCheckChange(ISelect select, int position, boolean checked);
        }
    }


}