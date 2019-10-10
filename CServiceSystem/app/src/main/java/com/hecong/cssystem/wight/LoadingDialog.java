package com.hecong.cssystem.wight;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;


/**
 * Description:
 *
 * @author wuyangwuyang
 * Created by 2019/3/1 on 16:48
 */
public class LoadingDialog extends DialogFragment {



    String msg="";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.loading_dialog, null);
//        TextView title = (TextView) view.findViewById(R.id.loading_msg);
//        title.setText(msg);
//        Dialog dialog = new Dialog(getActivity(), R.style.dialog);
//        dialog.setContentView(view);
//        return dialog;
        return null;
    }

    @Override
    public void show(android.app.FragmentManager manager, String tag) {
        if (tag!=null){
        msg=tag;
        }
        if(!this.isAdded()) {
            try {
                super.show(manager, tag);
            }catch (Exception e) {}
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        }catch (Exception e) {}

    }
}
