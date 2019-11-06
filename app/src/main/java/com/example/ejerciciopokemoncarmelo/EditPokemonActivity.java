package com.example.ejerciciopokemoncarmelo;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.ejerciciopokemoncarmelo.adapter.MainViewModel;
import com.example.ejerciciopokemoncarmelo.model.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class EditPokemonActivity extends AppCompatActivity {

    boolean pickImage = false;
    Intent i;
    private static final int PHOTO_SELECTED = 1;
    private String myUri;
    TextView tvError;
    Button btEditPokemon, btEditChoose;
    ImageView ivEditPokemon;
    EditText etEditName;
    Spinner sEditType;
    Pokemon pokemon, pokemonAux;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);
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
        i = getIntent();
        pokemon = i.getParcelableExtra("idPokemonEdit");
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        ivEditPokemon = findViewById(R.id.ivEditPokemon);
        etEditName = findViewById(R.id.etEditName);
        sEditType = findViewById(R.id.sEditType);
        tvError = findViewById(R.id.tvEditError);
        btEditPokemon = findViewById(R.id.btEditPokemon);
        btEditChoose = findViewById(R.id.btEditChoose);

        initEvents();
    }

    private void initEvents() {
        Glide.with(this)
                .load(pokemon.getImage())
                .override(500, 500)// prueba de escalado
                .into(ivEditPokemon);

        etEditName.setText(pokemon.getName());
        putSpinner();

        btEditPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPokemon();
            }
        });

        btEditChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void editPokemon() {
        pokemonAux = pokemon;
        String type = sEditType.getSelectedItem().toString();
        String name = etEditName.getText().toString();
        int selec = sEditType.getSelectedItemPosition();
        tvError.setTextColor(getResources().getColor(R.color.red));
        if (selec == 0){
            tvError.setText(getString(R.string.typeError));
        }else if (etEditName.length() == 0){
            tvError.setText(getString(R.string.nameError));
        }else{
            tvError.setText("");
            if (pickImage){
                pokemonAux.setImage(myUri);
                pokemonAux.setName(name);
                pokemonAux.setType(type);
            }else{
                pokemonAux.setImage(pokemon.getImage());
                pokemonAux.setName(name);
                pokemonAux.setType(type);
            }
            viewModel.edit(pokemonAux);
            Intent f = new Intent(this, MainActivity.class);
            startActivity(f);
        }
    }

    private boolean putSpinner(){
        String type = pokemon.getType();
        for (int i=0; i<19; i++){
            sEditType.setSelection(i);
            if (sEditType.getSelectedItem().toString().compareTo(type) == 0){
                return true;
            }
        }
        return false;
    }

    private void chooseImage() {
        Intent f = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(f, PHOTO_SELECTED);
        pickImage = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            android.net.Uri miPokemon = data.getData();


            Glide.with(this)
                    .load(miPokemon)
                    .override(500, 500)// prueba de escalado
                    .into(ivEditPokemon);
            myUri = miPokemon.toString();
        }
    }
}
