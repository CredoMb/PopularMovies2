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
 */

public class MovieCredit {

    @SerializedName("id")
    public int id;

    @SerializedName("cast")
    public List<Cast> castList;

    @SerializedName("crew")
    public List<Crew> crewList;

    // Will be the default value for all the
    // movies that don't have directors.
    private final String UNKNOWN_DIRECTOR = "Unknown Director";

    public class Cast {

        /**
         * The following variables represent the property of one
         * Json Cast object fetched from the API.
         *
         * P.S. We only created property
         * that we will need during this
         * project.
         *
         * The Json version could be
         * bigger than our model
         */
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

        /**
         * The following variables represent the property of one
         * Json Crew object fetched from the API.
         */
        @SerializedName("department")
        private String department;
        @SerializedName("job")
        private String job;
        @SerializedName("name")
        private String name;

        /**
         * Bellow, We have set
         * the necessary getters
         **/
        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }
    }

    public String getDirectorName() {

        // Iterate through the movie crew list.
        // If the job of a movie crew member is "Director",
        // then return his name.
        for (MovieCredit.Crew crew : crewList) {

            if (crew.getJob().equals("Director")) {
                return crew.getName();
            }
        }
        return UNKNOWN_DIRECTOR;
    }
}
