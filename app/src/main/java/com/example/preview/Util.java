package com.example.preview;

/**
 * Created by Freed on 2017-1-16.
 */

import android.content.Context;
import android.widget.Toast;

/**
 * 将Toast的调用封装成一个接口，这样就不会每次调用都生成一个新的Toast对象了
 */
public class Util {

    private static Toast toast;

    public static void showToast(Context context, String content){
        if (toast == null) {    // 先判断toast对象是否为空
            // 如果为空，则调用makeText()
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {    // 否则调用setText()来显示新的内容
            toast.setText(content);
        }
        toast.show();
    }


}
