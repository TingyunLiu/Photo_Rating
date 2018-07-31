package com.example.t233liu.fotagmobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;

import java.io.InputStream;

public class EnlargedImageActivity extends AppCompatActivity {

    /**
     * OnCreate
     * -- Called when application is initially launched.
     *    @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle.html">Android LifeCycle</a>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load base UI layout from resources.
        setContentView(R.layout.enlarged_image_activity);

        final EnlargedImageActivity enlargedImageActivity = this;
        final ImageView imageView = (ImageView)findViewById(R.id.enlarged_image);
        Intent intent = getIntent();
        String imageName = intent.getStringExtra("image_name");

        if (imageName == null) { // url loaded image, bitmap

            /**
             * Reference: https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
             */
            class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

                public DownloadImageTask() { }

                protected Bitmap doInBackground(String... urls) {
                    String url = urls[0];
                    Bitmap image = null;
                    try {
                        InputStream in = new java.net.URL(url).openStream();
                        image = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return image;
                }

                protected void onPostExecute(Bitmap image) {
                    imageView.setImageBitmap(image);
                }
            }

            new DownloadImageTask().execute(ImageCollectionModel.getInstance().getImageToEnlarge());

        } else {
            imageView.setImageResource(ImageSingleView.getImageId(enlargedImageActivity, imageName));
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Save and Restore State
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Save the state of the application
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
