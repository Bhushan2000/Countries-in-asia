package com.example.countriesinasia;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.countriesinasia.room.User;
import com.example.countriesinasia.room.UserDatabase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InternetConnectivityListener {
    ApiInterface apiInterface;
    private static final String TAG = "MainActivity";
    private TextView tvShow;
    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private RelativeLayout relativeLayout;
    private FloatingActionButton mFloatingActionButton;
    public static UserDatabase userDatabase;
    private AlertDialog alertDialog;
    private InternetAvailabilityChecker mInternetAvailabilityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tvShow = findViewById(R.id.tvShow);
        recyclerView = findViewById(R.id.recyclerview);
        relativeLayout = findViewById(R.id.relativeLayout);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);

        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "CountryDb").allowMainThreadQueries().build();


        getCountries();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectOperation(MainActivity.this);


            }
        });


    }

    private void getCountries() {
        Call<List<Country>> call = apiInterface.getList();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (!response.isSuccessful()) {
                    tvShow.setText("code: " + response.code());
                    return;
                }


                relativeLayout.setVisibility(View.GONE);


                generateDataList(response.body());


            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
                relativeLayout.setVisibility(View.GONE);
                tvShow.setText("You  are offline !!");

            }
        });


    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Country> countryList) {

        countryAdapter = new CountryAdapter(countryList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(countryAdapter);
    }

    // add data
    private void addData() {
        TextView name, capital, region, subregion, population, borders, languages;
        ImageView flag;
        LayoutInflater inflater = getLayoutInflater();
        View myView = inflater.inflate(R.layout.country_row, null);


        name = findViewById(R.id.tvName);
        capital = findViewById(R.id.tvCapital);
        region = findViewById(R.id.tvRegion);
        subregion = findViewById(R.id.tvSubRegion);
        population = findViewById(R.id.tvPopulation);
        borders = findViewById(R.id.tvBorders);
        languages = findViewById(R.id.tvLanguages);
        flag = findViewById(R.id.ivFlag);

        String cName = name.getText().toString();
        String cCapital = capital.getText().toString();
        String cRegion = region.getText().toString();
        String cSubRegion = subregion.getText().toString();
        String cPopulation = population.getText().toString();
        String cBorders = borders.getText().toString();
        String cLanguage = languages.getText().toString();
        String cFlag = flag.getDrawable().toString();


        User user = new User();


        user.setName(cName);
        user.setCapital(cCapital);
        user.setRegion(cRegion);
        user.setSubRegion(cSubRegion);
        user.setPopulation(cPopulation);
        user.setBorders(cBorders);
        user.setLanguages(cLanguage);
        user.setFlag(cFlag);


        if (user!=null){

            userDatabase.userDao().insert(user);
            Toast.makeText(MainActivity.this, "Country Added Successfully !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Offline.. !!!", Toast.LENGTH_SHORT).show();

        }

    }

    private void selectOperation(Context context) {
        final CharSequence[] options = {"Save countries offline", "Delete offline countries", "Show offline countries", "Cancel"};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Choose your operation");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Save countries offline")) {

                    addData();


                } else if (options[item].equals("Delete offline countries")) {
                    if (userDatabase.userDao().getCountries() != null) {

                        userDatabase.userDao().deleteCountry();
                        Toast.makeText(MainActivity.this, "Country Deleted Successfully...!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Country Data not found", Toast.LENGTH_SHORT).show();

                    }

                } else if (options[item].equals("Show offline countries")) {
                    Intent intent = new Intent(context, OfflineCountries.class);
                    startActivity(intent);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {

            getCountries();

        } else {
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("internet is connected or not");
            alertDialog.setMessage("not connected");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
    }
}