package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
//////////validation left
public class editAdmin extends AppCompatActivity {

    private Button save;
    private ImageView img;
    private EditText name, phone, state,city,pincode;
    FirebaseAuth adminFAuth = FirebaseAuth.getInstance();
    FirebaseFirestore adminFStore = FirebaseFirestore.getInstance();
    DocumentReference reference;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_admin);

        img = findViewById(R.id.imageView2);
        name = findViewById(R.id.restaurant_name);
        phone = findViewById(R.id.phone);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        save = findViewById(R.id.admnSave);
        pincode = findViewById(R.id.pin);
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Saving");
        dialog.setCanceledOnTouchOutside(false);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(editAdmin.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i=new Intent(Intent.ACTION_PICK);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i,"Select an image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(editAdmin.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Saving Data");
                dialog.show();

                ////storing data

                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference storageReference=storage.getReference("Admins").child(adminFAuth.getUid()+new Random().toString());
                storageReference.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //Error might occur
                                        reference=adminFStore.collection("Admin").document(adminFAuth.getCurrentUser().getUid());
                                        Map<String,Object> data=new HashMap<>();
                                        data.put("name",name.getText().toString().trim());
                                        data.put("phone",phone.getText().toString().trim());
                                        data.put("state",state.getText().toString().trim());
                                        data.put("city",city.getText().toString().trim());
                                        data.put("pincode",pincode.getText().toString().trim());
                                        data.put("imageUri",uri.toString());
                                        reference.set(data, SetOptions.merge()).
                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(editAdmin.this, "Saved", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(editAdmin.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        dialog.dismiss();
                                       // Snackbar snackbar=Snackbar.make(view,"Data Saved Successfully",Snackbar.LENGTH_LONG);

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar snackbar=Snackbar.make(view,"Error",Snackbar.LENGTH_LONG);
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                float per=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                dialog.setMessage("Uploading "+(int)per+"%");//working
                            }
                        });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            filepath=data.getData();
            try {

                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(editAdmin.this,AdminProfile.class));
        finish();
    }
}