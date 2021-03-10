package com.example.countriesinasia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.countriesinasia.room.User;

import java.util.List;

public class OfflineCountries extends AppCompatActivity {
    private TextView tvOfflineCountries;

    OfflineAdapter  offlineAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_countries);
      //  tvOfflineCountries = findViewById(R.id.tvOfflineCountries);

        recyclerView = findViewById(R.id.offlineRecyclerview);


        List<User> userList = MainActivity.userDatabase.userDao().getCountries();
        String info = "";
//        for (User user : userList) {
//            String name = user.getName();
//            String capital = user.getCapital();
//            String region = user.getRegion();
//            String subRegion = user.getSubRegion();
//            String population = user.getPopulation();
//            String borders = user.getBorders();
//            String language = user.getLanguages();
//            String flag = user.getFlag();
////
//            info = info + "\n\n" + name + "\n\n" +
//                    capital + "\n\n" +
//                    region + "\n\n" +
//                    subRegion + "\n\n" +
//                    population + "\n\n" +
//                    borders + "\n\n" +
//                    language +
//                    flag;
//        }
//        tvOfflineCountries.setText(info);

        generateDataList(userList);
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<User> userList) {

        offlineAdapter= new OfflineAdapter(userList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OfflineCountries.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(offlineAdapter);
    }
}