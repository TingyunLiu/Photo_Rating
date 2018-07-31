package com.example.t233liu.fotagmobile;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ImageCollectionModel extends Observable {

    // Create static instance of this mModel
    private static final ImageCollectionModel ourInstance = new ImageCollectionModel();
    static ImageCollectionModel getInstance()
    {
        return ourInstance;
    }

    // Default images when clicking upload
    private static final String[] DefaultImages = {
            "blue_pond",
            "brushes",
            "bristle_grass",
            "beach",
            "el_capitan",
            "elephant",
            "frog",
            "high_sierra",
            "lion",
            "yosemite",
            "cat",
            "dog"
    };

    // Private Variables
    private java.util.List<Observer> observers;
    private java.util.List<ImageModel> images;
    private int rating;
    private boolean addingNewImage;
    private String imageToEnlarge;

    /**
     * Model Constructor:
     * - Init member variables
     */
    public ImageCollectionModel() {
        this.addingNewImage = false;
        this.observers = new ArrayList();
        this.images = new ArrayList();
        this.rating = 0;
        this.imageToEnlarge = null;
    }

    public int numImages() { return images.size(); }

    public boolean isAddingNewImage() { return this.addingNewImage; }

    public ImageModel getImageAt(int index) { return this.images.get(index); }

    public int getRating() { return this.rating; }

    public void setRating(int rate) {
        this.rating = rate;
        this.notifyObservers();
    }

    public void setImageToEnlarge(String image) { this.imageToEnlarge = image; }

    public String getImageToEnlarge() { return this.imageToEnlarge; }

    public void addImage(String imageName, int rate) {
        ImageModel imageModel = new ImageModel(this, imageName, rate);
        this.images.add(imageModel);
        this.addingNewImage = true;
        this.notifyObservers();
        this.addingNewImage = false;
    }

    public void addImage(int rate, String url) {
        ImageModel imageModel = new ImageModel(this, rate, url);
        this.images.add(imageModel);
        this.addingNewImage = true;
        this.notifyObservers();
        this.addingNewImage = false;
        // this is to layout correctly
        this.notifyObservers();
    }

    public void loadDefaultImages() {
        for (String image: DefaultImages) {
            this.addImage(image, 0);
        }
        this.notifyObservers();
    }

    public void reloadImages(ArrayList<String> imageNames, ArrayList<String> URLs,
                             ArrayList<Integer> imageRatings) {
        for (int i = 0; i < imageNames.size(); i++) {
            if (imageNames.get(i) == null) { // loaded from url, use bitmap
                this.addImage(imageRatings.get(i), URLs.get(i));
            } else {
                this.addImage(imageNames.get(i), imageRatings.get(i));
            }
            this.getImageAt(i).notifyObservers();
        }
        this.notifyObservers();
    }

    public ArrayList<Integer> getImageRatings() {
        ArrayList<Integer> ratings = new ArrayList();
        for (ImageModel image: this.images) {
            ratings.add(image.getRating());
        }
        return ratings;
    }

    public ArrayList<String> getImageNames() {
        ArrayList<String> ImageNames = new ArrayList();
        for (ImageModel image: this.images) {
            ImageNames.add(image.getImageName());
        }
        return ImageNames;
    }

    public ArrayList<String> getURLs() {
        ArrayList<String> URLs = new ArrayList();
        for (ImageModel image: this.images) {
            URLs.add(image.getUrl());
        }
        return URLs;
    }

    public void deleteAll() {
        this.images.clear();
        this.rating = 0;
        this.notifyObservers();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Observable Methods
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Helper method to make it easier to initialize all observers
     */
    public void initObservers() {
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     *
     * @param o the observer to be deleted.
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        this.observers.remove(o);
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    @Override
    public synchronized void addObserver(Observer o) {
        this.observers.add(o);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Override
    public synchronized void deleteObservers() {
        this.observers.clear();
        for (Observer ob: this.observers) {
            this.deleteObserver(ob);
        }
    }

    /**
     * Gives number of observers
     */
    public int numObservers() {
        return observers.size();
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this, null);
        }
    }
}
