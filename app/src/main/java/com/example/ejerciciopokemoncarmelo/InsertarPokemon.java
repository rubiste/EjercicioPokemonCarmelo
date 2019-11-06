package com.example.ejerciciopokemoncarmelo;

import android.content.Intent;
import android.net.Uri;
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

public class InsertarPokemon extends AppCompatActivity {

    private boolean pickImage = false;
    private String myUri;
    private static final int PHOTO_SELECTED = 1;
    private Spinner sChooseType;
    private EditText etChooseName;
    private Button btPickImage, btSavePokemon;
    private ImageView ivPokemonPicker;
    private TextView tvError;

    private Pokemon pokemon;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_pokemon);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponents();
    }

    private void initComponents() {
        ivPokemonPicker = findViewById(R.id.ivPokemonPicker);
        btPickImage = findViewById(R.id.btPickImage);
        btSavePokemon = findViewById(R.id.btSavePokemon);
        etChooseName = findViewById(R.id.etChooseName);
        sChooseType = findViewById(R.id.sChooseType);
        tvError = findViewById(R.id.tvError);

        pokemon = new Pokemon();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        btPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btSavePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selec = sChooseType.getSelectedItemPosition();
                tvError.setTextColor(getResources().getColor(R.color.red));
                if (selec == 0){
                    tvError.setText(getString(R.string.typeError));
                }else if (etChooseName.length() == 0){
                    tvError.setText(getString(R.string.nameError));
                }else if(!pickImage){
                    tvError.setText(getString(R.string.photoError));
                }else{
                    tvError.setText("");
                    insertPokemon();
                }
            }
        });
    }

    private void insertPokemon() {
        String type = sChooseType.getSelectedItem().toString();
        String name = etChooseName.getText().toString();

        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setImage(myUri);

        viewModel.insert(pokemon);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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
                    .into(ivPokemonPicker);
            myUri = miPokemon.toString();
        }
    }
}
