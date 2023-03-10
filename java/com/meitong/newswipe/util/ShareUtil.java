package com.meitong.newswipe.util;

import android.content.Intent;

import com.meitong.newswipe.newswipeApplication;

public class ShareUtil {

    public static void share(String context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, context);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newswipeApplication.context.startActivity(intent);
    }
}
