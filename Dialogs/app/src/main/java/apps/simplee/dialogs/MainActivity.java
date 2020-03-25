package apps.simplee.dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import static apps.simplee.dialogs.MyDialog.DIALOG_ALERT;
import static apps.simplee.dialogs.MyDialog.DIALOG_CUSTOM;
import static apps.simplee.dialogs.MyDialog.DIALOG_DATE_PICKER;
import static apps.simplee.dialogs.MyDialog.DIALOG_PROGRESS;
import static apps.simplee.dialogs.MyDialog.DIALOG_TIME_PICKER;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       findViewById(R.id.btn_alert).setOnClickListener(this::clicked);
       findViewById(R.id.btn_datepicker).setOnClickListener(this::clicked);
       findViewById(R.id.btn_timepicker).setOnClickListener(this::clicked);
       findViewById(R.id.btn_progress).setOnClickListener(this::clicked);
       findViewById(R.id.btn_custom).setOnClickListener(this::clicked);
    }

    private void clicked(View view) {
        if(view.getId()==R.id.btn_alert) {
            //showAlert();
            showDialog(DIALOG_ALERT);
        }
        if(view.getId()==R.id.btn_datepicker) showDialog(DIALOG_DATE_PICKER);
        if(view.getId()==R.id.btn_timepicker) showDialog(DIALOG_TIME_PICKER);
        if(view.getId()==R.id.btn_progress) showDialog(DIALOG_PROGRESS);
        if(view.getId()==R.id.btn_custom) showDialog(DIALOG_CUSTOM);
    }

    private void showAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.message)
                .setTitle(R.string.tittle);
                /*.setPositiveButton(R.string.btnYes, (di,which)->{
                    mt("Positive Button Clicked");
                })*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialog(String tag) {

        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(),tag);
    }

    private void mt(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
