package com.pietheta.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ExampleItem> exampleList;
    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_cloud, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_content, "Line 5", "Line 6"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_cloud, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_content, "Line 5", "Line 6"));


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

adapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(int position) {

        changeItem(position,"Clicked");

    }

    @Override
    public void deleteItem(int position) {
        deleteItemAt(position);
    }
});
    }

    private void deleteItemAt(int position) {
        exampleList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void changeItem(int position, String string) {
        exampleList.get(position).changeLine1(string);
        adapter.notifyItemChanged(position);
    }
}
