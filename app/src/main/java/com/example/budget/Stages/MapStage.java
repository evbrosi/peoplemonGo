package com.example.budget.Stages;

import android.app.Application;

import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Riggers.SlideRigger;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public class MapStage extends IndexedStage {
    private final SlideRigger rigger;

    public MapStage(Application context){
        super(MapStage.class.getName());
        // this is calling the Riggers/SlideRigger
        this.rigger = new SlideRigger(context);
    }

    public MapStage() {
        this(PokemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.map_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
