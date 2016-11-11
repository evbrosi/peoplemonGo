package com.example.budget.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Riggers.VerticalSlideRigger;

/**
 * Created by eaglebrosi on 11/11/16.
 */

public class UsersNearbyStage extends IndexedStage {
    private final VerticalSlideRigger rigger;

    public UsersNearbyStage(Application context){
        super(MapStage.class.getName());
        // this is calling the Riggers/SlideRigger
        this.rigger = new VerticalSlideRigger(context);
    }

    public UsersNearbyStage() {
        this(PokemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.nearby_list_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}