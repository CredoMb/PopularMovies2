package com.example.android.popularmovies2.APIResponsePOJO;

import com.google.gson.annotations.SerializedName;

/**
 * This is a modelisation of the
 * json response that we should get from
 * the "detail endpoint" of "the movie database"
 *
 * P.S. We only created property
 * that we will need during this
 * project.
 *
 * The Json version could be
 * bigger than our model
 * */
public class MovieDetail {

    /**
     * The following variables represent the property of one
     * Json Detail object fetched from the API.
     *
     * P.S. We only created property
     * that we will need during this
     * project.
     *
     * The Json version could be
     * bigger than our model
     */
    @SerializedName("original_title")
    public String title;

    @SerializedName("runtime")
    public int runtime;

    /**
     *  The following method will modify the movie length format.
     *  By default, the length is represented in minutes.
     *  A 2h00 movie would have a default runtime of "120". Our
     *  goal is to change it from "120" to "2h00"
     *
     *  */
    public String getFormattedLength() {

        String formatedLength = "";

        if (runtime > 60 || runtime == 60) {
            formatedLength = String.valueOf(runtime / 60) + "h"
                    + String.format("%02d", runtime % 60);
        } else {
            formatedLength = "0h" + String.format("%02d", runtime);
        }
        return formatedLength;
    }
}