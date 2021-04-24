package com.example.seller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class AuctonsAdapter extends RecyclerView.Adapter<AuctonsAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    CountDownTimer timer;
    Calendar calendar;

    public AuctonsAdapter(Context context, List<Upload> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_auction_view, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final Upload uploadCurrent = mUploads.get(position);


        String inputDateString =  uploadCurrent.getDateTime();
        Calendar calCurr = Calendar.getInstance();
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(inputDateString));
            if(day.before(calCurr)){
                //holder.textViewName.setText("Expired!");

                holder.itemView.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.height = 0;
                params.width = 0;
                holder.itemView.setLayoutParams(params);

                holder.itemView.setVisibility(View.VISIBLE);

                //DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                //ref.child("uploads").child(uploadCurrent.getKey()).removeValue();


            }else{

                holder.textViewName.setText(uploadCurrent.getName());
                holder.price.setText("$CAD "+uploadCurrent.getpPrice());
                holder.desc.setText(uploadCurrent.getpDesc());


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }




        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);



        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        String formattedDate = df.format(c);









        holder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,String.valueOf(uploadCurrent.getKey()),Toast.LENGTH_SHORT).show();

                Context context = view.getContext();



                Intent intent = new Intent(context,ProductDetails.class);
                intent.putExtra("image_url",uploadCurrent.getImageUrl());
                intent.putExtra("desc",uploadCurrent.getpDesc());
                intent.putExtra("price",uploadCurrent.getpPrice());
                intent.putExtra("name",uploadCurrent.getName());




                context.startActivity(intent);


            }
        });

        holder.relative_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (final DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                            final TextView input = new TextView (mContext);
                            final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setTitle("Do you want to delete this item?");

                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Do something with value!

                                    Log.e("Position is ", String.valueOf(childSnapshot.getKey()));
                                    databaseReference.getRef().removeValue();

                                }
                            });

                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Do something with value!

                                }
                            });

                            alert.show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,desc,price;
        public ImageView imageView;
        RelativeLayout relative_layout;


        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            desc = itemView.findViewById(R.id.textViewShortDesc);
            price = itemView.findViewById(R.id.textViewPrice);




            imageView = itemView.findViewById(R.id.image_view_upload);
            relative_layout = itemView.findViewById(R.id.relative_layout);

        }

    }


}
