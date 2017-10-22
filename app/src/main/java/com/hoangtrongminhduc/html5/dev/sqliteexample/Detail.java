package com.hoangtrongminhduc.html5.dev.sqliteexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {
    private TextView tvName, tvPhone, tvAddress, tvDate, tvTime, tvGender;
    private Contact contact;
    private ImageView btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addControl();
        addEvent();
    }
    //action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_detail, menu);
        return true;
    }

    private void addControl(){
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        tvAddress = (TextView)findViewById(R.id.tvAddress);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvTime = (TextView)findViewById(R.id.tvTime);
        tvGender = (TextView)findViewById(R.id.tvGender);
        btnCall = (ImageView)findViewById(R.id.btnCall);
    }
    //nhan doi tuong duoc chon trong activity main va set text cho layout
    private void addEvent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        contact =(Contact) bundle.getSerializable("SEND");
        setTextLayout(contact);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contact.getNumber())));
            }
        });
    }
    //xu li su kien khi chon vao nut action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.btnFix:
                Intent i = new Intent(Detail.this,EditContact.class);
                Bundle b = new Bundle();
                b.putSerializable("SEND_EDIT",contact);
                i.putExtras(b);
                startActivityForResult(i,1);
                break;
            case R.id.btnXoa:
                deleteItem();
                Toast.makeText(this, "Đã xóa contact!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //goi ham xoa 1 doi tuong va quay ve main activity sau khi xoa
    private void deleteItem(){
        MyDatabase db = new MyDatabase(this);
        db.deleteStudent(contact);
        Intent intent = new Intent(Detail.this, MainActivity.class);
        startActivity(intent);
    }
    //nhan ket qua tra ve tu editcontact sau khi fix va set laij texxt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode == RESULT_OK){
            contact = (Contact) data.getExtras().getSerializable("RETURN_EDIT");
            setTextLayout(contact);
        }
    }
    //set thong tin cho layout
    private void setTextLayout(Contact contact){
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getNumber());
        tvAddress.setText(contact.getAddress());
        tvDate.setText(contact.getDate());
        tvTime.setText(contact.getTime());
        tvGender.setText(contact.getGender());
    }
}
