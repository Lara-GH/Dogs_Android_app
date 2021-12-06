package com.example.dogs_app.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dogs_app.activity.ShowDogActivity;
import com.example.dogs_app.activity.ChooseDogActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetUrlContentTask extends AsyncTask<String, String, String> {

    public Context context;
    public boolean getImage = false;
    public String breed;

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();

            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result != null) {

            Log.i("TAGSS", result.toString());

            Log.i("TAGSS", String.valueOf(getImage));

            if (getImage) {
                //get picture
                try {
                    JSONObject jObject = new JSONObject(result);
                    String urlImage = jObject.get("message").toString();

                    ShowDogActivity.showDog(urlImage, breed);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //get list
                try {
                    JSONObject jObject = new JSONObject(result);
                    JSONObject jObjectIn = jObject.getJSONObject("message");
                    List<String> list = new ArrayList<String>();
                    Iterator<String> keys = jObjectIn.keys();
                    while (keys.hasNext()) {
                        list.add((String)keys.next());
                    }

                    ChooseDogActivity.initSpinner(list, context);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
