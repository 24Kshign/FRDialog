package cn.jake.share.frdialog.interfaces;

import android.view.View;

/**
 * Created by jack on 2018/2/10
 */

public interface FRDialogClickListener {

    /**
     *
     * @param view
     * @return 返回true会dismiss当前dialog
     */
    boolean onDialogClick(View view);
}