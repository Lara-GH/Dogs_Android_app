package com.example.dogs_app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.dogs_app.R;
import com.example.dogs_app.service.GetUrlContentTask;

public class ChooseDogActivity extends AppCompatActivity {

    Context context = ChooseDogActivity.this;
    public static String selectedItem;
    public static Spinner spinner;
    public GetUrlContentTask getUrlContentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initServiceConnection();
        initClickListeners();
    }

    private void initServiceConnection() {
        try {
            getUrlContentTask = new GetUrlContentTask();
            getUrlContentTask.context = this;
            getUrlContentTask.getImage = false;
            getUrlContentTask.execute("https://dog.ceo/api/breeds/list/all");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initClickListeners() {
        spinner = findViewById(R.id.breeds_list);
        Button btnShowDog = findViewById(R.id.main_btn);
        ImageButton btnExitApp = findViewById(R.id.exit_btn);

        btnShowDog.setOnClickListener(view -> {
            if (!(selectedItem.equals(""))) {
                String str = selectedItem.toString();
                Intent intent = new Intent(context, ShowDogActivity.class);
                intent.putExtra("breed", str);
                startActivity(intent);
            }
        });

        btnExitApp.setOnClickListener(view -> {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(ChooseDogActivity.this, R.style.StyleCustomDialog);
            a_builder.setMessage("Close app?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = a_builder.create();
            alertDialog.show();
        });
    }

    public static void initSpinner(List<String> list, Context context) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = spinner.getItemAtPosition(position).toString();
                Toast.makeText(context, selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}