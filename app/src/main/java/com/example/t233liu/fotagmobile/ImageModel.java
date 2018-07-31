package com.example.t233liu.fotagmobile;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ImageModel extends Observable {
    // Private Variables
    private ImageCollectionModel collectionModel;
    private java.util.List<Observer> observers;
    private int rating;
    private String imageName;
    private String url;

    /**
     * Model Constructor:
     * - Init member variables
     */
    public ImageModel(ImageCollectionModel collectionModel, String imageName, int rate) {
        this.collectionModel = collectionModel;
        this.observers = new ArrayList();
        this.imageName = imageName;
        this.url = null;
        this.rating = rate;
    }

    public ImageModel(ImageCollectionModel collectionModel, int rate, String url) {
        this.collectionModel = collectionModel;
        this.observers = new ArrayList();
        this.imageName = null;
        this.url = url;
        this.rating = rate;
    }

    public String getImageName() { return this.imageName; }

    public String getUrl() { return this.url; }

    public int getRating() { return this.rating; }

    public void setRating(int rate) {
        this.rating = rate;
        if (this.rating < this.collectionModel.getRating()) {
            this.collectionModel.notifyObservers();
        }
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
        for (Observer ob: this.observers) {
            deleteObserver(ob);
        }
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
