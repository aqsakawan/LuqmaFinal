package com.example.logindemo;

//import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search_activity extends AppCompatActivity {

    private EditText mSearchField;
    //private Button mSearchBtn;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> Name_Rest;
    ArrayList<String> Address_Rest;
    ArrayList<String> Picture_Rest;
    SearchAdapter searchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);

        mSearchField =(EditText)findViewById(R.id.search_edit_text);
        //mSearchBtn = (Button)findViewById(R.id.Searchbutton);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        Name_Rest = new ArrayList<>();
        Address_Rest = new ArrayList<>();
        Picture_Rest = new ArrayList<>();

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    setAdapter(editable.toString());
                }
                else{
                    Name_Rest.clear();
                    Address_Rest.clear();
                    Picture_Rest.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String search_string){


        databaseReference.child("rest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Name_Rest.clear();
                Address_Rest.clear();
                Picture_Rest.clear();
                recyclerView.removeAllViews();

                int counter = 0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String uid =  snapshot.getKey();
                    String full_name = snapshot.child("name").getValue(String.class);
                    String Address = snapshot.child("address").getValue(String.class);
                    String pic = snapshot.child("image").getValue(String.class);

                    if(full_name.toLowerCase().contains(search_string)){
                        Name_Rest.add(full_name);
                        Address_Rest.add(Address);
                        Picture_Rest.add(pic);
                        counter++;
                    }

                    if(counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(Search_activity.this, Name_Rest, Address_Rest, Picture_Rest);
                recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
