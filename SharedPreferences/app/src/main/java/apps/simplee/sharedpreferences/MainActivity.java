package apps.simplee.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList array = new ArrayList<String>();
    ListView listView;
    ArrayAdapter adapter;
    View layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.list_id);
        adapter = new ArrayAdapter<>(getApplication(), R.layout.list_item_layout, R.id.textId, array);
        listView.setAdapter(adapter);
        loadSavedData();
        findViewById(R.id.btnRed).setOnClickListener(v -> setBackgroundColor(Color.RED));
        findViewById(R.id.btnWhite).setOnClickListener(v -> setBackgroundColor(Color.WHITE));
        findViewById(R.id.btnGreen).setOnClickListener(v -> setBackgroundColor(Color.GREEN));

        findViewById(R.id.addBtn).setOnClickListener(this::showAddItemDialog);

    }


    //methods
    private void loadSavedData() {
        SharedPreferences preferences = getSharedPreferences("color", MODE_PRIVATE);
        int color = preferences.getInt("color", Color.WHITE);
        setBackgroundColor(color);
    }

    private void setBackgroundColor(int color) {
        listView.setBackgroundColor(color);
        adapter.notifyDataSetChanged();
        saveColor(color);
    }

    private void saveColor(int color) {
        SharedPreferences preferences = getSharedPreferences("color", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("color", color);
        editor.commit();
    }

    private void showAddItemDialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        layout = LayoutInflater.from(listView.getContext()).inflate(R.layout.item_dialog, null);
        builder.setView(layout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        EditText editText = layout.findViewById(R.id.edtId);
        layout.findViewById(R.id.btnSaveId).setOnClickListener(v -> {
            array.add(editText.getText().toString());
            editText.setText("");
            adapter.notifyDataSetChanged();
        });
        layout.findViewById(R.id.btnCancel).setOnClickListener(v -> alertDialog.dismiss());


    }


}
