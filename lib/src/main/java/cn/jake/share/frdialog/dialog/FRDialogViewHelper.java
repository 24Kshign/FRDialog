package cn.jake.share.frdialog.dialog;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.jake.share.frdialog.interfaces.FRDialogClickListener;
import cn.jake.share.frdialog.interfaces.FRDialogTextChangeListener;
import cn.jake.share.frdialog.util.StringUtil;

/**
 * Created by jack on 2018/1/22
 * Dialog View的辅助类
 */

class FRDialogViewHelper {

    private View mContentView;

    private SparseArray<WeakReference<View>> mViews;

    public FRDialog mDialog;

    public FRDialogViewHelper(View view) {
        this.mContentView = view;
        mViews = new SparseArray<>();
    }

    public View getContentView() {
        return mContentView;
    }

    public void setDialog(FRDialog dialog) {
        this.mDialog = dialog;
    }

    public <T extends View> T getView(@IdRes int idRes) {
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

    public void setText(@IdRes int id, CharSequence charSequence) {
        View view = getView(id);
        if (view instanceof TextView) {
            view.setVisibility(View.VISIBLE);
            ((TextView) view).setText(StringUtil.valueOf(charSequence));
        }
    }

    public void setOnClickListener(@IdRes int id, final View.OnClickListener onClickListener) {
        View view = getView(id);
        if (null != view && null != onClickListener) {
            view.setOnClickListener(onClickListener);
        }
    }

    public void setOnDialogClickListener(@IdRes int id, final FRDialogClickListener dialogClickListener) {
        View view = getView(id);
        if (null != view && null != dialogClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean dismiss = dialogClickListener.onDialogClick(v);
                    if (null != mDialog && dismiss) {
                        mDialog.dismiss();
                    }
                }
            });
        }
    }

    public void setTextColor(@IdRes int id, int color) {
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }

    public void addTextChangedListener(@IdRes int id, FRDialogTextChangeListener frDialogTextChangeListener) {
        View view = getView(id);
        if (null != view && view instanceof EditText && null != frDialogTextChangeListener) {
            ((EditText) view).addTextChangedListener(frDialogTextChangeListener);
        }
    }

    public void setVisibleOrGone(@IdRes int id, boolean isVisible) {
        View view = getView(id);
        if (null != view) {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}