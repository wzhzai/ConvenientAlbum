package com.wzz.ConvenientAlbum.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import com.wzz.ConvenientAlbum.R;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class Utils {
    /**
     * 判断sd卡是否存在
     * @return 存在返回true
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 无SD卡的toast
     * @param context
     */
    public static void toastSDCardError(Context context) {
        Toast.makeText(context, R.string.sd_card_error, Toast.LENGTH_SHORT).show();
    }
}
