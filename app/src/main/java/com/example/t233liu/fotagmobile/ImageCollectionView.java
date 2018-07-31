package com.example.t233liu.fotagmobile;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ImageCollectionView extends GridLayout implements Observer {
    private ImageCollectionModel collectionModel;
    private java.util.List<ImageSingleView> images;

    public ImageCollectionView(Context context, final ImageCollectionModel imageCollectionModel) {
        super(context);

        // Get the XML view description and "inflate" it into the display (like rendering)
        View.inflate(context, R.layout.collection_image_layout, this);

        this.collectionModel = imageCollectionModel;
        this.collectionModel.addObserver(this);
        this.images = new ArrayList();
    }

    private void addNewImageView(ImageModel imageModel) {
        ImageSingleView imageSingleView = new ImageSingleView(this.getContext(), imageModel);
        this.images.add(imageSingleView);

        GridLayout layout = (GridLayout)findViewById(R.id.collection_image_layout);

        int row = this.images.indexOf(imageSingleView) / layout.getColumnCount();
        int column = this.images.indexOf(imageSingleView) % layout.getColumnCount();
        Spec rowSpec = GridLayout.spec(row);
        Spec columnSpec = GridLayout.spec(column);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.leftMargin = 30;
        params.rightMargin = 30;

        layout.addView(imageSingleView, params);
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
        GridLayout layout = (GridLayout)findViewById(R.id.collection_image_layout);

        if (collectionModel.numImages() == 0) {
            this.images.clear();
            layout.removeAllViews();
        } else if (collectionModel.isAddingNewImage()) {
            ImageModel imageModel = this.collectionModel.getImageAt(this.collectionModel.numImages()-1);
            this.addNewImageView(imageModel);
        } else {
            layout.removeAllViews();
            int numDisplayableImages = 0;
            for (int i = 0; i < collectionModel.numImages(); i++) {
                ImageModel imageModel = collectionModel.getImageAt(i);
                /*
                if (imageModel.getRating() < collectionModel.getRating()) {
                    this.images.get(i).setVisibility(View.GONE);
                } else {
                    this.images.get(i).setVisibility(View.VISIBLE);
                }
                */
                if (imageModel.getRating() >= collectionModel.getRating()) {
                    int row = numDisplayableImages / layout.getColumnCount();
                    int column = numDisplayableImages % layout.getColumnCount();
                    Spec rowSpec = GridLayout.spec(row);
                    Spec columnSpec = GridLayout.spec(column);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                    params.leftMargin = 30;
                    params.rightMargin = 30;

                    layout.addView(this.images.get(i), params);

                    numDisplayableImages++;
                }
            }
        }
    }
}
