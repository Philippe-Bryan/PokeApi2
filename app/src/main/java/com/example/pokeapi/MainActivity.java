package com.example.pokeapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String nmPoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClickPesq(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null){
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        EditText txtDigite = (EditText) findViewById(R.id.txtDigite);

        nmPoke = txtDigite.getText().toString();

        Intent intent = new Intent(this, MostraResult.class);
        Bundle bundlePoke = new Bundle();

        bundlePoke.putString("key_nmPoke", String.valueOf(nmPoke));
        intent.putExtras(bundlePoke);
        startActivity(intent);
    }
}
