package com.example.budget.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.budget.Models.User;
import com.example.budget.Network.RestClient;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Stages.LoginStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.budget.Components.Constants.apiKeyRegister;
import static com.example.budget.Components.Constants.avatarPlaceHolder;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public class RegisterView extends LinearLayout {
    private Context context;

    @Bind(R.id.email_field)
    EditText emailField;

    @Bind(R.id.username_field)
    EditText usernameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.confirm_field)
    EditText confirmField;

    @Bind(R.id.register_button)
    Button registerButton;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void login() {
        //dropa the keyboard off the screen when the user hits the button.
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmField.getWindowToken(), 0);

        // gets the value from each text field.
        String email = emailField.getText().toString();
        String username = usernameField.getText().toString();
        String avatarBase64 = avatarPlaceHolder;
        String apiKey = apiKeyRegister;
        String password = passwordField.getText().toString();

        String confirm = confirmField.getText().toString();

        // if they don't have both fields filled out we throw a toast at them.
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() || email.isEmpty()) {
            Toast.makeText(context, R.string.fill_out, Toast.LENGTH_LONG).show();
            // email address isn't valid.
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, R.string.email_invalid, Toast.LENGTH_LONG).show();
            // passwords don;t match!
        } else if (!password.equals(confirm)) {
            Toast.makeText(context, R.string.passwords_match, Toast.LENGTH_LONG).show();
        } else {
            //if they give us both a username and password, we disable the register button so that it can't be spammed.
            registerButton.setEnabled(false);

            User user = new User(email, username, avatarBase64, apiKey, password);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(user).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "OH GOD! IT WORKED", Toast.LENGTH_LONG).show();
                        Flow flow = PokemonApplication.getMainFlow();
                        History newHistory = History.single(new LoginStage());
                        flow.setHistory(newHistory, Flow.Direction.BACKWARD);
                    } else {
                        resetView();
                        Toast.makeText(context, "DUUUUUUDDDE, DIDN'T WORK!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, "didn't work bro!!!!", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }
    }

    private void resetView(){
        registerButton.setEnabled(true);
    }
}
