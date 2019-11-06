package com.example.ejerciciopokemoncarmelo;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.ejerciciopokemoncarmelo.adapter.MainViewModel;
import com.example.ejerciciopokemoncarmelo.model.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PokemonSelected extends AppCompatActivity {

    private Intent e;
    private ImageView ivPokemonSelected;
    private Button btEdit, btDelete;
    private TextView tvTypeSelected, tvNameSelected;
    private Pokemon pokemon;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_selected);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initComponents();
    }

    private void initComponents() {
        e = getIntent();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        pokemon = e.getParcelableExtra("idPokemon");

        ivPokemonSelected = findViewById(R.id.ivPokemonSelected);
        Glide.with(this)
                .load(pokemon.getImage())
                .override(500, 500)// prueba de escalado
                .into(ivPokemonSelected);

        btEdit = findViewById(R.id.btEdit);
        btDelete = findViewById(R.id.btDelete);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent (PokemonSelected.this, EditPokemonActivity.class);
                e.putExtra("idPokemonEdit", pokemon);
                startActivity(e);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete(pokemon);
                Intent f = new Intent(PokemonSelected.this, MainActivity.class);
                startActivity(f);
            }
        });

    }



}
