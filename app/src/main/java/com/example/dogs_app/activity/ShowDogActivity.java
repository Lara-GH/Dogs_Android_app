package com.example.dogs_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogs_app.R;
import com.example.dogs_app.service.GetImage;
import com.example.dogs_app.service.GetUrlContentTask;

public class ShowDogActivity extends AppCompatActivity {

    public static TextView result_info;
    public static ImageView image;
    String breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dog);

        getArguments();
        initClickListener();
        initServiceConnection();
    }

    private void getArguments() {
        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            breed = arguments.getString("breed");
        }
    }

    private void initClickListener() {
        result_info = findViewById(R.id.result_info);
        image = findViewById(R.id.image);

        ImageButton btnBack = findViewById(R.id.back_btn);
        btnBack.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void initServiceConnection() {
        try {
            GetUrlContentTask getUrlContentTask = new GetUrlContentTask();
            getUrlContentTask.context = this;
            getUrlContentTask.getImage = true;
            getUrlContentTask.breed = breed;
            getUrlContentTask.execute("https://dog.ceo/api/breed/" + breed + "/images/random");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDog(String urlImage, String breed) {
        GetImage getImage = new GetImage(image, breed, urlImage);
        getImage.execute(urlImage);
    }

    public static void showTextResultInfo(String text) {
        result_info.setText(text);
    }

    public static void setImageOnImageView(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }
}