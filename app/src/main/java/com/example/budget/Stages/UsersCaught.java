package com.example.budget.Stages;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Riggers.VerticalSlideRigger;
import com.example.budget.Views.CaughtListView;

/**
 * Created by eaglebrosi on 11/10/16.
 */

public class UsersCaught {
    private final VerticalSlideRigger rigger;

    public UsersCaught(PokemonApplication context){
        super(MapStage.class.getName());
        // this is calling the Riggers/SlideRigger
        this.rigger = new VerticalSlideRigger(context);
    }

    public UsersCaught() {
        this(PokemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.CaughtListView;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
