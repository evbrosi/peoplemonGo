package com.example.budget.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.budget.Adapters.NearbyAdapter;
import com.example.budget.Components.Constants;
import com.example.budget.Models.User;
import com.example.budget.Network.RestClient;
import com.example.budget.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eaglebrosi on 11/11/16.
 */

public class NearbyView extends LinearLayout {
    private Context context;
    private NearbyAdapter listNearbyAdapter;

    @Bind(R.id.nearby_pokemon)
    RecyclerView recyclerView;

    public NearbyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        listNearbyAdapter = new NearbyAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listNearbyAdapter);
        listNearby();
    }
    private void listNearby(){
        final RestClient restClient = new RestClient();
        restClient.getApiService().nearby(Constants.radiusInMeters).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "a wild sweet sweet baby appears", Toast.LENGTH_SHORT).show();
                    listNearbyAdapter.nearbyUsers = new ArrayList<>(Arrays.asList(response.body()));
                    for (User user : listNearbyAdapter.nearbyUsers) {
                        listNearbyAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(context, "Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, "Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}