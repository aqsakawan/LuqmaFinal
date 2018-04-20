package com.example.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    //private Button RegisterButton;
    private TextView userReg;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etUPassword);
        Info= (TextView)findViewById(R.id.textView3);
        Login = (Button)findViewById(R.id.btnLogin);
       // RegisterButton = (Button)findViewById(R.id.RegisterButton);
        userReg = (TextView)findViewById(R.id.RegisterText);


        Info.setText("No of attempts left: 5");
       firebaseAuth = FirebaseAuth.getInstance();
       progressDialog = new ProgressDialog(this);
       FirebaseUser user = firebaseAuth.getCurrentUser();

//       if(user != null){
//           finish();
//           startActivity(new Intent(MainActivity.this, SecondActivity.class));
//       }


        //validates if username and password
        Login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activity_reg.class));
            }
        });
    }

    private void validate(String userName, String userPassword){
//
        progressDialog.setMessage("Loading.....Please wait!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Search_activity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Login falied", Toast.LENGTH_SHORT).show();
                    counter--;
                    //Info.setText("No. of attempts: " + counter);
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                    progressDialog.dismiss();

                }
            }
        });
    }
}
