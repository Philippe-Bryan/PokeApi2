package com.example.pokeapi;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    private  static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/";
    //private static final String QUERY_PARAM = "pokemon";
    //private static final String MAX_RESULTS = "maxResults";
    //private static final String TIP_IMPRESS = "previous";
    static String buscaInfosPokemon(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String pokeJSONString = null;
        try {
            //Uri builtURI = Uri.parse(POKEMON_URL).buildUpon()
                    //.appendQueryParameter(QUERY_PARAM, queryString)
                    //.appendQueryParameter(MAX_RESULTS, "1")
                    //.appendQueryParameter(TIP_IMPRESS, "name")
                    //.build();
            //URL requestURL = new URL(builtURI.toString());

            String p = POKEMON_URL;
            p = p.concat(queryString);
            URL requestURL = new URL(p);

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            pokeJSONString = builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, pokeJSONString);
        return pokeJSONString;
    }
}
