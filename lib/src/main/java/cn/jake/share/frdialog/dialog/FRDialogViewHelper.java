package cn.jake.share.frdialog.dialog;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.jake.share.frdialog.image.CommonImageLoader;
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

    public String getContentById(@IdRes int id) {
        View view = getView(id);
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        } else {
            return "";
        }
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

    public void setTextColor(@IdRes int id, ColorStateList color) {
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

    public void setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            ((ImageView) (view)).setImageBitmap(bitmap);
        }
    }

    public void setImageDrawable(@IdRes int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            ((ImageView) (view)).setImageDrawable(drawable);
        }
    }

    public void setImagePath(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        //将第三方加载图片框架与之分离（解耦）——————这里主要参考红橙Darren的博客
        //https://www.jianshu.com/p/2c5a99984919
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            commonImageLoader.loadImageView(((ImageView) (view)), commonImageLoader.getImagePath());
        }
    }
}