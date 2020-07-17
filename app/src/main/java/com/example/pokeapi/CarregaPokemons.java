package com.example.pokeapi;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CarregaPokemons extends AsyncTaskLoader<String> {
    private  String mQueryString;
    CarregaPokemons(Context context, String queryString){
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground(){
        return NetworkUtils.buscaInfosPokemon(mQueryString);
    }
}
