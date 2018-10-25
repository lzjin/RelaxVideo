package com.fgkp.relax.mvp.custom;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.fgkp.relax.mvp.R;


/**
 * Created by Administrator on 2018/9/27.
 *  自定义弹框
 */

public class ProgressDialogFragment extends DialogFragment {
    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setLayout(dm.widthPixels, dm.heightPixels);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog show = builder.show();
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_bar,null);
        show.getWindow().setContentView(v);
        show.setCanceledOnTouchOutside(false);
        return show;
    }
}
