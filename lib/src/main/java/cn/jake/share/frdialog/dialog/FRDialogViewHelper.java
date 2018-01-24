package cn.jake.share.frdialog.dialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;


import java.lang.ref.WeakReference;

import cn.jake.share.frdialog.util.StringUtil;

/**
 * Created by jack on 2018/1/22
 */

class FRDialogViewHelper {

    private View mContentView;

    private SparseArray<WeakReference<View>> mViews;

    public FRDialogViewHelper(View view) {
        this.mContentView = view;
        mViews = new SparseArray<>();
    }

    public View getContentView() {
        return mContentView;
    }

    public <T extends View> T getView(int idRes) {
        //防止多次findViewById
        WeakReference<View> viewWeakReference = mViews.get(idRes);
        View view = null;
        if (null != viewWeakReference) {
            view = viewWeakReference.get();
        }
        if (null == view) {
            view = mContentView.findViewById(idRes);
            if (null != view) {
                mViews.put(idRes, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    public void setText(int id, CharSequence charSequence) {
        TextView tv = getView(id);
        if (null != tv) {
            tv.setText(StringUtil.valueOf(charSequence));
        }
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        View view = getView(id);
        if (null != view && null != onClickListener) {
            view.setOnClickListener(onClickListener);
        }
    }
}