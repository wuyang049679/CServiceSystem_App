package com.hc_android.hc_css.wight;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.ui.activity.MainActivity;

public class ChoiceDialog {


    private ChoiceCancelCallBack choiceCancelCallBack;
    public ChoiceDialog(@NonNull Context context, String text, int suc) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View inflate = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_choice, null);
        TextView textView = inflate.findViewById(R.id.text_hint);
        TextView ok = inflate.findViewById(R.id.click_ok);
        TextView cancel = inflate.findViewById(R.id.cancel_click);
        if (suc==1){
            cancel.setVisibility(View.GONE);
        }
        textView.setText(text);
        builder.setView(inflate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (choiceCancelCallBack!=null) {
                    choiceCancelCallBack.cancelBack();
                    choiceCancelCallBack.okBack();
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (choiceCancelCallBack!=null) {
                    choiceCancelCallBack.cancelBack();
                    choiceCancelCallBack.okBack();
                }
            }
        });
    }

    public static void updateApkDialog(@NonNull Context context, String url, UpdateConfiguration configuration) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_update_apk, null);
        TextView ok = inflate.findViewById(R.id.apk_update);
        builder.setView(inflate);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager manager = DownloadManager.getInstance(context);
                        manager.setConfiguration(configuration);
                        manager.setApkName("ai.hecong.apk")
                                .setApkUrl(url)
                                .setSmallIcon(R.mipmap.logo)
                                .download();
            }
        });
    }
    public void  setCancelCallBack(ChoiceCancelCallBack cancelCallBack){
        choiceCancelCallBack=cancelCallBack;
    }

    public interface ChoiceCancelCallBack{
        void cancelBack();
        void okBack();
    }


}
