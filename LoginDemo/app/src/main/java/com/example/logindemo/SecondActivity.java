package com.example.logindemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    private TextView Info;
    private Button SignOut;
    private Button SearchButton;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        restaurant rest1 = new restaurant(1000, "Jade", "Lakshmi Chowk", 5340340);
        db.collection("Restaurants Names").document("Jade Cafe").set(rest1);
        restaurant rest2 = new restaurant(1001, "China Town", "Hussain Chowk", 5340341);
        db.collection("Restaurants Names").document("China Town").set(rest2);
        restaurant rest3 = new restaurant(1010, "Zouk", "Opposite Khaadi", 5340342);
        db.collection("Restaurants Names").document("Cafe Zouk").set(rest3);

        final DocumentReference rest_name = db.document("Restaurants Names/Jade Cafe");


//        Map<String, Object> rest = new HashMap<>();
//        rest.put("Name", "Jade Cafe");
//        rest.put("Address", "42 Mm Alam");
//        rest.put("Contact", 1234);
// db.collection("Restaurants").document("JadeCafe").set(rest);

        Info = findViewById(R.id.welcomeUser);
        SignOut = findViewById(R.id.signOut);
        SearchButton = findViewById(R.id.Searchbutton);

        Info.setText("Welcome! ");
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Toast.makeText(SecondActivity.this, "Sign Out Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rest_name.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            restaurant res = documentSnapshot.toObject(restaurant.class);
                            String name = res.getName_rest();
                            
                            Toast.makeText(SecondActivity.this, "Resturant Found", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SecondActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(SecondActivity.this, "Resturants Does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SecondActivity.this, "Error!", Toast.LENGTH_SHORT).show();

                            }
                        });


            }
        });
    }




}
