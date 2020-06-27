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

    @SerializedName("original_title")
    String title;

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
        // Format the movie length (duration), based on
        // the total length
        if (runtime > 60 || runtime == 60) {
            formatedLength = String.valueOf(runtime / 60) + "h"
                    + String.format("%02d", runtime % 60);
        } else {
            formatedLength = "0h" + String.format("%02d", runtime);
        }
        return formatedLength;
    }
}