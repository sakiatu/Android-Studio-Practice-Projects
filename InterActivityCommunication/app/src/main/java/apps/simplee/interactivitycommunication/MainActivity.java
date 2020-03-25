package apps.simplee.interactivitycommunication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String MY_STRING = "myString";
    public static final String MY_BOOLEAN = "myBool";
    public static final int REQ_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText=findViewById(R.id.edtId);
        findViewById(R.id.btnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AnotherActivity.class);

                Bundle values = new Bundle();
                String string= editText.getText().toString();
                values.putString(MY_STRING,string);
                values.putBoolean(MY_BOOLEAN,true);
                intent.putExtras(values);
                //startActivity(intent);
                startActivityForResult(intent,REQ_CODE);//if any value needed from the intended activity
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==REQ_CODE){
            if(resultCode==RESULT_OK){
                Bundle bundle=data.getExtras();
                String string = bundle.getString(AnotherActivity.KEY_STRING,"No values");
                TextView textView= findViewById(R.id.textMainId);
                textView.setText(string);
            }
        }
    }
}
