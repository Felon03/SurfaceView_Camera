package com.example.preview;

/**
 * Created by Freed on 2017-1-16.
 */

import android.content.Context;
import android.widget.Toast;

/**
 * 将Toast的调用封装成一个接口
 */
public class Util {

    private static Toast toast;

    public static void showToast(Context context, String content){
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


}
