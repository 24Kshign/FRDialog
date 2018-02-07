package cn.jake.share.frdialog.dialog.interfaces;

import android.view.View;

import cn.jake.share.frdialog.dialog.FRDialog;

/**
 * Created by 大灯泡 on 2018/2/7.
 */
public interface DialogClickListener {
    /**
     * true for dismiss
     *
     * @param dialog
     * @param clickedView
     * @return
     */
    boolean onClick(FRDialog dialog, View clickedView);
}
