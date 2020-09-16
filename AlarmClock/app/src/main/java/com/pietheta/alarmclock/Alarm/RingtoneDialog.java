package com.pietheta.alarmclock.Alarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pietheta.alarmclock.R;

public class RingtoneDialog extends DialogFragment {
    public static final String RINGTONE_DIALOG = "RINGTONE";

    interface OnRingtoneClickListener {
        void onClick(String name, String uri);
    }

    OnRingtoneClickListener listener;

    public void setOnRingtoneClickListener(OnRingtoneClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag() == RINGTONE_DIALOG) dialog = getRingtoneDialog();
        return dialog;
    }

    private Dialog getRingtoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.ringtone_list, null);
        RecyclerView ringtoneList = view.findViewById(R.id.ringtoneList);
        RingtoneAdapter adapter = new RingtoneAdapter(getActivity());
        ringtoneList.setAdapter(adapter);
        builder.setView(view);

        adapter.setOnItemClickListener((name, uri) -> {
            listener.onClick(name, uri);
            dismiss();
        });

        return builder.create();
    }
}
