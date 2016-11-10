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

public class ListCaughtAdapter extends RecyclerView.Adapter <ListCaughtAdapter.ListCaughtHolder> {
    public ArrayList<User> caughtUsers;
    private Context context;

    // simple constructor for the usernames.
    public ListCaughtAdapter(ArrayList<User> caughtUsers, Context context){
        this.caughtUsers = caughtUsers;
        this.context = context;
    }

    public ArrayList<User> getCaughtUsers() {
        return caughtUsers;
    }

    public void setCaughtUsers(ArrayList<User> caughtUsers) {
        this.caughtUsers = caughtUsers;
    }

    public ListCaughtHolder onCreateViewHolder(View name) {

    }

    class ListCaughtHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_name_field)
        TextView user_name_field;

        public ListCaughtHolder()
    }


    @Override
    public void onBindViewHolder(ListCaughtAdapter.UserListHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(UserListHolder holder, int position) {
        User user = caughtUsers.get(position);
        holder.bindUser(user);
    }

    getItemCount

    }

    @Override
    public int getItemCount() {
        return caughtUsers == null ? 0 : caughtUsers.size();
    }

    @Override
    public UserListAdapter.UserListHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //ctrl-i
        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.caught_list_item, parent, false);
        return new UserListAdapter.UserListHolder(inflatedView);
    }


    class UserListHolder extends RecyclerView.ViewHolder { //populates view

        @Bind(R.id.user_name_field)
        TextView nameField;


        public UserListHolder(View itemView) { //ctrl-o
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {
            nameField.setText(user.getCaughtUserId());
        }
    }


    public UserListAdapter(ArrayList<User> caughtUsers, Context context) {
        this.caughtUsers = caughtUsers;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(UserListAdapter.UserListHolder holder, int position) {
        User user = caughtUsers.get(position);
        holder.bindUser(user);

    }

    @Override
    public int getItemCount() {
        return caughtUsers == null ? 0 : caughtUsers.size();
    }

    @Override
    public UserListAdapter.UserListHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //ctrl-i
        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.caught_list_item, parent, false);
        return new UserListAdapter.UserListHolder(inflatedView);
    }


    class UserListHolder extends RecyclerView.ViewHolder { //populates view

        @Bind(R.id.user_name_field)
        TextView nameField;


        public UserListHolder(View itemView) { //ctrl-o
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {
            nameField.setText(user.getCaughtUserId());
        }
    }
}
*/
