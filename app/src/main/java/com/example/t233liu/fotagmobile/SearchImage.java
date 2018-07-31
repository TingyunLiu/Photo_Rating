package com.example.t233liu.fotagmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;


public class SearchImage extends LinearLayout implements Observer {
    private ImageCollectionModel collectionModel;
    private AlertDialog dialog;

    public SearchImage(final Context context, final ImageCollectionModel imageCollectionModel) {
        super(context);

        // Get the XML view description and "inflate" it into the display (like rendering)
        View.inflate(context, R.layout.search_image_layout, this);

        this.collectionModel = imageCollectionModel;
        this.collectionModel.addObserver(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText imageURL = (EditText)findViewById(R.id.editText);
        ImageButton search = (ImageButton)findViewById(R.id.url_search_button);
        builder.setView(this);
        this.dialog = builder.create();
        this.dialog.show();

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        if (image == null) { // invalid url
                            Toast.makeText(context, "Invalid URL", Toast.LENGTH_SHORT).show();
                        } else {
                            collectionModel.addImage(0, imageURL.getText().toString());
                            Toast.makeText(context, "Load Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }

                new DownloadImageTask().execute(imageURL.getText().toString());

            }

        });
    }

    public AlertDialog getDialog() { return this.dialog; }

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

    }
}
