package com.pietheta.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText nameEdt, ageEdt;
    TextView textView;
    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DbHelper(this, "my.db", null, 1);

        nameEdt = findViewById(R.id.edtName);
        ageEdt = findViewById(R.id.edtAge);
        textView = findViewById(R.id.text);
        findViewById(R.id.btnInsert).setOnClickListener(this::onClick);
        findViewById(R.id.btnUpdate).setOnClickListener(this::onClick);
        findViewById(R.id.btnDelete).setOnClickListener(this::onClick);
        findViewById(R.id.btnDisplay).setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view.getId() == R.id.btnInsert) insert();
        if (view.getId() == R.id.btnUpdate) update();
        if (view.getId() == R.id.btnDelete) delete();
        if (view.getId() == R.id.btnDisplay) display();

    }

    private void insert() {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", getName());
        values.put("age", getAge());
        database.insert("myTable", null, values);
        database.close();
    }

    private Integer getAge() {
        return Integer.parseInt(ageEdt.getText().toString());
    }

    private String getName() {
        return nameEdt.getText().toString();
    }

    private void update() {
        SQLiteDatabase database = helper.getWritableDatabase();
        String table="myTable";
        ContentValues values = new ContentValues();
        values.put("name",getName());
        String whereClause="age=?";
        String[] whereArgs={String.valueOf(getAge())};
        database.update(table, values, whereClause, whereArgs);

        database.close();

    }

    private void delete() {

        SQLiteDatabase database = helper.getWritableDatabase();
        String table="myTable";
        String whereClause="age=?";
        String[] whereArgs={String.valueOf(getAge())};
        database.delete(table, whereClause, whereArgs);

        database.close();
    }

    private void display() {
        SQLiteDatabase database = helper.getReadableDatabase();

        String table = "myTable";
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
builder.append(name+" "+age+"\n");
        }
        textView.setText(builder.toString());

        database.close();


    }
}
