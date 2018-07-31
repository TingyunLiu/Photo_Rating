package com.example.t233liu.fotagmobile;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import java.util.Observable;
import java.util.Observer;

public class ToolbarView extends RelativeLayout implements Observer {
    private ImageCollectionModel collectionModel;

    private RatingBar filter;

    public ToolbarView(Context context, final ImageCollectionModel imageCollectionModel) {
        super(context);

        // Get the XML view description and "inflate" it into the display (like rendering)
        View.inflate(context, R.layout.toolbar_layout, this);

        this.collectionModel = imageCollectionModel;
        this.collectionModel.addObserver(this);

        ImageButton upload = (ImageButton)findViewById(R.id.upload_button);
        upload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCollectionModel.loadDefaultImages();
            }
        });

        ImageButton clear = (ImageButton)findViewById(R.id.clear_button);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCollectionModel.deleteAll();
            }
        });

        this.filter = (RatingBar)findViewById(R.id.filter);
        filter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                imageCollectionModel.setRating((int)v);
            }
        });

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
        this.filter.setRating(this.collectionModel.getRating());
    }

}
