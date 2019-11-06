package com.example.ejerciciopokemoncarmelo.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ejerciciopokemoncarmelo.DAO.PokemonDAO;
import com.example.ejerciciopokemoncarmelo.database.PokemonDatabase;

import java.util.List;

public class Repository {

        private PokemonDAO pokemonDAO;
        private List<Pokemon> pokemon;
        private LiveData<List<Pokemon>> pokemonLive;
        private LiveData<Pokemon> pokemonLiveData;

        public Repository(Context contexto){
            PokemonDatabase db = PokemonDatabase.getDatabase(contexto);
            pokemonDAO = (PokemonDAO) db.getPokemonDAO();

            pokemonLive = pokemonDAO.getAllLive();

        }

        public LiveData<List<Pokemon>> getPokemonLive(){
            return pokemonLive;
        }

        public void insertPokemon(Pokemon pokemon){
            new InsertThread().execute(pokemon);
        }

        public void deletePokemon(Pokemon pokemon){
            new DeleteThread().execute(pokemon);
        }

        public void editPokemon(Pokemon pokemon){
            new EditThread().execute(pokemon);
        }

        private class InsertThread extends AsyncTask<Pokemon, Void, Void> {
            @Override
            protected Void doInBackground(Pokemon... pokemon) {
                pokemonDAO.insert(pokemon[0]);
                Log.v("xyz", pokemon[0].toString());
                return null;
            }
        }

        private class EditThread extends AsyncTask<Pokemon, Void, Void> {
            @Override
            protected Void doInBackground(Pokemon... pokemon) {
                pokemonDAO.edit(pokemon[0]);
                Log.v("xyz", pokemon[0].toString());
                return null;
            }
        }

        private class DeleteThread extends AsyncTask<Pokemon, Void, Void> {
            @Override
            protected Void doInBackground(Pokemon... pokemon) {
                pokemonDAO.delete(pokemon[0]);
                Log.v("xyz", pokemon[0].toString());
                return null;
            }
        }
    }