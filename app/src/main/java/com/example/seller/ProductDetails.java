package com.example.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seller.R;
import com.example.seller.Upload;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    ImageView imageView;
    ActionBar toolbar;
    private FirebaseAuth mAuth;
    TextView desc,name,price;
    Button b;
    DataSnapshot dataSnapshot;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private RecyclerView mRecyclerView;
    private AuctonsAdapter mAdapter;
    private ProgressBar mProgressCircle;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        desc = findViewById(R.id.description);
        name = findViewById(R.id.product_name);
        price = findViewById(R.id.prices);
        imageView = findViewById(R.id.image_view);
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");



        Bundle extras = getIntent().getExtras();

        if (extras.getInt("category") >= 0) {
            String url = extras.getString("image_url");
            String prices = extras.getString("price");
            String descs = extras.getString("desc");
            String names = extras.getString("name");


            if (url != null) {

                Picasso.get().load(url).placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(imageView);

                name.setText(names);
                desc.setText(descs);
                price.setText("$CAD "+prices);

                mRecyclerView = findViewById(R.id.recy1);
                auth = FirebaseAuth.getInstance();
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(ProductDetails.this));
                mProgressCircle = findViewById(R.id.progress_circle);
                mUploads = new ArrayList<>();
                mAdapter = new AuctonsAdapter(ProductDetails.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mStorage = FirebaseStorage.getInstance();
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                Query queryRef = mDatabaseRef.orderByChild("sellerId").equalTo(currentUser.getEmail());

                mDBListener = queryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUploads.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            upload.setKey(postSnapshot.getKey());

                            mUploads.add(upload);
                        }
                        mAdapter.notifyDataSetChanged();
                        mProgressCircle.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProductDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });



            }
        }


       // navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener);

    }


    }



