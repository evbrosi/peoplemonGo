package com.example.budget.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.budget.MainActivity;
import com.example.budget.Models.ImageLoadedEvent;
import com.example.budget.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.budget.Components.Constants.apiKeyRegister;
import static com.example.budget.Components.Constants.avatarPlaceHolder;

/**
 * Created by eaglebrosi on 11/7/16.
 */

public class EditProfileView extends LinearLayout {
    private Context context;

    public String selectedImage;
    public Bitmap scaledImage;

    @Bind(R.id.displayname_field)
    EditText emailField;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.image_button)
    ImageButton getAPic;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void login() {
        //dropa the keyboard off the screen when the user hits the button.
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);

        // gets the value from each text field.
        String email = emailField.getText().toString();
        String avatarBase64 = avatarPlaceHolder;
        String apiKey = apiKeyRegister;
    }

    // goes to the Main activity to get an image.
    @OnClick(R.id.image_button)
    public void pictureTappaed(){
        ((MainActivity)context).getImage();

//        EventBus.getDefault().post(new ImageStartEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelectedImage(ImageLoadedEvent event){
        selectedImage = event.selectedImage;
        Bitmap image = BitmapFactory.decodeFile(selectedImage);
     //   avatar64.setImageBitmap(image);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void imageReady(ImageLoadedEvent event){
        event.getSelectedImage();
    }

    private void resetView(){
        registerButton.setEnabled(true);
    }
}
