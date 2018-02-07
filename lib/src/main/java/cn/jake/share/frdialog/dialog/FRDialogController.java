package cn.jake.share.frdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cn.jake.share.frdialog.R;


/**
 * Created by jack on 2018/1/22
 */

class FRDialogController {

    private FRDialog mMDialog;
    private Window mWindow;
    private FRDialogViewHelper dialogViewHelper;

    public FRDialogController(FRDialog dialog, Window window) {
        this.mMDialog = dialog;
        this.mWindow = window;
    }

    public FRDialog getDialog() {
        return mMDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setText(int id, CharSequence charSequence) {
        dialogViewHelper.setText(id, charSequence);
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        dialogViewHelper.setOnClickListener(id, onClickListener);
    }

    public void setDialogViewHelper(FRDialogViewHelper dialogViewHelper) {
        this.dialogViewHelper = dialogViewHelper;
    }

    public <T extends View> T getView(int viewId) {
        return dialogViewHelper.getView(viewId);
    }

    public void setMaterialDesignPositiveListener(int id, View.OnClickListener onClickListener) {
        dialogViewHelper.setOnClickListener(id, onClickListener);
    }

    public void setMaterialDesignNegativeListener(int id, View.OnClickListener onClickListener) {
        dialogViewHelper.setOnClickListener(id, onClickListener);
    }

    static class FRDialogParams {

        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable = true;  //点击返回键是否dismiss
        public boolean mCancelableOutside = true;  //点击外部是否dismiss
        /**
         * dialog监听事件
         */
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        /**
         * 布局和布局Id
         */
        public int mLayoutRes;
        public View mContentView;
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();  //dialog布局上的文案
        public SparseArray<View.OnClickListener> mClickListenerArray = new SparseArray<>(); //dialog上控件的点击事件
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;  //dialog宽度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;  //dialog高度
        public int mAnimation; //dialog动画
        public int mGravity = Gravity.CENTER;  //dialog位置


        public boolean mIsMaterialDesign = false; //是否是MaterialDesign
        public CharSequence mNegativeContent;  //MD风格的取消按钮
        public CharSequence mPositiveContent;  //MD风格的确认按钮
        public CharSequence mMaterialDesignTitle;   //MD风格的标题
        public CharSequence mMaterialDesignContent;   //MD风格的内容
        public View.OnClickListener mPositiveListener;   //MD风格确认按钮的点击事件
        public View.OnClickListener mNegativeListener;  //MD风格取消按钮的点击事件
        public int mNegativeTextColor;   //MD风格确认按钮的颜色
        public int mPositiveTextColor;  //MD风格取消按钮的颜色

        public FRDialogParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        //绑定View
        public void apply(final FRDialogController controller) {
            FRDialogViewHelper dialogViewHelper = null;

            if (mLayoutRes != 0) {
                dialogViewHelper = new FRDialogViewHelper(LayoutInflater.from(mContext).inflate(mLayoutRes, null));
            }
            if (null != mContentView) {
                dialogViewHelper = new FRDialogViewHelper(mContentView);
            }
            if (null == dialogViewHelper) {
                throw new IllegalArgumentException("前去设置setContentView");
            }

            controller.getDialog().setContentView(dialogViewHelper.getContentView());
            controller.setDialogViewHelper(dialogViewHelper);

            if (mIsMaterialDesign) {
                if (null != mPositiveContent && mPositiveContent.length() > 0) {
                    TextView tv = controller.getView(R.id.dialog_material_tv_confirm);
                    if (null != tv) {
                        tv.setVisibility(View.VISIBLE);
                        if (mPositiveTextColor != 0) {
                            tv.setTextColor(ContextCompat.getColor(mContext, mPositiveTextColor));
                        }
                        tv.setText(mPositiveContent);
                        if (null != mPositiveListener) {
                            tv.setOnClickListener(mPositiveListener);
                        }
                    }
                }
                if (null != mNegativeContent && mNegativeContent.length() > 0) {
                    TextView tv = controller.getView(R.id.dialog_material_tv_cancel);
                    if (null != tv) {
                        tv.setVisibility(View.VISIBLE);
                        if (mNegativeTextColor != 0) {
                            tv.setTextColor(ContextCompat.getColor(mContext, mNegativeTextColor));
                        }
                        tv.setText(mNegativeContent);
                        if (null != mNegativeListener) {
                            tv.setOnClickListener(mNegativeListener);
                        } else {
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    controller.getDialog().dismiss();
                                }
                            });
                        }
                    }
                }
                if (null != mMaterialDesignTitle && mMaterialDesignTitle.length() > 0) {
                    TextView tv = controller.getView(R.id.dialog_material_tv_title);
                    if (null != tv) {
                        tv.setText(mMaterialDesignTitle);
                    }
                }
                if (null != mMaterialDesignContent && mMaterialDesignContent.length() > 0) {
                    TextView tv = controller.getView(R.id.dialog_material_tv_content);
                    if (null != tv) {
                        tv.setText(mMaterialDesignContent);
                    }
                }
            } else {
                for (int i = 0; i < mTextArray.size(); i++) {
                    dialogViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
                }
                for (int i = 0; i < mClickListenerArray.size(); i++) {
                    dialogViewHelper.setOnClickListener(mClickListenerArray.keyAt(i), mClickListenerArray.valueAt(i));
                }
            }
            Window window = controller.getWindow();
            window.setGravity(mGravity);
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
            window.setLayout(mWidth, mHeight);
        }
    }
}