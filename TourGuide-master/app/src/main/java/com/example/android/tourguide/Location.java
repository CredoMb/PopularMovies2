package com.example.android.tourguide;


public class Location {

    // The id of the image's location
    private int mImageId;

    // Location's name that will stand as the title
    private String mTitle;

    //  Brief description of the location
    private String mDescription;

    // Rating bar showing people's appreciation of the location
    private float mRating;

    // Number of people that reviewed the location
    private int mNbReviewers;

    // The link to the location's website
    private String mLinkToWebsite;

    /**
     * Constructor used by the LocationBuilder
     */

    public Location(LocationBuilder builder) {

        mImageId = builder.imageId;
        mTitle = builder.title;
        mDescription = builder.description;
        mRating = builder.rating;
        mNbReviewers = builder.nbReviewers;
        mLinkToWebsite = builder.linkToWebsite;
    }

    /**
     * As the Location class possesses many attributes, we'll create a builder pattern
     * for an easy object instanciation
     */

    public static class LocationBuilder {

        // The id of the image's location
        private int imageId;

        // Location's name that will stand as the title
        private String title;

        //  Brief description of the location
        private String description;

        // Rating bar showing people's appreciation of the location
        private float rating;

        // Number of people that reviewed the location
        private int nbReviewers;

        // The link to the location's website
        private String linkToWebsite;

        public LocationBuilder() {
        }

        public LocationBuilder setImageId(int img) {
            imageId = img;
            return this;
        }

        public LocationBuilder setTitle(String t) {
            title = t;
            return this;
        }


        public LocationBuilder setDescription(String desc) {
            description = desc;
            return this;
        }


        public LocationBuilder setRating(float rating) {

            this.rating = rating;
            return this;
        }

        public LocationBuilder setNbReviewers(int reviews) {
            nbReviewers = reviews;
            return this;
        }

        public LocationBuilder setLinkToWebSite(String link) {
            linkToWebsite = link;
            return this;
        }

        // This method helps us build the Location object
        public Location build() {
            return new Location(this);
        }
    }

    /**
     * Defining getters for the Location Class
     */

    public int getImageId() {
        return mImageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public float getRating() {
        return mRating;
    }

    public int getNbReviewers() {
        return mNbReviewers;
    }

    public String getLinkToWebsite() {
        return mLinkToWebsite;
    }

}
