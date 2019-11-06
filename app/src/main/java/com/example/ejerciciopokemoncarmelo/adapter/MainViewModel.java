package com.example.ejerciciopokemoncarmelo.adapter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ejerciciopokemoncarmelo.model.Pokemon;
import com.example.ejerciciopokemoncarmelo.model.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Pokemon>> pokemon;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        pokemon = repository.getPokemonLive(); //se hace aquí porque cuanto antes se haga antes se cargan los datos al poblarlos
    }

    public LiveData<List<Pokemon>> getPokemon() {
        return pokemon;
    }

    public void edit(Pokemon pokemon){repository.editPokemon(pokemon);}

    public void delete(Pokemon pokemon){repository.deletePokemon(pokemon);}

    public void insert(Pokemon pokemon){
        repository.insertPokemon(pokemon);
    }
}
