package com.hoangtrongminhduc.html5.dev.sqliteexample;

import android.content.Intent;
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

import static com.hoangtrongminhduc.html5.dev.sqliteexample.R.id.btnAdd;
import static com.hoangtrongminhduc.html5.dev.sqliteexample.R.id.btnCancel;
import static com.hoangtrongminhduc.html5.dev.sqliteexample.R.id.radioFemale;
import static com.hoangtrongminhduc.html5.dev.sqliteexample.R.id.radioMale;

/**
 * Created by HTML5 on 22/10/2017.
 */

public class EditContact extends AppCompatActivity implements View.OnClickListener{
    private Button btnAddedit, btnCanceledit;
    private EditText edtName, edtPhone, edtAdress;
    private RadioGroup radioGroup;
    private RadioButton radioGender;
    private Contact contact;
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
        btnCanceledit = (Button)findViewById(btnCancel);
        btnAddedit = (Button)findViewById(btnAdd);
        btnAddedit.setText("Fix");
        //khai bao su kien nhan vao nut
        btnAddedit.setOnClickListener(this);
        btnCanceledit.setOnClickListener(this);
        //nhan doi tuong tu detail va set text cho layout
        Intent intent=getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        contact =(Contact) bundle.getSerializable("SEND_EDIT");
        setTextLayout(contact);
    }
    //ham set text cho layout
    private void setTextLayout(Contact contact){
        edtName.setText(contact.getName());
        edtAdress.setText(contact.getAddress());
        edtPhone.setText(contact.getNumber());
        radioGroup.check(radioMale);
        if(contact.getGender().compareTo("Nữ")==0) radioGroup.check(radioFemale);
    }
    //xu li khi chon vao nut
    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id){

            case R.id.btnAdd:
                if(edtName.getText().length()!=0 &&
                        edtPhone.getText().length()!=0&&
                        edtAdress.getText().length()!=0){
                    getContact();
                    updateContact();
                    finish();
                }else
                    Toast.makeText(this, "Mời Ban nhập đủ thông tin!", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnCancel:
                finish();
                break;
        }
    }
    //ham cap nhat contact vao sqlite va gui ket qua ve detail
    private void updateContact(){
        MyDatabase db = new MyDatabase(this);
        db.Update(contact);
        db.close();
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("RETURN_EDIT",contact);
        i.putExtras(b);
        setResult(RESULT_OK,i);
    }
    //lay thong tin sau khi chinh sua xong
    private void getContact(){
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
    }
}
