package com.example.budget;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.example.budget.Models.ImageLoadedEvent;
import com.example.budget.Network.UserStore;
import com.example.budget.Stages.EditProfileStage;
import com.example.budget.Stages.LoginStage;
import com.example.budget.Stages.MapStage;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

import static com.example.budget.PokemonApplication.getMainFlow;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private String TAG = "MainActivity";
    // flow holds the data of what is where!
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    // Main activity doesn't have a saved Instacnce State so we make it a variable and give it a value in OnCreate. DAWG!
    public Bundle savedInstanceState;
    private Menu menu;
// for the picture
    private static int RESULT_LOAD_IMAGE = 1;

//    private GoogleApiClient mGoogleApiClient;

    //butterknife and the convention is two lines no semi-colin.
    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    // gotta bundle that savedInstance state. It's why we made the variable in the first place. We gotta do this for the PeopleMapView later.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // we have to have this line. It won't call the container and it'll be empty!
        ButterKnife.bind(this);

        //we get a reference for our main application
        flow = getMainFlow();
        //controls the UI, says this is the main activity and the container is the view.
        dispatcher = new ScreenplayDispatcher(this, container);
        // we set up based on the flow.
        dispatcher.setUp(flow);

      //  testCalls();
        if(UserStore.getInstance().getToken()== null|| UserStore.getInstance().getTokenExpiration() ==null) {
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        }

        //COMMENT OUT THIS FILE!
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onBackPressed(){
        // if it can't do it we need to do some things.
        if(!flow.goBack()){
            // we get rid of the dispatcher
            flow.removeDispatcher(dispatcher);
            // we delete the history.
            flow.setHistory(History.single(new MapStage()),
                    Flow.Direction.FORWARD);
            // then try again.
            super.onBackPressed();
        }
    }

//just a menu button to put in the top right corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.open_edit_profile:
                Flow flow = PokemonApplication.getMainFlow();
                History newHistory = flow.getHistory().buildUpon()
                        .push(new EditProfileStage())
                        .build();
                flow.setHistory(newHistory, Flow.Direction.FORWARD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMenuItem(boolean show){
        if(menu != null){
            menu.findItem(R.id.open_edit_profile).setVisible(show);
        }
    }

    public void getImage(){
     //   android.provider.
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imageString = cursor.getString(columnIndex);
                cursor.close();

                //Convert to Bitmap Array
                Bitmap bm = BitmapFactory.decodeFile(imageString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                //Take the bitmap Array and encode it to Base64
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                EventBus.getDefault().post(new ImageLoadedEvent(imageString));

            } else {
                Toast.makeText(this, "didn't work", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "DIDN'T WORK!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
