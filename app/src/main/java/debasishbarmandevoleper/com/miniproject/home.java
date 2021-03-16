package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class home extends AppCompatActivity {



    private Button loginBtn, regBtn;
    private EditText username, password;
    TextView register;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    DocumentReference dRefs;
    ProgressDialog progressDialog;
    private int flag;// 0 for admin 1 for user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        loginBtn = findViewById(R.id.login);
        username = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        register = findViewById(R.id.textView54);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        //Login button Click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setEnabled(false);//after clicked on login button
                Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,SignUp.class));
                finish();
            }
        });


    }

    private void Login() {
        String u, p;
        u = username.getText().toString().trim();
        p = password.getText().toString().trim();

        //check if field is empty
        if ((TextUtils.isEmpty(u) || TextUtils.isEmpty(p))) {
            username.setError("Email is empty");
            password.setError("Password is empty");
            return;
        }else{
            try{
                progressDialog.setMessage("Login");
                progressDialog.setCancelable(false);
                progressDialog.show();
                fAuth.signInWithEmailAndPassword(u,p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        DocumentReference dref=fStore.collection("Users").document(authResult.getUser().getUid());
                        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                               // String x=documentSnapshot.getString("isUser");
                                if(documentSnapshot.getString("isUser")!=null){

                                    //startActivity(new Intent(home.this,User_Profile.class));
                                    startActivity(new Intent(home.this,allCategory.class));
                                    finish();

                                }
                                if(documentSnapshot.getString("isAdmin")!=null){
                                    startActivity(new Intent(home.this,AdminProfile.class));
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(home.this, "Data not Found" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                loginBtn.setEnabled(true);
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(home.this, "Error login" +e.getMessage(), Toast.LENGTH_SHORT).show();
                        loginBtn.setEnabled(true);
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, "Error Handled", Toast.LENGTH_SHORT).show();
                loginBtn.setEnabled(true);
            }
        }
    }

   /* private void RegisterAdmin() {
        String u, p;
        u = username.getText().toString().trim();
        p = password.getText().toString().trim();

        //check if checkbox is empty
        if (!(isAdmin.isChecked() || isUser.isChecked())) {
            Toast.makeText(home.this, "Select Account Type", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if field is empty
        if ((TextUtils.isEmpty(u) || TextUtils.isEmpty(p))) {
            username.setError("Email is empty");
            password.setError("Password is empty");
            return;
        } else {
            // Toast.makeText(home.this, ""+u+"Pass"+p, Toast.LENGTH_SHORT).show();
            //Toast.makeText(home.this, "clicked", Toast.LENGTH_SHORT).show();
            try {
                progressDialog.setMessage("Wait Adding Foods");
                progressDialog.show();

                fAuth.createUserWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(home.this, "Done", Toast.LENGTH_SHORT).show();
                                    if (flag == 0) {
                                        dRefs=fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                                        Map<String,Object> admin=new HashMap<>();
                                        admin.put("isAdmin","0");
                                        dRefs.set(admin);

                                        progressDialog.dismiss();
                                        Toast.makeText(home.this, "Admin Created Successfully ", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(home.this, AdminProfile.class));
                                        finish();
                                    }
                                    // 1 for normal users
                                    if (flag == 1) {
                                        dRefs=fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                                        Toast.makeText(home.this, ""+fAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                                        Map<String,Object> user=new HashMap<>();
                                        user.put("isUser","1");
                                        dRefs.set(user);

                                        progressDialog.dismiss();

                                        Toast.makeText(home.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(home.this, User_Profile.class));
                                        finish();
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(home.this, e.getMessage() + "Failed to create Account", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(home.this, "Error found " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(home.this,User_Profile.class));
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        if(fAuth.getCurrentUser()!=null){
            progressDialog.setMessage("Checking..");
            progressDialog.show();
            DocumentReference dRef=fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
            dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if(documentSnapshot.getString("isUser")!=null){
                        progressDialog.dismiss();
                        startActivity(new Intent(home.this,allCategory.class));
                       // startActivity(new Intent(home.this,User_Profile.class));
                        Toast.makeText(home.this, "Welcome "+fAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if(documentSnapshot.getString("isAdmin")!=null){
                        progressDialog.dismiss();
                        startActivity(new Intent(home.this,AdminProfile.class));
                        Toast.makeText(home.this, "Welcome "+fAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(home.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}