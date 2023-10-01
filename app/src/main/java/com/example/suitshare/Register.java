package com.example.suitshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText fullname, username, email, phoneNumber, dob, password, confirmPassword;
    Button registerSubmitButton, avatarButton;
    ImageView avatarImageView;
    DatabaseHelper db;
    String selectedAvatarUri = "default";
    private static final int GALLERY_REQUEST_CODE = 123;
    private static final int CAMERA_REQUEST_CODE = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        fullname = findViewById(R.id.fullnameEditText);
        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        phoneNumber = findViewById(R.id.phoneNumberEditText);
        dob = findViewById(R.id.dobEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
        registerSubmitButton = findViewById(R.id.registerSubmitButton);
        avatarButton = findViewById(R.id.avatarButton);
        avatarImageView = findViewById(R.id.avatarImageView);

        // Set the hint for DOB format
        dob.setHint("Date of Birth (YYYY-MM-DD)");

        // Default avatar image
        avatarImageView.setImageResource(R.drawable.avatar);

        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerOptions();
            }
        });

        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameValue = fullname.getText().toString().trim();
                String user = username.getText().toString().trim();
                String em = email.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                String dobValue = dob.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String confirmPwd = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(fullNameValue) || TextUtils.isEmpty(user) || TextUtils.isEmpty(em)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(dobValue)
                        || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pwd.equals(confirmPwd)) {
                    boolean isInserted = db.insertData(fullNameValue, user, em, phone, dobValue, pwd, confirmPwd, selectedAvatarUri);
                    if (isInserted) {
                        Toast.makeText(Register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    } else {
                        Toast.makeText(Register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showImagePickerOptions() {
        CharSequence[] options = {"Choose from Gallery", "Capture with Camera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    chooseImageFromGallery();
                } else if (which == 1) {
                    captureImageWithCamera();
                }
            }
        });

        builder.show();
    }

    private void chooseImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void captureImageWithCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                avatarImageView.setImageURI(selectedImage);
                selectedAvatarUri = selectedImage.toString();
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                avatarImageView.setImageBitmap(photo);
                // Convert bitmap to URI if needed
            }
        }
    }
}
