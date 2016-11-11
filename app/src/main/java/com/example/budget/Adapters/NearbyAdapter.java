package com.example.budget.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budget.Models.User;
import com.example.budget.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eaglebrosi on 11/11/16.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyHolder> {
    public ArrayList<User> nearbyUsers;
    private Context context;

    // simple constructor for the usernames.
    public NearbyAdapter(ArrayList<User> caughtUsers, Context context) {
        this.nearbyUsers = nearbyUsers;
        this.context = context;
    }

    @Override
    public NearbyAdapter.NearbyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.users_caught_item, parent,
                false);
        return new NearbyAdapter.NearbyHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(NearbyHolder holder, int position) {
        User nearbyUser = nearbyUsers.get(position);
        holder.bindNearbyUser(nearbyUser);
    }

    @Override
    public int getItemCount() {
        return nearbyUsers == null ? 0 : nearbyUsers.size();
    }

    class NearbyHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.list_nearby)
        TextView listNearby;

        public NearbyHolder(View usersNearby) {
            super(usersNearby);
            ButterKnife.bind(this, usersNearby);
        }

        public void bindNearbyUser(User nearbyUser) {
            listNearby.setText(nearbyUser.getNotAnEmail());
        }
    }
}
