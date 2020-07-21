package com.example.android.popularmovies2.APIResponsePOJO;

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
 * bigger than our model
 * */

public class MovieReviews {

/*    @SerializedName("id")
    public int movieId;*/

    @SerializedName("results")
    public List<Review> reviewList;

    public class Review{
        @SerializedName("author")
        private String author;

        @SerializedName("content")
        private String content;

        @SerializedName("id")
        private String ReviewId;

        @SerializedName("url")
        private String ReviewUrl;

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }
}
