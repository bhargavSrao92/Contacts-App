package com.company.contacts.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.company.contacts.Con.MyDataModel;

import java.util.ArrayList;


public class ContactsDatabase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Contact";
    private static final String DB_TABLE1 = "Contact_table";

    private SQLiteDatabase dbase;
    private int rowCount = 0;
    private final Context mcontext;

    //  Contacts db
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONENO = "phonno";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String IMAGE = "image";



    public ContactsDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mcontext = context;

    }

    String sqlQuery = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT  , %s TEXT, %s TEXT , %s TEXT  , %s TEXT  , %s TEXT  )",
            DB_TABLE1, ID, NAME, PHONENO, EMAIL, ADDRESS,IMAGE);



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //contacts
    public void insertContacts(String name, String phone,String  email, String address, byte image[]) {

        dbase = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(NAME, name);
        contentValue.put(PHONENO, phone);
        contentValue.put(EMAIL, email);
        contentValue.put(ADDRESS, address);
        contentValue.put(IMAGE, image);
        dbase.insert(DB_TABLE1, null, contentValue);
        Log.e("insert district", "insert dis succesfully");
        Log.e("insert district",  address);


    }


    public int getRowCount(){
        dbase = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DB_TABLE1 +"";
        Cursor cursor = dbase.rawQuery(selectQuery,null);
        return cursor.getCount();

    }



    //get contacts

    public ArrayList getcontactdetails() {

        ArrayList rData = new ArrayList<>();
        dbase = this.getReadableDatabase();
        MyDataModel mycontacts = new MyDataModel();
        String SQ =  "SELECT *  FROM " + DB_TABLE1 ;

        Log.e("swljdsjhskj", SQ);
        Cursor cursor = dbase.rawQuery(SQ, null);
        String names = null;
        String phone = null;
        byte[] image = null;
        String email = null;
        String address = null;

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                names = cursor.getString(cursor.getColumnIndex(NAME));
                phone = cursor.getString(cursor.getColumnIndex(PHONENO));
                image = cursor.getBlob(cursor.getColumnIndex(IMAGE));
                email = cursor.getString(cursor.getColumnIndex(EMAIL));
                address = cursor.getString(cursor.getColumnIndex(ADDRESS));
                mycontacts.setName(names);
                mycontacts.setPhoneno(phone);
                mycontacts.setImage(image);
                mycontacts.setEmail(email);
                mycontacts.setAddress(address);

            }
        }
        return rData;
    }

    public void UpdateContactDetails( String id,String name, String phoneno, byte image[],String  email, String address) {

        dbase = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(NAME, name);
        contentValue.put(PHONENO, phoneno);
        contentValue.put(EMAIL, email);
        contentValue.put(ADDRESS, address);
        contentValue.put(IMAGE, image);
        dbase.update(DB_TABLE1, contentValue, ID + " = " + id,null);
        Log.e("Contacts updated","Contacts ");


    }




}
