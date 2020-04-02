package com.pietheta.alarmclock;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private OnClickListener onClickListener;


    public interface OnClickListener {
        void onClicked(View view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.repeat_bottom_sheet_dialog, container, false);
        view.findViewById(R.id.once_repeat).setOnClickListener(v -> onClickListener.onClicked(v));
       view.findViewById(R.id.everyday_repeat).setOnClickListener(v -> onClickListener.onClicked(v));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onClickListener = (OnClickListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.repeat_bottom_sheet_dialog, null);
        dialog.setContentView(contentView);

        ((View) contentView.getParent()).setBackgroundColor(Color.TRANSPARENT);

    }
}
