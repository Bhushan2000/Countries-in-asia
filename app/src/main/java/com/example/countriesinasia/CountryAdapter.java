package com.example.countriesinasia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryVH> {
    List<Country> countryList;
    Context context;
    private final String BASE_IMG_URL_250_PX = "https://github.com/hjnilsson/country-flags/blob/master/png250px/";


    public CountryAdapter(List<Country> countryList, Context context) {
        this.countryList = countryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);
        return new CountryVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CountryVH holder, int position) {
        Country country = countryList.get(position);
        holder.name.setText("Country: " + country.getName());
        holder.capital.setText("Capital: " + country.getCapital());
        holder.region.setText("Region: " + country.getRegion());
        holder.subregion.setText("SubRegion: " + country.getSubregion());
        holder.population.setText("Population: " + country.getPopulation().toString());
        holder.borders.setText("Borders: " + country.getBorders().toString());


        for (Language language : country.getLanguages()) {
            String content = "";

            content += "languageName: " + language.getLanguageName() + "\n\n";

            content += "nativeName: " + language.getNativeName() + "\n\n";

            holder.languages.append(content);
        }


//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load("https://cdn3.vectorstock.com/i/1000x1000/42/97/critical-error-icon-vector-124297.jpg")
//                .placeholder((R.drawable.image))
//                .error(R.drawable.image_error)
//                .into(holder.flag);
        Glide.with(context).load(BASE_IMG_URL_250_PX + country.getAlpha2Code().toLowerCase() + ".png?raw=true")
                .placeholder((R.drawable.image))
                .error(R.drawable.image_error)
                .into(holder.flag);











    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    class CountryVH extends RecyclerView.ViewHolder {
        TextView name, capital, region, subregion, population, borders, languages;
        ImageView flag;

        public CountryVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            capital = itemView.findViewById(R.id.tvCapital);
            region = itemView.findViewById(R.id.tvRegion);
            subregion = itemView.findViewById(R.id.tvSubRegion);
            population = itemView.findViewById(R.id.tvPopulation);
            borders = itemView.findViewById(R.id.tvBorders);
            languages = itemView.findViewById(R.id.tvLanguages);
            flag = itemView.findViewById(R.id.ivFlag);

        }

    }
}
