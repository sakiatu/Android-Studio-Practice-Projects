package apps.simplee.dialogs;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


public class MyDialog extends DialogFragment {
    public static final String DIALOG_ALERT = "alert";
    public static final String DIALOG_DATE_PICKER = "datePicker";
    public static final String DIALOG_TIME_PICKER = "timePicker";
    public static final String DIALOG_CUSTOM = "custom";
    public static final String DIALOG_PROGRESS = "progress";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(DIALOG_ALERT)) dialog = showAlertDialog();
        if (getTag().equals(DIALOG_DATE_PICKER)) dialog = showDatePicker();
        if (getTag().equals(DIALOG_TIME_PICKER)) dialog = showTimePicker();
        if (getTag().equals(DIALOG_PROGRESS)) dialog = showProgressDialog();
        if (getTag().equals(DIALOG_CUSTOM)) dialog = showCustomDialog();

        return dialog;
    }

    private Dialog showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog, null);
        layout.findViewById(R.id.btnLogin).setOnClickListener(v -> mt("Login Clicked"));
        builder.setView(layout);
        return builder.create();
    }

    private Dialog showProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(R.string.tittle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.message));
        progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE,getString(R.string.btnYes),((dialog, which) -> mt("yes clicked")));

        return progressDialog;

    }

    private Dialog showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), ((view, hourOfDay, minute) -> mt(hourOfDay + ":" + minute)), 11, 8, false);
        return timePickerDialog;
    }

    private Dialog showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.message)
                .setTitle(R.string.tittle)
                .setPositiveButton(R.string.btnYes, this::alertButtonClicked)
                .setNegativeButton(R.string.btnNo, this::alertButtonClicked);
        return builder.create();
    }

    private Dialog showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                ((view, year, month, dayOfMonth) -> mt(dayOfMonth + "/" + (month + 1) + "/" + year)),
                2020, 0, 1);
        return datePickerDialog;
    }

    private void alertButtonClicked(DialogInterface dialogInterface, int whichButton) {
        if (whichButton == DialogInterface.BUTTON_POSITIVE) {
            mt("Positive");
        } else if (whichButton == DialogInterface.BUTTON_NEGATIVE) {
            mt("Negative");
        } else {
            mt("Neutral");
        }
    }

    private void mt(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
