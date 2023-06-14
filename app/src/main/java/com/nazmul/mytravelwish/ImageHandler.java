package com.nazmul.mytravelwish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class ImageHandler extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launchGalleryForResult;
    private ActivityResultLauncher<Intent> launchCameraForResult;
    Button takePhoto, selectPhoto, saveImageButton;
    String wishId;
    ImageView imageView;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_handler);
        firebaseService = new FirebaseService();

        takePhoto = (Button)findViewById(R.id.takePhoto);
        selectPhoto = (Button)findViewById(R.id.selectPhoto);
        imageView = (ImageView)findViewById(R.id.uploadImageView);
        saveImageButton = (Button)findViewById(R.id.saveImageButton);

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryBtnPressed(view);
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraBtnPressed(view);
            }
        });

        createGalleryLauncher();
        createCameraLauncher();

        Intent intent = getIntent();
        wishId = intent.getStringExtra("wishId");
    }

    private void createGalleryLauncher() {
        launchGalleryForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        imageView.setImageURI(intent.getData());
                        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        saveImageButton.setOnClickListener(view -> {
                            firebaseService.saveImage(getBytes(bm), wishId);
                            goToHomePage();
                        });
                    }
                }
        );
    }

    private void createCameraLauncher() {
        launchCameraForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        // capture image data...
                        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                        saveImageButton.setOnClickListener(view -> {
                            firebaseService.saveImage(getBytes(bitmap), wishId);
                            goToHomePage();
                        });
                    }
                }
        );
    }

    public void galleryBtnPressed(View view){
        Log.i("imageupload", "clicked gallery");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launchGalleryForResult.launch(intent);
    }

    public void cameraBtnPressed(View view){
        Log.i("imageupload", "clicked camera");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launchCameraForResult.launch(intent);
    }

    private byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bs);
        return bs.toByteArray();
    }

    /**
     * after successful submission send back to home page
     */
    private void goToHomePage(){
        Intent intent=new Intent(this, MyWish.class); // set intent
        startActivity(intent); // start the page (ImageHandler)
        finish(); // finishing the current activity
    }

}
