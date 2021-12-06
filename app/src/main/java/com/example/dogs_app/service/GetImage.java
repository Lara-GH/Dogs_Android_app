package com.example.dogs_app.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.dogs_app.activity.ShowDogActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImage extends AsyncTask<String, Void, Bitmap> {

    String breed;
    String urlImage;
    ImageView imageView;

    protected void onPreExecute() {
        super.onPreExecute();
        ShowDogActivity.showTextResultInfo("Please wait..");
    }

    public GetImage(ImageView image, String breed, String urlImage) {
        this.imageView = image;
        this.breed = breed;
        this.urlImage = urlImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        ShowDogActivity.showTextResultInfo(breed);
        HttpURLConnection connection = null;
        Bitmap bitmap = null;

        try {
            URL url = new URL(urlImage);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(stream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.i("TAGSS", bitmap.toString());
        ShowDogActivity.setImageOnImageView(bitmap);
    }
}