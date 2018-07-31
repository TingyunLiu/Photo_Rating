# Photo_Rating

#### Development Environment
    Machine tested: Mac
    Build environment: Android Studio 3.1 (built-in Java 8)
    AVD tested: Pixel
    API: 26
    

#### Design
    Framework: MVC (two models and multiple views, where view and controller are combined)
        Whenever a sequence of changes performed on model by each view (through many setters), the
        model notify each observers associated with the model (by calling each observer's update).
        Then each view updates its most recent view to the display.
    Layout used: GridLayout, ScrollView, LinearLayout, ConstraintLayout, RelativeLayout
    Widgets used: ImageView, ImageButton, RatingBar, EditText, AlertDialog, Toast


#### Description
    A android application that users can upload and rate a set of images.
    Initially, the window is empty, users can upload 10 default images by clicking the upload icon
    at the leftmost position of the toolbar. Loaded image thumbnails are displayed in a single
    column if you hold the device vertically while image thumbnails will be displayed in two columns
    if you hold the device horizontally(landscape). In either view, users may click clear button
    besides the load button to delete all images. Users can touch on an image to enlarge the image
    in a separate window (separate activity in the implementation), touching the image again would
    dismiss it, returning to the thumbnails view. Each image is kept aspect ratio with respect to
    the image original ratio. In addition, there is a rating mechanism for users to rate each image,
    ranging from 1 star to 5 stars and initially no star. Users can also touch/slide stars at the
    upper right corner of the window to filter a set of images which have minimum rating. By sliding
    rating stars all the way to the left, users are able to clear a rating for each specific image
    or remove the display filter.
  
  
#### Enhancement
    There is a floating search button at the right bottom corner, which will prompts the user to
    enter a URL of an image on the web. If the image exists, it will be loaded and added to the
    bottom of the list. The user is able to search multiple times.


