package com.hoangtrongminhduc.html5.dev.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HTML5 on 22/10/2017.
 */

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contact_list";
    private static final String TABLE_NAME = "contact";
    private static final String ID = "id";
    private static final String NAME  = "name";
    private static final String NUMBER = "number";
    private static final String ADDRESS = "address";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String GENDER = "gender";
    private Context context;

    public MyDatabase(Context context){
        super(context,DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME+" (" +
                ID+" integer primary key, "+
                NAME+" TEXT, "+
                NUMBER+" TEXT," +
                ADDRESS+" TEXT,"+
                DATE+" TEXT,"+
                TIME+" TEXT,"+
                GENDER +" TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }
    //ham them contact vao sqlite
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(NUMBER, contact.getNumber());
        values.put(ADDRESS, contact.getAddress());
        values.put(DATE, contact.getDate());
        values.put(TIME, contact.getTime());
        values.put(GENDER, contact.getGender());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    //lay ra tat ca cac contact trong sql ra
    public List<Contact> getAllContact() {
        List<Contact> listContact = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));
                contact.setAddress(cursor.getString(3));
                contact.setDate(cursor.getString(4));
                contact.setTime(cursor.getString(5));
                contact.setGender(cursor.getString(6));
                listContact.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listContact;
    }
    //xoa tat ca doi tuong
   public void deleteAllStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    //xoa 1 doi tuong theo id
    public void deleteStudent(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
    //cap nhat vao doi tuong
    public int Update(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(NUMBER, contact.getNumber());
        values.put(ADDRESS, contact.getAddress());
        values.put(DATE, contact.getDate());
        values.put(TIME, contact.getTime());
        values.put(GENDER, contact.getGender());
        return db.update(TABLE_NAME,values,ID +"=?",new String[] { String.valueOf(contact.getId())});
    }
}
