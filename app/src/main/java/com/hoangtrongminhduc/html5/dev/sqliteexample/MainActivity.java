package com.hoangtrongminhduc.html5.dev.sqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;
    private List<Contact> contacts;
    private MyDatabase db = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();
        addControl();
    }

    private void addControl(){
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        contacts = new ArrayList<>();
        contacts=db.getAllContact();
        mAdapter = new RecycleAdapter(contacts, MainActivity.this);
        mAdapter.notifyDataSetChanged();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }
    //Ham xoa tat ca trong sqlite va adapter
    private void deleteAll(){
        db.deleteAllStudent();
        contacts.clear();
        mAdapter.notifyDataSetChanged();
    }
    //Ham goi menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_main, menu);
        return true;
    }
    //Xu li su kien chon nut tren action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivityForResult(intent,1);
                break;
            case R.id.action_delete:
                deleteAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //Nhan doi tuong contact tra ve tu activity Add va them vao adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode == RESULT_OK){
            Contact contact = (Contact) data.getExtras().getSerializable("RETURN");
            contacts.add(contact);
            mAdapter.notifyDataSetChanged();
        }
    }
}
