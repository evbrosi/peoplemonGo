package com.example.budget.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.budget.Models.User;
import com.example.budget.Network.RestClient;
import com.example.budget.Network.UserStore;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Stages.MapStage;
import com.example.budget.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.budget.Components.Constants.grantType;
import static com.example.budget.Components.Constants.token;
import static com.example.budget.R.id.password_field;
import static com.example.budget.R.id.username_field;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public class LoginView extends LinearLayout {
    private Context context;
    //this is the email- this swagger API is silly!
    @Bind(username_field)
    EditText usernameField;

    @Bind(password_field)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    //looking at the life cycle of a view.
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void showRegisterView() {
        Flow flow = PokemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.login_button)
    public void login() {
        //drop the keyboard off the screen when the user hits the button.
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);

        // gets the username and password.
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String grant_type = grantType;

        // if they don't have both fields filled out we throw a toast at them.
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.fill_out, Toast.LENGTH_LONG).show();
        } else {
            //if they give us both a username and password, we disable the login button so that it can't be spammed.
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            User user = new User(username, password, grant_type);
            RestClient restClient = new RestClient();
            restClient.getApiService().login(username,password,grant_type).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    // now, here's the tricky thing. we have to make sure the password and username is correct
                    // isSuccessful checks if the server goes between 200 and 299.
                    if (response.isSuccessful()) {
                        // if the response is right, we wanna pass it in.

                        User authUser = response.body();
                        UserStore.getInstance().setToken(authUser.getAccess_token());
                        UserStore.getInstance().setTokenExpiration(authUser.getExpires());

                        Toast.makeText(context, "OH MY GOD IT WORKED!!!", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, token, Toast.LENGTH_LONG).show();
  //                      Toast.makeText(context, tokenExpiration, Toast.LENGTH_LONG).show();

                        Flow flow = PokemonApplication.getMainFlow();
                        History newHistory = History.single(new MapStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    } else {
                        // so if the username and password is wrong.
                        resetView();
                        Toast.makeText(context, "DIDN'T WORK" + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // if there's a time out or another error it resets the buttons and then pops up with a try again.
                    resetView();
                    Toast.makeText(context, "big old mistake kid", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }
    }

    private void resetView(){
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}



