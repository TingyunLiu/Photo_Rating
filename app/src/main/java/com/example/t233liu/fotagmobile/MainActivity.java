package com.example.t233liu.fotagmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Private Vars
    private ImageCollectionModel imageCollectionModel;

    private SearchImage searchImage;

    private static final String MODEL_FILTER = "collectionModelFilter";
    private static final String IMAGE_RATINGS = "imageModelRatingList";
    private static final String IMAGE_NAMES = "imageModelImageNameList";
    private static final String URLS = "imageBitmapsURLs";


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
        setContentView(R.layout.activity_main);

        // Init Model
        this.imageCollectionModel = new ImageCollectionModel();

        // initially set to null
        this.searchImage = null;

        ////////////////////////////////////////
        // Setup Views
        ////////////////////////////////////////
        ToolbarView toolbarView = new ToolbarView(this, imageCollectionModel);
        ViewGroup v1 = (ViewGroup)findViewById(R.id.mainactivity_1);
        v1.addView(toolbarView);

        final ImageCollectionView imageCollectionView = new ImageCollectionView(this, imageCollectionModel);
        ViewGroup v2 = (ViewGroup)findViewById(R.id.mainactivity_2);
        v2.addView(imageCollectionView);

        final Context context = this;
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImage = new SearchImage(context, imageCollectionModel);
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
        outState.putInt(MODEL_FILTER, this.imageCollectionModel.getRating());
        outState.putIntegerArrayList(IMAGE_RATINGS, this.imageCollectionModel.getImageRatings());
        outState.putStringArrayList(IMAGE_NAMES, this.imageCollectionModel.getImageNames());
        outState.putStringArrayList(URLS, this.imageCollectionModel.getURLs());
        if (this.searchImage != null) this.searchImage.getDialog().cancel();
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

        if (savedInstanceState != null) {
            this.imageCollectionModel.setRating(savedInstanceState.getInt(MODEL_FILTER));
            this.imageCollectionModel.reloadImages(savedInstanceState.getStringArrayList(IMAGE_NAMES),
                    savedInstanceState.getStringArrayList(URLS),
                    savedInstanceState.getIntegerArrayList(IMAGE_RATINGS));
        }
    }
}
