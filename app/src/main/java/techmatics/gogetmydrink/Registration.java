package techmatics.gogetmydrink;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataItemAsset;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by admin on 02/10/18.
 */

public class Registration extends Activity implements View.OnClickListener,WebApiResponseCallback {

    public static int SELECT_FILE=200;
    public static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=210;
    Button submit;
    ImageView back;
    AppController controller;
    EditText name,email,mobile;
    EditText servicingArea;
    TextView locationName;
    public static ArrayList<DeliveryLocationModel>deliveryList=new ArrayList<DeliveryLocationModel>();
    Dialog dialog;
    public final int permissionReadCamera = 1;
    boolean isImageCaptured=false;
    Dialog alertDialog;
    int apiCall=-1;
    int register=1;
    int updatePRofilePic=2;
    TextView  upload_profilePic;
    LinearLayout registration_view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        deliveryList.clear();
        initializeAll();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = ContextCompat.checkSelfPermission(Registration.this,
                    android.Manifest.permission.CAMERA);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Registration.this,
                        new String[]{android.Manifest.permission.CAMERA},
                        permissionReadCamera);
            }
            //
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permissionReadCamera: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(this, "Please provide read permission for using camera.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    public void initializeAll()
    {  controller=(AppController)getApplicationContext();
       submit=(Button)findViewById(R.id.submit);
        locationName=(TextView)findViewById(R.id.locationName);
         back=(ImageView)findViewById(R.id.back);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.emailId);
        mobile=(EditText)findViewById(R.id.mobile);
        servicingArea=(EditText)findViewById(R.id.autoCompleteTextView);
        upload_profilePic=(TextView)findViewById(R.id.upload_profilePic);
        registration_view=(LinearLayout)findViewById(R.id.registration_view);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        servicingArea.setOnClickListener(this);
        upload_profilePic.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                if(isAllFieldsValidated())
                {
                   if(Common.isNetworkAvailable(Registration.this))
                   { dialog=Common.showPogress(Registration.this);
                   apiCall=register;
                       controller.getWebApiCall().postData(Common.registartion,WebApiCall.JSON,getRegistrationJson().toString(),this);
                   }
                }
                break;
            case R.id.autoCompleteTextView:
                startActivityForResult(new Intent(Registration.this,LocationSearch.class),2);
                break;
            case R.id.upload_profilePic:
                Common.selectImageDialog(Registration.this);
                break;
        }

    }

    public JSONObject getRegistrationJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("FIRST_NAME", name.getText().toString());
            jsonObject.put("LAST_NAME", "");
            jsonObject.put("EMAILID", email.getText().toString());
            jsonObject.put("MOBILE", mobile.getText().toString());
            jsonObject.put("CATEGORY", "");
            jsonObject.put("RANK", 0);
            jsonObject.put("IMAGE", "");
            for (int i = 0; i < deliveryList.size(); i++) {
                DeliveryLocationModel model = deliveryList.get(i);
                JSONObject deliverylocation = new JSONObject();
                deliverylocation.put("LOCATIONNAME", model.getLocationName());
                deliverylocation.put("LAT", model.getLat());
                deliverylocation.put("LON", model.getLon());
                jsonArray.put(i, deliverylocation);
            }
            jsonObject.put("DELIVERY_AREA", jsonArray);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    public boolean isAllFieldsValidated()
    {boolean status=false;
        if(name.getText().length()>0)
        {
            if(mobile.getText().length()>0)
            {
                if(mobile.getText().length()==10)
                {
                    if(email.getText().length()>0)
                    {
                        if(email.getText().toString().contains("@"))
                        {
                            if(deliveryList.size()>0)
                            {
                               status=true;
                            }else{
                                Toast.makeText(this,"Please choose servicing area",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(this,"Please enter valid Email Id",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"Please enter Email Id",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this,"Please enter valid mobile number",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Please enter Mobile number",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show();
        }
        return status;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Common.tempPath = Common.imageUri.getPath();
                isImageCaptured = true;
                showDialog();
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                Common.tempPath = c.getString(columnIndex);
                c.close();
                isImageCaptured = true;
                showDialog();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                // Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, options));

            } else {
                Toast.makeText(this, " This Image cannot be stored .please try with some other Image. ", Toast.LENGTH_SHORT).show();
            }

        }else {
            if ((requestCode == 2) & (resultCode == RESULT_OK)) {
                if (locationName.getText().length() == 0) {
                    locationName.setText(data.getStringExtra("Location"));
                } else {
                    String text = locationName.getText().toString();
                    text = text + ",\n" + data.getStringExtra("Location");
                    locationName.setText(text);
                }
            }
        }
    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this)
                .setTitle("Update Profile Pic");

        final FrameLayout frameView = new FrameLayout(Registration.this);
        builder.setView(frameView);

        alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.uploadprofilepic, frameView);
        ImageView image = (ImageView) dialoglayout.findViewById(R.id.pic);
        final Button upload = (Button) dialoglayout.findViewById(R.id.upload);

        // Uri uri = Uri.fromFile();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath,options);
        File file =new File(Common.tempPath);
        final String name=file.getName();

        image.setImageBitmap(myBitmap);
//        Picasso.with(Profile.this).load(new File(Common.tempPath))
//                .resize(300, 300).centerCrop().into(image);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageCaptured == true) {
                    dialog = Common.showPogress(Registration.this);
                    apiCall = updatePRofilePic;
                    controller.getWebApiCall().postImage(Common.getUploadProfilePicUrl(email.getText().toString(),name),WebApiCall.mediaTypePlain ,Common.getBase64FromFile(Common.tempPath),Registration.this);

                }

            }
        });
        alertDialog.show();
    }
    @Override
    public void onSucess(String value) {
        {
            dialog.cancel();
        }
        if(apiCall==register) {
            if (value.contains("Successfully Registered")) {
                Common.showToast(Registration.this, "Registered Sucessfully");
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Common.selectImageDialog(Registration.this);
                       registration_view.setVisibility(View.GONE);
                       upload_profilePic.setVisibility(View.VISIBLE);
                   }
               });

            }else{
                Common.showToast(Registration.this, value);
            }
        }else{
            if(value.contains("Picture Upload Successfully"))
            {
                finish();
                Common.showToast(Registration.this, "profile Updated sucessfully.");
            }else{
                Common.showToast(Registration.this, value );
            }
        }
    }

    @Override
    public void onError(String value) {
        {
            dialog.cancel();
        }

        if(value.contains("Bad Request")) {
            if(apiCall==register) {
                Common.showToast(Registration.this, value);
            }else{
                Common.showToast(Registration.this, value);
            }
        }else{
            Common.showToast(Registration.this, value);
        }
    }


}
