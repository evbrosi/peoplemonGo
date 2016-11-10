package com.example.budget.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Riggers.VerticalSlideRigger;

/**
 * Created by eaglebrosi on 11/8/16.
 */

public class EditProfileStage extends IndexedStage {
    private final VerticalSlideRigger rigger;

    public EditProfileStage(Application context) {
        super(MapStage.class.getName());
        // this is calling the Riggers/SlideRigger
        this.rigger = new VerticalSlideRigger(context);
    }

    public EditProfileStage() {
        this(PokemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}

