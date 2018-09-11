package cn.jake.share.frdialog.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Jack
 * Date：2018/9/11 下午1:49
 * Desc：
 */
public class FRBaseDialogViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public FRBaseDialogViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public <VIEW extends View> VIEW getView(@IdRes int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (VIEW) view;
    }


    ///////////////////////////////////////////////////////////////////////对View的操作/////////////////////////////////////////////////////////////////////

    public FRBaseDialogViewHolder setText(int viewId, CharSequence charSequence) {
        TextView tv = getView(viewId);
        tv.setText(charSequence);
        return this;
    }

    public FRBaseDialogViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public FRBaseDialogViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public FRBaseDialogViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public FRBaseDialogViewHolder setImagePath(int viewId, CommonImageLoader commonImageLoader) {
        //将第三方加载图片框架与之分离（解耦）
        ImageView view = getView(viewId);
        commonImageLoader.loadImageView(view, commonImageLoader.getImagePath());
        return this;
    }

    public FRBaseDialogViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public FRBaseDialogViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public FRBaseDialogViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public FRBaseDialogViewHolder setTextSize(int viewId, int textSize) {
        TextView view = getView(viewId);
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        return this;
    }

    public FRBaseDialogViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(getContext().getResources().getColor(textColorRes));
        return this;
    }

    public FRBaseDialogViewHolder setAlpha(int viewId, float value) {
        AlphaAnimation alpha = new AlphaAnimation(value, value);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        getView(viewId).startAnimation(alpha);
        return this;
    }

    public FRBaseDialogViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public FRBaseDialogViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public FRBaseDialogViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public FRBaseDialogViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public FRBaseDialogViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public FRBaseDialogViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public FRBaseDialogViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public FRBaseDialogViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public FRBaseDialogViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public FRBaseDialogViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public FRBaseDialogViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }


    ///////////////////////////////////////////////////////////////////////View的事件/////////////////////////////////////////////////////////////////////

    public FRBaseDialogViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public FRBaseDialogViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public FRBaseDialogViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


    public abstract static class CommonImageLoader {

        private String mImagePath;

        public CommonImageLoader(String imagePath) {
            mImagePath = imagePath;
        }

        public abstract void loadImageView(ImageView imageView, String imagePath);

        public String getImagePath() {
            return mImagePath;
        }
    }

}