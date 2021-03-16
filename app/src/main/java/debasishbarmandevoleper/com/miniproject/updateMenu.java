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
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Adapter.menuAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class updateMenu extends AppCompatActivity {

    EditText name,price,desc;
    private menuAdapter adapter;
    private CircleImageView imageView;
    private String names,prices,descs,url,id;
    Button btnUpdate,btnDelete;

    private Spinner category;
    ProgressDialog progressDialog;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore db;
    FirebaseAuth auth;
    StorageReference storageReference;
    DocumentReference docRef,docRefdata;
    Uri uri;
    Bitmap bitmap;
    boolean clicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_menu);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        name=findViewById(R.id.editTextTextPersonName3);
        price=findViewById(R.id.editTextNumber2);
        desc=findViewById(R.id.editTextTextMultiLine3);
        imageView=findViewById(R.id.imageView6);
        btnUpdate=findViewById(R.id.button10);
        btnDelete=findViewById(R.id.button12);
        category=findViewById(R.id.spinner3);



        //getting data from adapterMenu
        Bundle bundle=getIntent().getExtras();
        names=bundle.getString("name");
        prices=bundle.getString("price");
        descs=bundle.getString("desc");
        url=bundle.getString("imgurl");
        id=bundle.getString("id");
        Toast.makeText(this, ""+id.toString(), Toast.LENGTH_SHORT).show();



        name.setText(names.toString());
        price.setText(prices.toString());
        desc.setText(descs.toString());
        Glide.with(this).load(url).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked=true;
                Toast.makeText(updateMenu.this, "Kuch bhi", Toast.LENGTH_SHORT).show();
                Dexter.withContext(updateMenu.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i=new Intent(Intent.ACTION_PICK);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i,"Select an image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();

                            }
                        }).check();
            }
        });


        //update;
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(price.getText()) || TextUtils.isEmpty(desc.getText())||imageView==null){
                    name.setError("Empty");
                    price.setError("Empty");
                    desc.setError("Empty");
                    return;
                }
                else{

                    if(clicked==true){

                        storageReference=firebaseStorage.getReference("AdminFoodPic").child(auth.getUid()+new Random().toString());
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //CollectionReference collectionReference=db.collection("AdminFoods");
                                        // Query query=collectionReference.whereEqualTo("admin_id",auth.getCurrentUser().getUid());
                                        docRef=db.collection("AdminFoods").document(id);
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("title",name.getText().toString().trim());
                                        map.put("price",price.getText().toString().trim());
                                        map.put("category",category.getSelectedItem().toString());
                                        map.put("description",desc.getText().toString().trim());
                                        map.put("imgUrl",uri.toString());
                                        docRef.update(map).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(updateMenu.this, "Error Occured saving", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(updateMenu.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    else{

                        docRef=db.collection("AdminFoods").document(id);
                        Map<String,Object> map=new HashMap<>();
                        map.put("title",name.getText().toString().trim());
                        map.put("price",price.getText().toString().trim());
                        map.put("category",category.getSelectedItem().toString());
                        map.put("description",desc.getText().toString().trim());
                       // map.put("imgUrl",uri.toString());
                        docRef.update(map).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(updateMenu.this, "Error Occured saving", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(updateMenu.this, "Update Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&& resultCode==RESULT_OK){
            uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
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