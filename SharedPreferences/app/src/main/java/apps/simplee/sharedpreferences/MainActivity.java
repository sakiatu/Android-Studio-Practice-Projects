package apps.simplee.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList array = new ArrayList<String>();
    EditText editText;
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edtId);
        findViewById(R.id.btnSaveId).setOnClickListener(this::save);

        listView = findViewById(R.id.list_id);
        adapter= new ArrayAdapter<>(getApplication(), R.layout.list_item_layout, R.id.textId, array);
        listView.setAdapter(adapter);
        readSavedData();
        findViewById(R.id.btnRed).setOnClickListener(v -> setBackgroundColor(Color.RED));
        findViewById(R.id.btnWhite).setOnClickListener(v -> setBackgroundColor(Color.WHITE));
        findViewById(R.id.btnGreen).setOnClickListener(v -> setBackgroundColor(Color.GREEN));
    }

    private void readSavedData() {
        SharedPreferences preferences = getSharedPreferences("color",MODE_PRIVATE);
        int color = preferences.getInt("color", Color.WHITE);
        setBackgroundColor(color);
    }

    private void setBackgroundColor(int color) {
       listView.setBackgroundColor(color);
        adapter.notifyDataSetChanged();
        saveColor(color);
    }

    private void saveColor(int color) {
        SharedPreferences preferences = getSharedPreferences("color",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("color",color);
        editor.commit();
    }


    private void save(View view) {
        if(view.getId()==R.id.btnSaveId && editText.getText().length()!=0){

            array.add(editText.getText().toString());
            editText.setText("");
            adapter.notifyDataSetChanged();
        }
    }
}
