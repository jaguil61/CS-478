//Jose M. Aguilar (jaguil61)

package edu.jaguil61.project5musicclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.jaguil61.project5aidl.MyAidlInterface;

public class MainActivity extends AppCompatActivity {

    public static MyAidlInterface myMusicService;
    private Boolean isBound = false;
    private Button bindButton, unbindButton, downloadButton;
    private TextView bindText;
    private List<String> songNameList = new ArrayList<>(); //list of all the song names
    private List<String> artistNameList = new ArrayList<>(); //list of all the artist names
    private List<Bitmap> songCoverList = new ArrayList<>(); //list of all the song photos
    private List<Uri> songUriList = new ArrayList<>(); //list of all the song URLs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindButton = (Button) findViewById(R.id.bindButton);
        unbindButton = (Button) findViewById(R.id.unbindButton);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        bindText = (TextView) findViewById(R.id.bindText);

        downloadButton.setEnabled(false); //service must be bounded first!
        unbindButton.setEnabled(false); //service must be bounded first!

        bindButton.setOnClickListener(v -> checkBindingAndBind());

        unbindButton.setOnClickListener(v -> unbindApp());

        downloadButton.setOnClickListener(v -> {
            try {

                displayInfo();

            }
            catch (RemoteException e) {

                Log.e("CLIENT_APP", e.toString());

            }
        });
    }

    //if its bound and if so it'll display the text
    private void displayInfo() throws RemoteException {

        if (isBound) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            startActivity(intent);
        }

        else {
            Log.i("CLIENT_APP", "App was not bound!");
        }

    }

    //unbind when app is closed
    @Override
    protected void onStop() {
        super.onStop();

        unbindApp();
    }

    //unbinds the app
    protected void unbindApp() {
        String text = "Service Not Bound";

        if (isBound) {
            unbindService(this.myConnection);

            bindButton.setEnabled(true);
            downloadButton.setEnabled(false); //songs can't be downloaded
            unbindButton.setEnabled(false); //already unbounded
            bindText.setText(text);

            isBound = false;
        }

        //else do nothing
    }

    protected void checkBindingAndBind() {
        String text = "Service Bounded";

        if (!isBound) {
            boolean b = false;

            Intent intent = new Intent(MyAidlInterface.class.getName());
            ResolveInfo info = getPackageManager().resolveService(intent, 0);
            intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(intent, myConnection, Context.BIND_AUTO_CREATE);

            bindButton.setEnabled(false); //service is already bounded
            downloadButton.setEnabled(true); //songs can be downloaded now
            unbindButton.setEnabled(true); //service can be unbounded now
            bindText.setText(text);
            
            if (b)
                Log.i("CLIENT_APP", "bindService() Succeeded");
            else
                Log.i("CLIENT_APP", "bindService() was unsuccessful");
        }
    }

    private final ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myMusicService = MyAidlInterface.Stub.asInterface(service);
            
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myMusicService = null;
            
            isBound = false;
        }
    };
}