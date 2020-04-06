package com.example.android.tourguide;

import java.util.Random;

public final class ReviewUtils {
    // The minimum rating any place could get
    private static float MIN_RATING = 3.0f;
    // The maximum rating any place could get
    private static float MAX_RATING = 4.9f;
    // The minimum number of reviews of one given place
    private static int MIN_REVIEWERS = 100;
    // The maximum number of reviews of one given place
    private static int MAX_REVIEWERS = 1500;

    /**
     * This function generate random float between 3 and 4.9
     * It will be used to generate a random rating mark for every destination
     **/

    public static float randomRating() {
        Random r = new Random();
        float randomFloat = MIN_RATING + r.nextFloat() * (MAX_RATING - MIN_RATING);

        return randomFloat;
    }

    /**
     * This function generate random int between 100 and 1500
     * It will be used to generate a random "number of reviewers" for every destination
     **/

    public static int randomReviewers() {
        Random r = new Random();
        int randomInt = r.nextInt(MAX_REVIEWERS) + MIN_REVIEWERS;

        return randomInt;
    }
}
