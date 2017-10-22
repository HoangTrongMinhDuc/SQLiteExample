package com.hoangtrongminhduc.html5.dev.sqliteexample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add extends AppCompatActivity implements View.OnClickListener{
    private Button btnAdd, btnCancel;
    private EditText edtName, edtPhone, edtAdress;
    private RadioGroup radioGroup;
    private RadioButton radioGender;
    private MyDatabase db = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addControl();
    }

    private void addControl(){
        edtName = (EditText)findViewById(R.id.edtName);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtAdress = (EditText)findViewById(R.id.edtAddress);
        radioGroup = (RadioGroup)findViewById(R.id.radioGender);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        //Nhan vao nut Add va Cancel
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    //Ham xu li chinh, them doi tuong vao sqlite va recycle
    @TargetApi(Build.VERSION_CODES.N)
    private void add(){
        //lay thong tin da nhap gan vao doi tuong
        Contact contact = new Contact();
        contact.setName(edtName.getText().toString());
        contact.setNumber(edtPhone.getText().toString());
        contact.setAddress(edtAdress.getText().toString());
        long day = System.currentTimeMillis();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(day));
        contact.setDate(date);
        String h = new SimpleDateFormat("hh:mm:ss").format(new Date(day));
        contact.setTime(h);
        int idrad = radioGroup.getCheckedRadioButtonId();
        radioGender = (RadioButton)findViewById(idrad);
        contact.setGender(radioGender.getText().toString());
        //them vao sqlite
        db.addContact(contact);
        db.close();
        //gui doi tuong moi tao them vao recycle o MainActivity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("RETURN",contact);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);

        Toast.makeText(this, "Đã thêm",Toast.LENGTH_SHORT).show();
        finish();
    }

    //Xu li su kien tuong ung khi nhan nut
    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.btnAdd:
                if(edtName.getText().length()!=0 &&
                        edtPhone.getText().length()!=0&&
                        edtAdress.getText().length()!=0)
                    add();
                else
                    Toast.makeText(this,"Mời bạn nhập đủ thông tin!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }























}
