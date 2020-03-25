package apps.simplee.interactivitycommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {

    public static final String KEY_STRING = "keyString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        TextView textView = findViewById(R.id.textId);
        final Bundle bundle = getIntent().getExtras();
        final String string = bundle.getString(MainActivity.MY_STRING,"No values");

        if (!string.equals("")) textView.setText(string);

        findViewById(R.id.btnNewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                 EditText edt = findViewById(R.id.edtNewId);

                bundle1.putString(KEY_STRING,edt.getText().toString());
                intent.putExtras(bundle1);

                setResult(RESULT_OK,intent);//it will invoke the method "onActivityResult" in MainActivity
                finish(); //this activity will finish
            }
        });
    }
}
