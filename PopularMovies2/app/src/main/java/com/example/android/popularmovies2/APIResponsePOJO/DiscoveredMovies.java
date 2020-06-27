package com.example.android.popularmovies2.APIResponsePOJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * All the element inside this
 * class represent the json propery
 * we are interested in.
 *
 * P.s. We only created property
 * that we will need during this
 * project. The Json version could be
 * bigger than our model
 *
 */

public class DiscoveredMovies {

    @SerializedName("results")
    public List<AMovie> movieList;

    public class AMovie {

        /*The following variables represent the property of one
         Json movie object fetched from the API.*/
        @SerializedName("popularity")
        private Double popularity;
        @SerializedName("vote_count")
        private Integer voteCount;

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("id")
        private Integer id;

        @SerializedName("title")
        private String title;
        @SerializedName("vote_average")
        private float voteAverage;
        @SerializedName("overview")
        private String overview;
        @SerializedName("release_date")
        private String releaseDate;

        /* We have defined the necessary getters bellow. */

        public Integer getId() {
            return id;
        }

        public Integer getVoteCount() {
            return voteCount;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public Double getPopularity() {
            return popularity;
        }

        public String getTitle() {
            return title;
        }

        public float getVoteAverage() {
            return voteAverage;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

    }
}
