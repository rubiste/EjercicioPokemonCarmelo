package com.example.ejerciciopokemoncarmelo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ejerciciopokemoncarmelo.model.Pokemon;
import com.example.ejerciciopokemoncarmelo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<Pokemon> dataset;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public PokemonAdapter (Context context, OnItemClickListener listener){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Pokemon pokemon);
    }

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, final int position) {
        final Pokemon pokemon = dataset.get(position);
        holder.tvPokemon.setText(pokemon.getName());
        holder.tvType.setText(pokemon.getType());
        Uri imagenPokemon = Uri.parse(pokemon.getImage());

        Glide.with(context)
                .load(imagenPokemon)
                .override(500,500)
                .into(holder.imgPokemon);

        holder.pokemonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "position: "+position, Toast.LENGTH_SHORT).show();
                listener.onItemClick(pokemon);
            }
        });
    }

    @Override
    public int getItemCount() {
        int elementos = 0;
        if (dataset != null){
            elementos = dataset.size();
        }
        return elementos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPokemon;
        private TextView tvPokemon, tvType;
        private CardView pokemonCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPokemon = itemView.findViewById(R.id.imgPokemon);
            tvPokemon = itemView.findViewById(R.id.tvPokemon);
            tvType = itemView.findViewById(R.id.tvType);
            pokemonCard = itemView.findViewById(R.id.pokemonCard);
        }

    }

    public void setMisPokemon(List<Pokemon> misPokemon){
        this.dataset = misPokemon;
        notifyDataSetChanged();
    }
}
