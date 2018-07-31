package com.example.t233liu.fotagmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.content.Intent;

import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

public class ImageSingleView extends LinearLayout implements Observer {

    private ImageModel imageModel;
    private RatingBar ratingBar;
    private ImageView imageView;

    public ImageSingleView(final Context context, final ImageModel imageModel) {
        super(context);

        // Get the XML view description and "inflate" it into the display (like rendering)
        View.inflate(context, R.layout.single_image_layout, this);

        this.imageModel = imageModel;
        this.imageModel.addObserver(this);

        final String imageName = this.imageModel.getImageName();

        this.imageView = (ImageView)findViewById(R.id.imageView);
        if (imageName == null) { // from url, Bitmap
            loadImageBitmap();
        } else {
            this.imageView.setImageResource(getImageId(context, imageName));
        }
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EnlargedImageActivity.class);
                intent.putExtra("image_name", imageName);
                if (imageName == null) {
                    ImageCollectionModel.getInstance().setImageToEnlarge(imageModel.getUrl());
                }
                context.startActivity(intent);
            }
        });

        this.ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        this.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                imageModel.setRating((int)v);
            }
        });
    }

    public static int getImageId(Context context, String imageName) {
        int id = context.getResources().getIdentifier(imageName, "drawable",context.getPackageName());
        return id;
    }

    private void loadImageBitmap() {
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

        new DownloadImageTask().execute(this.imageModel.getUrl());
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        this.ratingBar.setRating(this.imageModel.getRating());
    }
}
