package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import cn.jake.share.frdialog.dialog.interfaces.DialogClickListener;
import cn.jake.share.frdialog.util.StringUtil;

/**
 * Created by 大灯泡 on 2018/1/27.
 */
public abstract class FRBaseMessageDialogBuilder<T extends FRBaseMessageDialogBuilder> extends FRDialogBuilder<T> {
    public SparseArray<InnerViewWrapper> mViewWrappers = new SparseArray<>();

    public FRBaseMessageDialogBuilder(Context context) {
        super(context);
    }

    public FRBaseMessageDialogBuilder(Context context, int themeId) {
        super(context, themeId);
    }

    public T setText(@IdRes int viewId, @StringRes int text) {
        return setText(viewId, StringUtil.getResString(mContext, text));
    }

    public T setText(@IdRes int viewId, @StringRes int text, Object... obj) {
        return setText(viewId, StringUtil.getResString(mContext, text, obj));
    }

    public T setText(@IdRes int viewId, CharSequence text) {
        getViewWrapper(viewId).text = text;
        return castReturn();
    }

    public T setColorRes(@IdRes int viewId, @ColorRes int color) {
        return setColor(viewId, mContext.getResources().getColor(color));
    }

    public T setColor(@IdRes int viewId, @ColorInt int color) {
        getViewWrapper(viewId).color = color;
        return castReturn();
    }

    public T addClick(@IdRes int viewId, DialogClickListener l) {
        getViewWrapper(viewId).mDialogClickListener = l;
        return castReturn();
    }

    private InnerViewWrapper getViewWrapper(int key) {
        InnerViewWrapper viewWrapper = mViewWrappers.get(key);
        if (viewWrapper == null) {
            viewWrapper = new InnerViewWrapper();
            mViewWrappers.put(key, viewWrapper);
        }
        return viewWrapper;
    }

    @Override
    void onInitView(@NonNull View contentView, @NonNull FRDialog.DialogLayoutParams params) {
        final int size = mViewWrappers.size();
        for (int i = 0; i < size; i++) {
            InnerViewWrapper viewWrapper = mViewWrappers.valueAt(i);
            viewWrapper.prepare(mViewWrappers.keyAt(i), contentView);
            onViewInit(viewWrapper.v, params);
        }
    }

    abstract void onViewInit(@Nullable View view, @NonNull FRDialog.DialogLayoutParams params);

    private class InnerViewWrapper implements Serializable {
        View v;
        DialogClickListener mDialogClickListener;
        CharSequence text;
        int color = -1;

        public InnerViewWrapper() {
        }

        void prepare(@IdRes int viewId, View contentView) {
            if (v == null) {
                v = contentView.findViewById(viewId);
            }
            if (v == null) return;
            v.setVisibility(View.VISIBLE);
            if (v instanceof TextView) {
                if (color != -1) {
                    ((TextView) v).setTextColor(color);
                }
                if (!TextUtils.isEmpty(text)) {
                    ((TextView) v).setText(text);
                }
            }
            if (mDialogClickListener != null) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean dismiss = mFRDialog != null && mDialogClickListener.onClick(mFRDialog, v);
                        if (dismiss) {
                            mFRDialog.dismiss();
                        }
                    }
                });
            } else {
                v.setOnClickListener(null);
            }
        }

    }

}
