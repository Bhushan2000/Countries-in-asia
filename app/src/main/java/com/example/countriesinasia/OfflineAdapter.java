package com.example.countriesinasia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.CircularIntArray;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.countriesinasia.room.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.OfflineVH> {
    List<User> list;
    Context context;
    private final String BASE_IMG_URL_250_PX = "https://github.com/hjnilsson/country-flags/blob/master/png250px/";

    public OfflineAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OfflineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_country_details, parent, false);
        return new OfflineVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineVH holder, int position) {
        User user = list.get(position);
        holder.name.setText(         user.getName());
        holder.capital.setText(      user.getCapital());
        holder.region.setText(       user.getRegion());
        holder.subregion.setText(     user.getSubRegion());
        holder.population.setText(     user.getPopulation().toString());
        holder.borders.setText(      user.getBorders().toString());



        holder.languages.append(user.getLanguages());


//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load("https://cdn3.vectorstock.com/i/1000x1000/42/97/critical-error-icon-vector-124297.jpg")
//                .placeholder((R.drawable.image))
//                .error(R.drawable.image_error)
//                .into(holder.flag);

//        Glide.with(context).load(BASE_IMG_URL_250_PX + user.getAlpha2Code().toLowerCase() + ".png?raw=true")
//                .placeholder((R.drawable.image))
//                .error(R.drawable.image_error)
//                .into(holder.image_view_country_flag);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class OfflineVH extends RecyclerView.ViewHolder {
       // CircleImageView image_view_country_flag;
        TextView name, capital, region, subregion, population, borders, languages;

        public OfflineVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_view_country_name);
            capital = itemView.findViewById(R.id.text_view_country_capital);
            region = itemView.findViewById(R.id.text_view_country_region);
            subregion = itemView.findViewById(R.id.text_view_country_subRegion);
            population = itemView.findViewById(R.id.text_view_country_population);
            borders = itemView.findViewById(R.id.text_view_country_borders);
            languages = itemView.findViewById(R.id.text_view_country_languages);
           // image_view_country_flag = itemView.findViewById(R.id.image_view_country_flag);

        }
    }
}
