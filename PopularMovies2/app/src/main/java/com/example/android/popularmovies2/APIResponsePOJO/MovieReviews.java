package com.example.android.popularmovies2.APIResponsePOJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is a modelisation of the
 * json response that we should get from
 * the "review endpoint" of "the movie database"
 *
 * P.S. We only created property
 * that we will need during this
 * project.
 *
 * The Json version could be
 * bigger than our model.
 */

public class MovieReviews {

    @SerializedName("id")
    public int movieId;

    @SerializedName("results")
    public List<Review> reviewList;

    public static class Review implements Parcelable {

        /**
         * The following variables represent the property of one
         * Json Review object fetched from the API.
         *
         * P.S. We only created property
         * that we will need during this
         * project.
         *
         * The Json version could be
         * bigger than our model
         */
        @SerializedName("author")
        private String author;

        @SerializedName("content")
        private String content;

        @SerializedName("id")
        private String ReviewId;

        @SerializedName("url")
        private String ReviewUrl;


        public Review (String author, String content,String id,
                       String reviewUrl){
            this.author = author;
            this.content = content;
            this.ReviewId = id;
            this.ReviewUrl = reviewUrl;

        }

        // The constructor to build back the
        // object from a parcel.
        public Review(Parcel in) {

            author = in.readString();
            content = in.readString();
            ReviewId = in.readString();
            ReviewUrl = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

            parcel.writeString(author); // author
            parcel.writeString(content); // content
            parcel.writeString(ReviewId); // ReviewId
            parcel.writeString(ReviewUrl); // ReviewUrl
        }

        // The creator will be used to create the object from
        // a parcel. It will use one of the constructor
        // defined in this class.
        public static final Parcelable.Creator<Review> CREATOR =
                new Parcelable.Creator<Review>(){
                    @Override
                    public Review createFromParcel(Parcel parcel) {
                        return new Review(parcel);
                    }

                    @Override
                    public Review[] newArray(int i) {
                        return new Review[i];
                    }
                };
        /**
         * Bellow, We have set
         * the necessary getters
         */
        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }
}
