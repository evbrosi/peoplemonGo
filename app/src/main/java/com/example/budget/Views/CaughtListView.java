package com.example.budget.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.budget.Adapters.ListCaughtAdapter;
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
 * Created by eaglebrosi on 11/9/16.
 */

public class CaughtListView extends LinearLayout {
    private Context context;
    private ListCaughtAdapter listCaughtAdapter;

    @Bind(R.id.recycle_pokemon)
    RecyclerView recyclerView;

    public CaughtListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        listCaughtAdapter = new ListCaughtAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listCaughtAdapter);
        listCaughtPeople();
    }
    private void listCaughtPeople(){
        RestClient restClient = new RestClient();
        restClient.getApiService().caught().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){
                    Toast.makeText(context, "HAHAHAHAHAHAHAHAHAHAHAHAH", Toast.LENGTH_SHORT).show();
                    listCaughtAdapter.caughtUsers = new ArrayList<>(Arrays.asList(response.body()));
                    for (User user  : listCaughtAdapter.caughtUsers) {
                        listCaughtAdapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}