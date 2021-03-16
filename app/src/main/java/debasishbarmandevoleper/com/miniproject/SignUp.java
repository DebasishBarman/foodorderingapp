package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText username, password;
    private Button register;
    private TextView logintxt;
    private CheckBox isAdmin, isUser;
    private int flag;// 0 for admin 1 for user
    ProgressDialog progressDialog;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    DocumentReference dRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        isAdmin = findViewById(R.id.checkBox3);
        isUser = findViewById(R.id.checkBox2);
        register=findViewById(R.id.button17);
        username = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        logintxt=findViewById(R.id.logs);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,home.class));
                finish();
            }
        });

        isAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isUser.setChecked(false);
                    flag = 0;
                    return;
                }
            }
        });
        isUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isAdmin.setChecked(false);
                    flag = 1;
                    return;
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterAdmin();
            }
        });

    }
    private void RegisterAdmin() {
        String u, p;
        u = username.getText().toString().trim();
        p = password.getText().toString().trim();

        //check if checkbox is empty
        if (!(isAdmin.isChecked() || isUser.isChecked())) {
            Toast.makeText(SignUp.this, "Select Account Type", Toast.LENGTH_SHORT).show();
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
                progressDialog.setMessage("Creating Account");
                progressDialog.setCancelable(false);
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
                                        Toast.makeText(SignUp.this, "Admin Created Successfully ", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this, AdminProfile.class));
                                        finish();
                                    }
                                    // 1 for normal users
                                    if (flag == 1) {
                                        dRefs=fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                                        Toast.makeText(SignUp.this, ""+fAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                                        Map<String,Object> user=new HashMap<>();
                                        user.put("isUser","1");
                                        dRefs.set(user);

                                        progressDialog.dismiss();

                                        Toast.makeText(SignUp.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this, User_Profile.class));
                                        finish();
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, e.getMessage() + "Failed to create Account", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(SignUp.this, "Error found " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(home.this,User_Profile.class));
        }
    }
}