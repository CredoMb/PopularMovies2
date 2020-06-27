package com.example.android.popularmovies2.APIResponsePOJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is a modelisation of the
 * json response that we should get from
 * the "credit endpoint" of "the movie database".
 *
 * P.S. We only created property
 * that we will need during this
 * project.
 *
 * The Json version could be
 * bigger than our model
 * */

public class MovieCredit {
    @SerializedName("id")
    public int id;

    @SerializedName("cast")
    public List<Cast> castList;

    @SerializedName("crew")
    public List<Crew> crewList;

    // What do we need from the cast and from the crew ?
    // Keep working, your thoughts are illusions, remember that !
    public class Cast {

        // The following variables represent the property of one
        // Json movie object fetched from the API.
        @SerializedName("id")
        private Integer castId;

        @SerializedName("character")
        private String character;

        @SerializedName("name")
        private String name;

        // This is the only getter we need so far,
        // because we'll only use the actor's name.
        public String getName() {
            return name;
        }
    }

    public class Crew {

        // The following variables represent the property of one
        // Json movie object fetched from the API.
        @SerializedName("department")
        private String department;

        @SerializedName("job")
        private String job;

        @SerializedName("name")
        private String name;


        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public String getJob() {
            return job;
        }
    }
}
