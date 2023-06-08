package com.greenhuecity.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.greenhuecity.R;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadCarsActivity extends AppCompatActivity {

    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView showSelectedImage;

    EditText edtCarName, edtPrice, edtPlates, edtTopSpeed, edtHorsePower, edtMileage, edtDescription;
    TextView tvStatus, tvPower, tvBrand, tvDistributors, tvFromTime, tvEndTime;

    Bitmap FixBitmap;

    ProgressDialog progressDialog;

    ByteArrayOutputStream byteArrayOutputStream;

    byte[] byteArray;

    String ConvertImage;

    String nameCar, licensePlates, description, status, fromTime, endTime;
    int power_id, brand_id, distributor_id;
    double price, topSpeed, horsePower, mileage;

    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cars);
        initGUI();
//

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameCar = edtCarName.getText().toString();
                licensePlates = edtPlates.getText().toString();
                description = edtDescription.getText().toString();
//                status = tvStatus.getText().toString();
//                fromTime = tvFromTime.getText().toString();
//                endTime = tvEndTime.getText().toString();
                status ="Pending";
                fromTime = "2023-06-10 12:10:06";
                endTime = "2023-06-20 12:10:06";
                price = Double.parseDouble(edtPrice.getText().toString());
                topSpeed = Double.parseDouble(edtTopSpeed.getText().toString());
                horsePower = Double.parseDouble(edtHorsePower.getText().toString());
                mileage = Double.parseDouble(edtMileage.getText().toString());
                power_id = 2;
                brand_id = 2;
                distributor_id = 2;
                UploadImageToServer();

            }
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
    }

    private void initGUI() {
        GetImageFromGalleryButton = findViewById(R.id.btn_imgcar);
        UploadImageOnServerButton = findViewById(R.id.button_upload_cars);
        showSelectedImage = findViewById(R.id.img_addcar);
        edtCarName = findViewById(R.id.edit_namecar);
        edtPrice = findViewById(R.id.edit_price);
        edtPlates = findViewById(R.id.edit_plates);
        edtTopSpeed = findViewById(R.id.edit_topSpeed);
        edtHorsePower = findViewById(R.id.edit_horse);
        edtMileage = findViewById(R.id.edit_mileage);
        edtDescription = findViewById(R.id.edit_discription);
        tvBrand = findViewById(R.id.edit_brandcar);
        tvStatus = findViewById(R.id.edit_status);
        tvPower = findViewById(R.id.edit_power);
        tvDistributors = findViewById(R.id.edit_Distributor);
        tvFromTime = findViewById(R.id.edit_fromTime);
        tvEndTime = findViewById(R.id.edit_endTime);

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    showSelectedImage.setImageBitmap(FixBitmap);
                    UploadImageOnServerButton.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            showSelectedImage.setImageBitmap(FixBitmap);
            UploadImageOnServerButton.setVisibility(View.VISIBLE);

        }
    }


    public void UploadImageToServer() {

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        ApiService service = RetrofitClient.getClient().create(ApiService.class);

        Call<Cars> call = service.uploadCars(nameCar, price, description, licensePlates, status, fromTime, endTime,
                "pending", power_id, brand_id, 38, distributor_id, topSpeed, horsePower, mileage, ConvertImage, "adf");

        progressDialog = ProgressDialog.show(this, "Upload Image", "Please wait...", false, false);

        call.enqueue(new Callback<Cars>() {
            @Override
            public void onResponse(Call<Cars> call, Response<Cars> response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Upload successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Cars> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
