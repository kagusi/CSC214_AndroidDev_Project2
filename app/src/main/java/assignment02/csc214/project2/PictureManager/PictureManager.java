package assignment02.csc214.project2.PictureManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Kennedy on 4/4/2017.
 */

public class PictureManager {


    private File mPhotoFile;
    private String mFilePath;
    private Activity mActivity;

    public PictureManager(Activity activity)
    {
        this.mActivity = activity;
    }

    private File createImageFile(String username) throws IOException {

        String filename = username+ UUID.randomUUID().toString();
        File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                filename,
                ".jpg",
                storageDir
        );

        mFilePath = image.getAbsolutePath();
        return image;
    }

    public void takeAPhoto(String username) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile(username);
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mActivity.getApplicationContext(),
                        "assignment02.csc214.project2",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                mActivity.startActivityForResult(takePictureIntent, 0);
            }
        }
    }

    public Bitmap getScaledBitmap(String path, int width, int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int sampleSize = 1;
        if(srcHeight > height || srcWidth > width ) {
            if(srcWidth > srcHeight) {
                sampleSize = Math.round(srcHeight / height);
            }
            else {
                sampleSize = Math.round(srcWidth / width);
            }
        }
        BitmapFactory.Options scaledOptions =
                new BitmapFactory.Options();
        scaledOptions.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(path, scaledOptions);
    }


    public File getPhotoFile() {
        return mPhotoFile;
    }

    public void setPhotoFile(File photoFile) {
        mPhotoFile = photoFile;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }
}
