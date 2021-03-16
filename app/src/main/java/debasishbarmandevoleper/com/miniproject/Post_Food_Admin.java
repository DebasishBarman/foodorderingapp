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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Post_Food_Admin extends AppCompatActivity {

    private Button foodbtn,browse;
    private EditText title,price,description;
    private ImageView img;
    private Spinner category;
    ProgressDialog progressDialog;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fAdminStore;
    FirebaseAuth fAdminAuth;
    StorageReference storageReference;
    DocumentReference docRef,docRefdata;
     Uri uri;
     Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_food);

        category=findViewById(R.id.spinner);
        description=findViewById(R.id.editTextTextMultiLine);
        foodbtn=findViewById(R.id.button);
        img=findViewById(R.id.imageView4);
       // browse=findViewById(R.id.browseImg);
        price=findViewById(R.id.editTextNumber);
        title=findViewById(R.id.editTextTextPersonName2);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Post_Food_Admin.this, "Browse Clicked", Toast.LENGTH_SHORT).show();
                Dexter.withContext(Post_Food_Admin.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i=new Intent(Intent.ACTION_PICK);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i,"Select an image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(Post_Food_Admin.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(description.getText().toString().trim()) &&
                        TextUtils.isEmpty(price.getText().toString().trim()) &&
                        TextUtils.isEmpty(title.getText().toString().trim())) {
                    description.setError("Empty field");
                    title.setError("Empty field");
                    price.setError("Empty field");
                }
                else {
                    ///suggestion to use async function call for better image handling
                    postFood();
                }
            }
        });



    }

    private void postFood() {
        progressDialog.setMessage("Wait Adding Foods");
        progressDialog.show();
        if(img==null){
            Toast.makeText(this, "No Image", Toast.LENGTH_SHORT).show();
            return;
        }
       // String folder="AdminProfile";//image folder
        fAdminStore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance(); //storage
        fAdminAuth=FirebaseAuth.getInstance();
        StorageReference adminProfile=firebaseStorage.getReference("AdminFoodPic").child(fAdminAuth.getUid()+new Random().toString());
        adminProfile.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                adminProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        docRef=fAdminStore.collection("AdminFoods").document();
                        Map<String,Object> img=new HashMap<>();
                        img.put("imgUrl",uri.toString());
                        img.put("admin_id",fAdminAuth.getCurrentUser().getUid());
                        img.put("title",title.getText().toString().trim());
                        img.put("category",category.getSelectedItem().toString());
                        img.put("description",description.getText().toString().trim());
                        img.put("price",price.getText().toString().trim());
                        docRef.set(img, SetOptions.merge());

                        progressDialog.dismiss();

                        Toast.makeText(Post_Food_Admin.this, "Upload successful+Data Saved", Toast.LENGTH_LONG).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Post_Food_Admin.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
               // float x=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK){
            uri=data.getData();
            try {

                InputStream inputStream=getContentResolver().openInputStream(uri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Post_Food_Admin.this,AdminProfile.class));
        finish();
    }
}