package com.company.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.contacts.Sqlite.ContactsDatabase;
import com.company.contacts.Con.MainActivity;
import com.company.contacts.Con.MyDataModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    ContactsDatabase contactsDatabase;

    EditText editTextName,editTextNumber,EmailEt,AddressEt;
    TextView buttonAdd;
    ImageView profile,edit;
    private ArrayList<MyDataModel> ContactsArraylist;
    byte[] photo;

    Boolean imagepicker = false;
    byte[] byteimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsDatabase = new ContactsDatabase(this);
        profile  = findViewById(R.id.profileimage);
        editTextName  = findViewById(R.id.nameEt);
        editTextNumber  = findViewById(R.id.phoneET);
        buttonAdd  = findViewById(R.id.add_contact_save);
        edit  = findViewById(R.id.edit);
        EmailEt  = findViewById(R.id.EmailEt);
        AddressEt  = findViewById(R.id.AddressEt);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });
       // init();
    }
public  void init(){
    if(editTextName.getText().toString().length() == 0){
        editTextName.setError("Please enter Name");
        editTextName.requestFocus();
    }
    if(editTextNumber.getText().toString().length() <10){
        editTextNumber.setError("Please enter valid phoneNumber");
        editTextNumber.requestFocus();
    }
    if(EmailEt.getText().toString().length() ==0){
        EmailEt.setError("Please enter Email");
        EmailEt.requestFocus();
    }

    if(AddressEt.getText().toString().length() ==0){
        AddressEt.setError("Please enter Address");
        AddressEt.requestFocus();
    }

    if (imagepicker == false) {
        Toast.makeText(this, "Capture image", Toast.LENGTH_LONG).show();


    }
    else
    {

        if(!editTextName.getText().toString().trim().equals("")&&
                !editTextNumber.getText().toString().trim().equals("")&&
                !EmailEt.getText().toString().trim().equals("") &&
                !AddressEt.getText().toString().trim().equals("")){
            contactsDatabase.insertContacts(editTextName.getText().toString().trim(),editTextNumber.getText().toString().trim(),EmailEt.getText().toString().trim(),AddressEt.getText().toString().trim(),byteimg);
            Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }





}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // photo = (byte[]) data.getExtras().get("data");
            // profile.setImageBitmap(photo);

            MyDataModel mydata = new  MyDataModel();
            //  mydata.setImage((photo));

            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(bmp);

            byteimg = getBitmapAsByteArray(bmp);
            mydata.setImage(byteimg);
            imagepicker = true ;


        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
