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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class edit_user extends AppCompatActivity {

    private Button save,uploadBtn,browseBtn;
    private CircleImageView imageView;
    private EditText name, phone, address,pincode;



    FirebaseAuth userFAuth = FirebaseAuth.getInstance();
    FirebaseFirestore userFStore = FirebaseFirestore.getInstance();
    DocumentReference reference;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);


        pincode=findViewById(R.id.phone2);
        imageView = findViewById(R.id.imageView2);
        name = findViewById(R.id.restaurant_name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.userAddress);
        save = findViewById(R.id.admnSave);
        ProgressDialog dialog = new ProgressDialog(this);

        //browsing images
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(edit_user.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i = new Intent(Intent.ACTION_PICK);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i, "Select an image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(edit_user.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //saving data on save btn press
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(pincode.getText().toString().trim())&&TextUtils.isEmpty(name.getText().toString().trim()) && TextUtils.isEmpty(phone.getText().toString().trim()) && TextUtils.isEmpty(address.getText().toString().trim())) {
                    if(phone.getText().length()<10 && pincode.getText().length()<6){
                        phone.setError("Invalid Phone Number");
                        return;
                    }
                    pincode.setError("pincode must be 6 digit");
                    name.setError("Empty field");
                    phone.setError("Empty field");
                    address.setError("Empty field");
                    return;
                }
                else {
                    dialog.setMessage("Saving Data");
                    dialog.show();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReference("UsersProfile" + new Random().nextInt(50));
                    storageReference.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //Error might occur
                                            reference = userFStore.collection("Users").document(userFAuth.getCurrentUser().getUid());
                                            Map<String, Object> img = new HashMap<>();
                                            img.put("imageUri", uri.toString());
                                            img.put("pincode",pincode.getText().toString());//
                                            img.put("userName", name.getText().toString().trim());
                                            img.put("userPhone", phone.getText().toString().trim());
                                            img.put("userAddress", address.getText().toString().trim());
                                            reference.set(img, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(edit_user.this, "Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                        }
                                    });

                                    dialog.dismiss();
                                    Toast.makeText(edit_user.this, "Upload Successfull", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    //float per = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                    dialog.setMessage("Saving");
                                }
                            });
                }
            }

        });
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(edit_user.this, User_Profile.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK){
            filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}