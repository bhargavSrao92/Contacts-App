package com.company.contacts.Con;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.contacts.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private List<MyDataModel> contactlist;

    Context context;
    ArrayList  arrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  tvName,tvPhone,nocont,imagetext;
        public RelativeLayout llheader;
        ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            tvName =  view.findViewById(R.id.nameTv);
            llheader = view.findViewById(R.id.Relaheader);
            nocont = view.findViewById(R.id.nocont);
            imageView = view.findViewById(R.id.imageIv);
            imagetext = view.findViewById(R.id.imagetext);



        }
    }

    public ContactAdapter(Context context, List<MyDataModel> sqli) {
        this.context = context;
        this.contactlist = sqli;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_itemlist, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        if(contactlist.size() == 0 ){

            myViewHolder.llheader.setVisibility(View.GONE);
        }
        else
        {





            final MyDataModel contact = contactlist.get(i);
            arrayList = new ArrayList();
            myViewHolder.nocont.setText("");

            myViewHolder.tvName.setText(contact.getName());
         //   myViewHolder.tvPhone.setText(contact.getPhoneno());

            byte[] img = contact.getImage();
            if (img!=null){
                Bitmap bmp= convertByteArrayToBitmap(img);
                myViewHolder.imageView.setImageBitmap(bmp);
            }

            String firstelement = contact.getName();
            char first = firstelement.charAt(0);


            myViewHolder.imagetext.setText(first + "");


            myViewHolder.llheader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    byte[] sc = contact.getImage();

                    Intent intent = new Intent(context,ContactDetailsActivity.class).putExtra("name", contact.getName()).putExtra("phoneno",contact.getPhoneno()).putExtra("image",sc).putExtra("address",contact.getAddress()).putExtra("email",contact.getEmail()).putExtra("position",contact.getId());
                    context.startActivity(intent);
                }
            });
        }


    }

    private Bitmap convertByteArrayToBitmap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }


    @Override
    public int getItemCount() {

        if(contactlist.size() == 0){

            return 1;

        }
        else
        {

            return contactlist.size();

        }
    }






}
