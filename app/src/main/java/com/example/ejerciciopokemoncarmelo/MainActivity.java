package com.example.ejerciciopokemoncarmelo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ejerciciopokemoncarmelo.adapter.MainViewModel;
import com.example.ejerciciopokemoncarmelo.adapter.PokemonAdapter;
import com.example.ejerciciopokemoncarmelo.model.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter listaPokemonAdapter;
    private MainViewModel viewModel;
    private Button btInsertarPokemon;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        btInsertarPokemon = findViewById(R.id.btInsertarPokemon);
        btInsertarPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToInsertActivity();
            }
        });

        initComponents();
    }

    private void moveToInsertActivity() {
        Intent i = new Intent(this, InsertarPokemon.class);
        startActivity(i);
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }
*/
    private void initComponents() {
        recyclerView =findViewById(R.id.my_recycler);
        listaPokemonAdapter = new PokemonAdapter(this, new PokemonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pokemon pokemon) {
                Intent i = new Intent(MainActivity.this, PokemonSelected.class);
                i.putExtra("idPokemon", pokemon);
                startActivity(i);
            }
        });

        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPokemon().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                listaPokemonAdapter.setMisPokemon(pokemons);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
