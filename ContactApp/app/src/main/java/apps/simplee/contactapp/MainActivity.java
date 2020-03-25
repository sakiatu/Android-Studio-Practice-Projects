package apps.simplee.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchContacts();
    }

    private void fetchContacts() {

       // Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uri = Uri.parse("content://com.example.assistant.PROVIDER");
        //String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String[] projection = null;
        String selection = null;//ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ " LIKE '%ashik%'";
        String[] selectionArgs =null;
        String sortOrder = null;
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        while (cursor.moveToNext()) {
            String number =cursor.getString(1);
            String name =cursor.getString(0);
//            Log.d("ContactApp","Name-"+name+" Number- "+number);
            info.add(name + "  " + number);
        }

        ListView listView = findViewById(R.id.list_id);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info));
    }
}
