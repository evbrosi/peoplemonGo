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
 * Created by eaglebrosi on 11/10/16.
 */

// we call that Recycler View! it says how many rows we have. it does that by looking at the getItemCount
// so then it takes that value to onCreateViewHolder which then binds it with the onBindViewHolder
// then it goes to bindCategory.

public class ListCaughtAdapter extends RecyclerView.Adapter<ListCaughtAdapter.ListCaughtHolder> {
    public ArrayList<User> caughtUsers;
    private Context context;

    // simple constructor for the usernames.
    public ListCaughtAdapter(ArrayList<User> caughtUsers, Context context) {
        this.caughtUsers = caughtUsers;
        this.context = context;
    }

    @Override
    public ListCaughtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.users_caught_item, parent,
                false);
        return new ListCaughtHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(ListCaughtHolder holder, int position) {
        User caughtUser = caughtUsers.get(position);
        holder.bindCaughtUser(caughtUser);
    }

    @Override
    public int getItemCount() {
        return caughtUsers == null ? 0 : caughtUsers.size();
    }

    class ListCaughtHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.list_caught)
        TextView listCaught;

        public ListCaughtHolder(View usersCaught) {
            super(usersCaught);
            ButterKnife.bind(this, usersCaught);
        }

        public void bindCaughtUser(User caughtUser) {
            listCaught.setText(caughtUser.getNotAnEmail());
        }
    }
}