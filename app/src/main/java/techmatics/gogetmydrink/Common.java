package techmatics.gogetmydrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ashish.kumar on 18-10-2018.
 */

public class Common {
    public static String baseUrl = "http://115.248.119.225/tsys/api/";
    public static String registartion = baseUrl + "Registration/RegisterUser";
    public static String uploadProfilePic = baseUrl + "Upload?";
    public static String getUserList = baseUrl + "Location/GetNearByUsers?";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static Uri imageUri = null;
    public static String sdCardPath;
    public static String folderName;
    public static String tempPath;

    public static String getUserList(String lat, String lon) {
        return getUserList + "Latitude=" + lat + "&Longitude=" + lon;
    }
    public static String getUploadProfilePicUrl(String emailId,String format)
    {
        return uploadProfilePic+"emailid="+emailId+"&formate="+format;
    }

    public static Dialog showPogress(Activity act) {
        final Dialog dialog = new Dialog(act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }

    public static void showToast(final Activity act, final String message) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static void makeFolder(String path, String folder) {
        File directory = new File(path, folder);
        if (directory.exists() == false) {
            directory.mkdirs();
        }
    }
    public static  String getBase64FromFile(String path)
    {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        byte[] baat = null;
        String encodeString = null;
        try
        {
            bmp = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baat = baos.toByteArray();
            encodeString = Base64.encodeToString(baat, Base64.DEFAULT);
            Log.d("length : ", ""+encodeString.length());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return encodeString;
    }

    public static String getImageBase64(String imageUrl) {

        // File imageFile = new File(imageUrl);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
//        byte[] byteArray = getImageByteArray(imageFile);
//        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return temp;
    }

    public static String getCompleteAddressString(final Activity act, final double LATITUDE, final double LONGITUDE) {
        List<Address> list;
        String strAdd = null;
        Geocoder geocoder = new Geocoder(act, Locale.getDefault());
        try {
            list = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            for (int n = 0; n <= list.get(0).getMaxAddressLineIndex() - 2; n++) {
                strAdd += list.get(0).getAddressLine(n) + ", ";
            }
            strAdd = list.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;

    }

    /* * camera module popup
   *************************************/
    public static void selectImageDialog(final Activity act) {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle("Profile Pic");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (isDeviceSupportCamera(act)) {
                        captureImage(act);
                    } else {
                        Toast.makeText(act, "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
                    }
                } else if (items[item].equals("Choose from gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    act.startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            Registration.SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Checking device has camera hardware or not
     */
    private static boolean isDeviceSupportCamera(Activity act) {
        if (act.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static void captureImage(Activity act) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Common.imageUri = getOutputMediaFileUri(Common.MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Common.imageUri);

        // start the image capture Intent
        try {
            act.startActivityForResult(intent, Registration.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public static Uri getOutputMediaFileUri(int type) {
        File tempFile = getOutputMediaFile(type);
        Uri uri = Uri.fromFile(tempFile);
        return uri;
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Common.sdCardPath);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Common.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        File files = mediaFile;
        return mediaFile;
    }
}



