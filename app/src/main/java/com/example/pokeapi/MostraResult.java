package com.example.pokeapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MostraResult extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private TextView NomePoke, IdPoke, DescPoke;
    private ImageView imagePoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_result);

        Intent intent2 = getIntent();
        Bundle bundlePoke = intent2.getExtras();

        NomePoke = ((TextView) findViewById(R.id.lblNomePoke));
        IdPoke = ((TextView) findViewById(R.id.lblIdPoke));
        DescPoke = ((TextView) findViewById(R.id.lblDescPoke));
        //imagePoke = (ImageView) itemView.findViewById(R.id.imgPoke);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        String queryString = bundlePoke.getString("key_nmPoke");

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            IdPoke.setText(" ");
            DescPoke.setText("Carregando...");
        } else {
            if (queryString.length() == 0) {
                IdPoke.setText(" ");
                DescPoke.setText("Informe o nome correto de um pokemon.");
            } else {
                IdPoke.setText(" ");
                DescPoke.setText("Verifique sua conexão.");
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if(args != null){
            queryString = args.getString("queryString");
        }
        return new CarregaPokemons(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArrey = jsonObject.getJSONArray("items");
            int i = 0;
            int id = 0;
            String desc = null;

            while (i < itemsArrey.length() &&
                    (id == 0 && desc == null)){
                JSONObject poke = itemsArrey.getJSONObject(i);
                JSONObject volumeInfo = poke.getJSONObject("volumeInfo");
                try{
                    id = volumeInfo.getInt("id");
                    desc = volumeInfo.getString("description");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                i++;
            }if (id != 0 && desc != null ){
                IdPoke.setText(id);
                DescPoke.setText(desc);
            } else {
                IdPoke.setText("Nenhum resultado encontrado");
                DescPoke.setText(" ");
            }
        } catch (Exception e){
            IdPoke.setText("Nenhum resultado encontrado");
            DescPoke.setText(" ");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        //sem ações executadas
    }

    public void OnClickVoltar(View l){
        Intent intentReturn = new Intent(this, MainActivity.class);
        startActivity(intentReturn);
    }

    public void OnBulba(View bulba){
        Intent intent2 = getIntent();
        Bundle bundlePoke = intent2.getExtras();
        String link = bundlePoke.getString("key_nmPoke");

        Uri webpage = Uri.parse("https://bulbapedia.bulbagarden.net/wiki/"+link);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

        startActivity(webIntent);
    }
}
