package cn.jakemesdg.commondialog.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import cn.jake.share.frdialog.dialog.FRDialog;
import cn.jake.share.frdialog.interfaces.FRDialogClickListener;

/**
 * Created by manji
 * Date：2018/11/12 5:00 PM
 * Desc：
 */
public class DialogService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new FRDialog.MDBuilder(this)
                .setTitle("测试")
                .setMessage("这是一个测试")
                .setPositiveContentAndListener("确定", new FRDialogClickListener() {
                    @Override
                    public boolean onDialogClick(View view) {
                        Log.e("TAG", "确定");
                        return true;
                    }
                })
                .setNegativeContentAndListener("取消", null)
                .show();
        return super.onStartCommand(intent, flags, startId);
    }
}
