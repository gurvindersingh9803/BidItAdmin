package com.example.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class registration extends AppCompatActivity {

    private EditText inputEmail, inputPassword, nameEditText, address, phoneEditText, passwordEditText,credit_card;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String SELLERS = "sellers";
    AlertDialog.Builder alertBuilder;

    EditText mEditText;
    boolean mInside = false;
    boolean mWannaDeleteHyphen = false;
    boolean mKeyListenerSet = false;
    final static String MARKER = "|";

    private String TAG = "RegisterActivity";
    private String username, fname, email, adress, phone, workplace,card;
    private String password;
    private User user;
    String UserType;

    private FirebaseAuth mAuth;
    Context mctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = database.getReference(SELLERS);
        //Button buy = v.findViewById(R.id.btnBuy);

        nameEditText = findViewById(R.id.name);
        address = findViewById(R.id.address);



        final CardForm cardForm = findViewById(R.id.card_form);



        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(registration.this);


        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);




        btnSignIn = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        //credit_card = (EditText) v.findViewById(R.id.credit_card);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);



        //CardForm cardForm = v.findViewById(R.id.card_form);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registration.this,UserLogin.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                fname = nameEditText.getText().toString();
                adress = address.getText().toString();
                String cardNumber = cardForm.getCardNumber();
                String cvv = cardForm.getCvv();
                String expiryMonth = cardForm.getExpirationMonth();
                String expiryYear = cardForm.getExpirationYear();
                String postal = cardForm.getPostalCode();

                user = new User(fname, adress, phone, email, password,cardNumber,cvv,expiryMonth,expiryYear,postal);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(registration.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(registration.this, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(registration.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(registration.this);
                    alertBuilder.setTitle("Details entered");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            // Toast.makeText(getActivity(), "Thank you for purchase", Toast.LENGTH_LONG).show();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(registration.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }



                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(registration.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                String keyid = mDatabase.push().getKey();
                                mDatabase.child(keyid).setValue(user);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(registration.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(registration.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });


            }
        });
    }
}