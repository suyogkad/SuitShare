package com.example.suitshare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Additem extends AppCompatActivity {

    EditText editItemName, editItemDescription, editItemPrice, editLocationTag;
    Switch switchPurchaseStatus;
    Button btnAddItem;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem); // Make sure this matches your XML filename

        editItemName = findViewById(R.id.editItemName);
        editItemDescription = findViewById(R.id.editItemDescription);
        editItemPrice = findViewById(R.id.editItemPrice);
        switchPurchaseStatus = findViewById(R.id.switchPurchaseStatus);
        editLocationTag = findViewById(R.id.editLocationTag);
        btnAddItem = findViewById(R.id.btnAddItem);

        databaseHelper = new DatabaseHelper(this);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = editItemName.getText().toString().trim();
                String itemDescription = editItemDescription.getText().toString().trim();
                double price = Double.parseDouble(editItemPrice.getText().toString().trim()); // Make sure to handle NumberFormatException
                boolean purchaseStatus = switchPurchaseStatus.isChecked();
                String locationTag = editLocationTag.getText().toString().trim();

                // Assuming you have the user's ID somehow (e.g., after login). Placeholder here:
                int userId = 1;  // Replace this with the actual user's ID

                boolean isInserted = databaseHelper.insertItem(userId, itemName, itemDescription, purchaseStatus, price, locationTag);
                if (isInserted) {
                    Toast.makeText(Additem.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    finish();  // Close activity if you wish
                } else {
                    Toast.makeText(Additem.this, "Error adding item. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
