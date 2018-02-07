package cn.jake.share.frdialog.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.EditText;

/**
 * Created by jack on 2018/1/23
 */

public class StringUtil {

    private StringUtil() {
    }

    public static String EMPTY_STRING = "";

    public static String valueOf(Object value) {
        return value != null ? value.toString() : EMPTY_STRING;
    }

    public static boolean isEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static String getEditTextContent(EditText editText) {
        if (editText == null) {
            return null;
        } else {
            return editText.getText().toString().trim();
        }
    }

    /**
     * 从资源文件拿到文字
     */
    public static String getResString(Context context, @StringRes int strId) {
        return getResString(context, strId);
    }

    /**
     * 从资源文件得到文字并format
     */
    public static String getResString(Context context, @StringRes int strId, Object... objs) {
        if (strId == 0 || context == null) return null;
        return context.getResources().getString(strId, objs);
    }

}