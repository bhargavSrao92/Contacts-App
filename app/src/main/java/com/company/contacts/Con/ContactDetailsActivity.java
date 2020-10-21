package com.company.contacts.Con;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.contacts.R;
import com.company.contacts.Sqlite.ContactsDatabase;

import java.io.ByteArrayOutputStream;

public class ContactDetailsActivity extends AppCompatActivity {



    String name;
    String phoneno,email,address;
    Byte images;
    byte[] imageProfile;
    byte[] byteimg;


    EditText Nametv,Phonetv,emailtv,addresstv;

    ImageView imageview,editimage;
    private byte imageProfile1;
    Button edit,save;
    ContactsDatabase contactsDatabase;
    String position = "";

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        contactsDatabase = new ContactsDatabase(this);

        Nametv = findViewById(R.id.nametvdetail);
        Phonetv = findViewById(R.id.phonetvdetail);
        emailtv = findViewById(R.id.emailtvdetail);
        addresstv = findViewById(R.id.addresstvdetail);
        imageview = findViewById(R.id.imageview);
        edit = findViewById(R.id.edit);
        editimage = findViewById(R.id.editimage);
        save = findViewById(R.id.save);


        editimage.setEnabled(false);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Nametv.setEnabled(true);
                emailtv.setEnabled(true);
                addresstv.setEnabled(true);
                Phonetv.setEnabled(true);
                editimage.setEnabled(true);
                save.setVisibility(View.VISIBLE);
            }
        });

        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Bundle bundle = getIntent().getExtras();
        try {
            name = bundle.getString("name");
            phoneno = bundle.getString("phoneno");
            email = bundle.getString("email");
            address = bundle.getString("address");
            imageProfile = bundle.getByteArray("image");
            position = bundle.getString("position");


        } catch (NullPointerException e) {

        }

        byte[] img = imageProfile;


        Bitmap bmp= convertByteArrayToBitmap(img);
        imageview.setImageBitmap(bmp);
        Phonetv.setText(phoneno);
        Nametv.setText(name);
        emailtv.setText(email);
        addresstv.setText(address);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                contactsDatabase.UpdateContactDetails(position,Nametv.getText().toString().trim(),Phonetv.getText().toString().trim(),byteimg,emailtv.getText().toString().trim(),addresstv.getText().toString().trim());

                Intent intent = new Intent( ContactDetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private Bitmap convertByteArrayToBitmap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
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
            imageview.setImageBitmap(bmp);

            byteimg = getBitmapAsByteArray(bmp);
            mydata.setImage(byteimg);


        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

}