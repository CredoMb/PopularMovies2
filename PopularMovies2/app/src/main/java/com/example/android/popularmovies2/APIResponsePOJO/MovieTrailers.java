package com.example.android.popularmovies2.APIResponsePOJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailers {
    @SerializedName("id")
    public int movieId;

    @SerializedName("results")
    public List<Trailer> trailerList;

    public class Trailer {

        private final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("key")
        private String linkKey;

        @SerializedName("site")
        private String site;

        public String getTrailerUrl()
        {return TRAILER_BASE_URL +linkKey;}
    }

}
