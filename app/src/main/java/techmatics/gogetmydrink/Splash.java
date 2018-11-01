package techmatics.gogetmydrink;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash  extends Activity implements View.OnClickListener {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    public static final int MULTIPLE_PERMISSIONS = 10;
    TextView register;
    Button home;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_splash);
        register=(TextView)findViewById(R.id.register);
        home=(Button)findViewById(R.id.home);
        register.setVisibility(View.GONE);
        home.setVisibility(View.GONE);
        register.setOnClickListener(this);
        home.setOnClickListener(this);
        int permissionCheck = ContextCompat.checkSelfPermission(Splash.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if(!checkPermissions()){
            } else {
                runThread();
            }
        } else {
            runThread();
        }
    }
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (int i=0;i<PERMISSIONS.length;i++) {
            result = ContextCompat.checkSelfPermission(this,PERMISSIONS[i]);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(PERMISSIONS[i]);
            }
        }
        String [] permissionList=new String[listPermissionsNeeded.size()];
        for(int i=0;i<listPermissionsNeeded.size();i++)
        {
            permissionList[i]=listPermissionsNeeded.get(i);
        }
        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    public void runThread()
    {
        register.setVisibility(View.VISIBLE);
        home.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.home:
                showPopUp();
                break;
            case R.id.register:
                startActivity(new Intent(this,Registration.class));
                break;
        }
    }

    public void showPopUp()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View category=(View)dialog.findViewById(R.id.click) ;
        category.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.cancel();
                                                }
                                            }, 500);
                                            startActivity(new Intent(Splash.this, Dashboard.class));
                                        }
                                    });
        dialog.show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    runThread();
                } else {
                    checkPermissions();
                }
                return;
            }

        }
    }
}
