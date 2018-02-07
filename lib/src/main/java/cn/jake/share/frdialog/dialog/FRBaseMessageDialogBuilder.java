package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

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

    T setText(@IdRes int viewId, @StringRes int text) {
        return setText(viewId, StringUtil.getResString(mContext, text));
    }

    T setText(@IdRes int viewId, @StringRes int text, Object... obj) {
        return setText(viewId, StringUtil.getResString(mContext, text, obj));
    }


    T setText(@IdRes int viewId, CharSequence text) {
        getViewWrapper(viewId).text = text;
        return castReturn();
    }

    T setColorRes(@IdRes int viewId, @ColorRes int color) {
        return setColor(viewId, mContext.getResources().getColor(color));
    }

    T setColor(@IdRes int viewId, @ColorInt int color) {
        getViewWrapper(viewId).color = color;
        return castReturn();
    }

    T addClick(@IdRes int viewId, View.OnClickListener l) {
        getViewWrapper(viewId).mOnClickListener = l;
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

    private static class InnerViewWrapper implements Parcelable {
        View v;
        View.OnClickListener mOnClickListener;
        CharSequence text;
        int color;

        public InnerViewWrapper() {
        }

        void prepare(@IdRes int viewId, View contentView) {
            if (v == null) {
                v = contentView.findViewById(viewId);
            }
            if (v == null) return;
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(color);
                ((TextView) v).setText(text);
            }
            v.setOnClickListener(mOnClickListener);
        }

        protected InnerViewWrapper(Parcel in) {
            color = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(color);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<InnerViewWrapper> CREATOR = new Creator<InnerViewWrapper>() {
            @Override
            public InnerViewWrapper createFromParcel(Parcel in) {
                return new InnerViewWrapper(in);
            }

            @Override
            public InnerViewWrapper[] newArray(int size) {
                return new InnerViewWrapper[size];
            }
        };
    }

}
