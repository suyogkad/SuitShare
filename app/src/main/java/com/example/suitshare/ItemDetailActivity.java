package com.example.suitshare;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    TextView tvItemName, tvDateAdded, tvLocation, tvItemDescription;
    Button btnEdit, btnDelete, btnMark;
    DatabaseHelper databaseHelper;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        tvItemName = findViewById(R.id.tvItemName);
        tvDateAdded = findViewById(R.id.tvDateAdded);
        tvLocation = findViewById(R.id.tvLocation);
        tvItemDescription = findViewById(R.id.tvItemDescription);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnMark = findViewById(R.id.btnMark);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve passed data from Intent
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);

        if (itemId != -1) {
            Cursor cursor = databaseHelper.getAllItemsForUser(itemId);
            if (cursor.moveToFirst()) {
                tvItemName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_NAME)));
                tvDateAdded.setText("Date Added: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_ADDED)));
                tvLocation.setText("Location: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION_TAG)));
                tvItemDescription.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_DESCRIPTION)));
            }
        }

        // TODO: Set onClick listeners for btnEdit, btnDelete, and btnMark.
    }
}
