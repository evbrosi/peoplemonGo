package com.example.budget;

import android.app.Application;

import com.example.budget.Stages.MapStage;

import flow.Flow;
import flow.History;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public class PokemonApplication extends Application {

    private static PokemonApplication application;
    public final Flow mainFlow = new Flow(History.single(new MapStage()));
    // we call the api first part of the string.
    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/";


//    https://efa-peoplemon-api.azurewebsites.net/swagger/ui/index#!/Account/Account_GetUserInfo
//    https://efa-peoplemon-api.azurewebsites.net:443/

    // This is for the Pokemon app
    //"https://efa-peoplemon-api.azurewebsites.net:443/"

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static PokemonApplication getInstance(){
        return application;
    }

    public static Flow getMainFlow(){
        return getInstance().mainFlow;
    }
}
