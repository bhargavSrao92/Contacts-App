package com.company.contacts.Con;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.company.contacts.ContactsActivity;
import com.company.contacts.R;
import com.company.contacts.Sqlite.ContactsDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    TextView ContactsTV,nocont;
    RecyclerView recyclerView;
    private ArrayList<MyDataModel> ContactsArraylist;
    private ContactAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    ContactsDatabase vc = new ContactsDatabase(this);
    FloatingActionButton addcontact;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONENO = "phonno";
    public static final String IMAGE = "image";
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";

    private static final String DB_TABLE1 = "Contact_table";


    private static final String DB_NAME = "Contact";
    SQLiteDatabase sqLiteDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        addcontact = findViewById(R.id.addcontact);

        sqLiteDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
         ContactsArraylist = new ArrayList<>();


        gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);



        Log.e("vc", String.valueOf(vc.getcontactdetails()));
        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });
        adapter = new ContactAdapter(this,ContactsArraylist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

fetch();

    }

    public void fetch() {
        String SQ =   "SELECT * FROM " + DB_TABLE1 ;
        Cursor cursor = sqLiteDatabase.rawQuery(SQ, null);
        String id = null;
        String name = null;
        String phone = null;
        byte[] image = null;
        String email = null;
        String address = null;

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                id = cursor.getString(cursor.getColumnIndex(ID));
                name = cursor.getString(cursor.getColumnIndex(NAME));
                phone = cursor.getString(cursor.getColumnIndex(PHONENO));
                image = cursor.getBlob(cursor.getColumnIndex(IMAGE));
                email = cursor.getString(cursor.getColumnIndex(EMAIL));
                address = cursor.getString(cursor.getColumnIndex(ADDRESS));
                MyDataModel myDataModel = new MyDataModel();
                myDataModel.setName(name);
                myDataModel.setImage(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
                myDataModel.setPhoneno(phone);
                myDataModel.setEmail(email);
                myDataModel.setAddress(address);
                myDataModel.setId(id);
                ContactsArraylist.add(myDataModel);
            }
        }
    }
    }





