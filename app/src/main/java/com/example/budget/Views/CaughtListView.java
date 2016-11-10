package com.example.budget.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.budget.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eaglebrosi on 11/9/16.
 */

public class CaughtListView extends RecyclerView {
    private Context context;

    @Bind(R.id.list_caught)
    TextView userNameField;

    public CaughtListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        context = this.context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void getPeopleMon(){

    }
}