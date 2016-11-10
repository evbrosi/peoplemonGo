package com.example.budget.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Riggers.VerticalSlideRigger;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public class RegisterStage extends IndexedStage {
    private final VerticalSlideRigger rigger;

        public RegisterStage(Application context){
            super(MapStage.class.getName());
            // this is calling the Riggers/SlideRigger
            this.rigger = new VerticalSlideRigger(context);
        }

        public RegisterStage() {
            this(PokemonApplication.getInstance());
        }

        @Override
        public int getLayoutId() {
            return R.layout.register_view;
        }

        @Override
        public Stage.Rigger getRigger() {
            return rigger;
        }
}
