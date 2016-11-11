package com.example.budget.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.budget.Components.Constants;
import com.example.budget.MainActivity;
import com.example.budget.Models.ImageLoadedEvent;
import com.example.budget.Models.User;
import com.example.budget.Network.RestClient;
import com.example.budget.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by eaglebrosi on 11/7/16.
 */

public class EditProfileView extends LinearLayout {
    private Context context;

    public String selectedImage;
    public Bitmap scaledImage;

    @Bind(R.id.displayname_field)
    EditText changeName;

    @Bind(R.id.change_button)
    Button changeButton;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.image_button)
    Button imageButton;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.change_button)
    public void login() {
        //dropa the keyboard off the screen when the user hits the button.
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(changeName.getWindowToken(), 0);

        // gets the value from each text field.
        String fullname = changeName.getText().toString();

        User changeInfo = new  User(fullname, Constants.avatarPlaceHolder);
        RestClient restClient = new RestClient();
        restClient.getApiService().userInfo(changeInfo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Change Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't upload picture.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // goes to the Main activity to get an image.
    @OnClick(R.id.image_button)
    public void changePicture(){
        ((MainActivity)context).getImage();

//        EventBus.getDefault().post(new ImageStartEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelectedImage(ImageLoadedEvent event){
        selectedImage = event.selectedImage;
        Bitmap image = BitmapFactory.decodeFile(selectedImage);
        imageView.setImageBitmap(image);
    }

    private void resetView(){
        changeButton.setEnabled(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }
}