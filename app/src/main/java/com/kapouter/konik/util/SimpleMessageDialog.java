package com.kapouter.konik.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class SimpleMessageDialog {

    public static AlertDialog show(Context context, int titleId, int messageId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        AlertDialog dialog = dialogBuilder.setTitle(titleId)
                .setMessage(messageId)
                .create();
        dialog.show();
        return dialog;
    }

}
