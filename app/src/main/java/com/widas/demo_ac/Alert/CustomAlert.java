package com.widas.demo_ac.Alert;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class CustomAlert {

    public SweetAlertDialog basicDialog(Activity activity, String title) {

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity)
                .setTitleText(title);
        return sweetAlertDialog;
    }

    public SweetAlertDialog dialogTitleSubTitle(Activity activity, String title, String contextTitle) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity)
                .setTitleText(title)
                .setContentText(contextTitle);
        return sweetAlertDialog;
    }

    public SweetAlertDialog showAlertDialog(Activity activity, String title, String contextTitle,
                                            Drawable drawable, int alertType, String confirmText, boolean showCancel, String cancelText) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, alertType)
                .setTitleText(title)
                .setContentText(contextTitle)
                .setConfirmText(confirmText);
        sweetAlertDialog.setCancelable(false);
        if (alertType == SweetAlertDialog.CUSTOM_IMAGE_TYPE && drawable != null) {
            sweetAlertDialog.setCustomImage(drawable);

        }
        if (showCancel) {
            sweetAlertDialog.showCancelButton(showCancel);
            sweetAlertDialog.setCancelText(cancelText);
        }
        return sweetAlertDialog;
    }
}
