package com.example.budget.Adapters;

/**
 * Created by eaglebrosi on 11/10/16.
 */
/*
public class ListCaughtAdapter extends RecyclerView.Adapter <ListCaughtAdapter.UserListHolder> {
    private Context context;

    public ArrayList<User> caughtUsers;

    public ListCaughtAdapter(ArrayList<User> caughtUsers, Context context){
        this.caughtUsers = caughtUsers;
        this.context = context;
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
