package cn.jake.share.frdialog.dialog.interfaces;

import android.view.View;

import cn.jake.share.frdialog.dialog.FRDialog;
import cn.jake.share.frdialog.dialog.FRDialogBuilder;

/**
 * Created by 大灯泡 on 2018/2/7.
 */
public interface DialogClickListener<T extends FRDialogBuilder> {
    /**
     * true for dismiss
     *
     * @param dialog
     * @param dialogBuilder
     * @param clickedView
     * @return
     */
    boolean onClick(FRDialog dialog, T dialogBuilder, View clickedView);
}
