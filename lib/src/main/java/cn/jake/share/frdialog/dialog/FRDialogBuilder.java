package cn.jake.share.frdialog.dialog;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by 大灯泡 on 2018/1/27.
 */
public abstract class FRDialogBuilder {

    //文案
    private SparseArray<CharSequence> mTextArray = new SparseArray<>();
    //点击事件
    private SparseArray<? extends View.OnClickListener> mClickListenerArray = new SparseArray<>();

    void reset() {
        mTextArray.clear();
        mClickListenerArray.clear();
    }
}
