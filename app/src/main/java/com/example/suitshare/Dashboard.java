package com.example.suitshare;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Dashboard extends AppCompatActivity {

    CardView addItemCard, viewItemsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addItemCard = findViewById(R.id.addItemCard);
        viewItemsCard = findViewById(R.id.viewItemsCard);

        addItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Add Item Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        viewItemsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "View Items Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
