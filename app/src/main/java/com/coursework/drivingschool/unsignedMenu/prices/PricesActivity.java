package com.coursework.drivingschool.unsignedMenu.prices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.RegisterActivity;
import com.google.android.material.button.MaterialButton;

public class PricesActivity extends AppCompatActivity {

    private CardView backA1;
    private CardView backA2;
    private CardView backB1;
    private CardView backB2;
    private int typeA = 0;
    private int typeB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);

        MaterialButton zapisA = findViewById(R.id.zapisA_btn);
        MaterialButton zapisB = findViewById(R.id.zapisB_btn);
        MaterialButton zapisVip = findViewById(R.id.zapisVip_btn);
        MaterialButton zapisAddD = findViewById(R.id.zapisAddD_btn);

        backA1 = findViewById(R.id.backA1);
        backA2 = findViewById(R.id.backA2);
        backB1 = findViewById(R.id.backB1);
        backB2 = findViewById(R.id.backB2);

        zapisA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeA == 0) {
                    Toast.makeText(PricesActivity.this, "Выберите группу", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(PricesActivity.this, RegisterActivity.class);
                    intent.putExtra("category", "A");
                    if (typeA == 1) {
                        intent.putExtra("type", "Утренняя");
                    } else {
                        intent.putExtra("type", "Вечерняя");
                    }
                    startActivity(intent);
                }
            }
        });

        zapisB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeB == 0) {
                    Toast.makeText(PricesActivity.this, "Выберите группу", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(PricesActivity.this, RegisterActivity.class);
                    intent.putExtra("category", "B");
                    if (typeB == 1) {
                        intent.putExtra("type", "Утренняя");
                    } else {
                        intent.putExtra("type", "Вечерняя");
                    }
                    startActivity(intent);
                }
            }
        });

        zapisVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PricesActivity.this, RegisterActivity.class);
                intent.putExtra("category", "Vip");
                intent.putExtra("type", " ");
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backA1: {
                if (typeA == 1) {
                    typeA = 0;
                    backA1.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                } else {
                    typeA = 1;
                    backA1.setCardBackgroundColor(getResources().getColor(R.color.prices_field_click));
                    backA2.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                }
                break;
            }

            case R.id.backA2: {
                if (typeA == 2) {
                    typeA = 0;
                    backA2.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                } else {
                    typeA = 2;
                    backA2.setCardBackgroundColor(getResources().getColor(R.color.prices_field_click));
                    backA1.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                }
                break;
            }

            case R.id.backB1: {
                if (typeB == 1) {
                    typeB = 0;
                    backB1.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                } else {
                    typeB = 1;
                    backB1.setCardBackgroundColor(getResources().getColor(R.color.prices_field_click));
                    backB2.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                }
                break;
            }

            case R.id.backB2: {
                if (typeB == 2) {
                    typeB = 0;
                    backB2.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                } else {
                    typeB = 2;
                    backB2.setCardBackgroundColor(getResources().getColor(R.color.prices_field_click));
                    backB1.setCardBackgroundColor(getResources().getColor(R.color.prices_field));
                }
                break;
            }
        }
    }
}