package com.example.android.popularmovies2.APIResponsePOJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is a modelisation of the
 * json response that we should get from
 * the "discover endpoint" of "the movie database"
 *
 * P.s. We only created property
 * that we will need during this
 * project.
 *
 * The Json version could be
 * bigger than our model
 *
 */

public class DiscoveredMovies {

    @SerializedName("results")
    public List<Movie> movieList;

    public class Movie {
        // This will be used to build the complete URL for the
        // poster image
       private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original";

       // The following variables represent
       // the index limit of the year sub string
       // inside of the release date text.
       private final int MOVIE_YEAR_START_INDEX = 0;
       private final int MOVIE_YEAR_END_INDEX = 4;

        /**
         * Except from the "Year" variable
         * The following variables represent the property of one
         * Json movie object fetched from the API.
         * */
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

        private String year;

        public boolean isFavorite;

        /**
         *  We have defined the necessary
         *  getters bellow.
         *  */

        public Integer getId() {
            return id;
        }

        public Integer getVoteCount() {
            return voteCount;
        }

        public String getPosterPath() {
            return  IMAGE_BASE_URL + posterPath;
        }

        public String getYear() {
            return releaseDate.substring(MOVIE_YEAR_START_INDEX,MOVIE_YEAR_END_INDEX);
        }

        public String getOverview() {
            return overview;
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


        /**
         *  Next, we have the variables that
         *  represent the POJO of the Data
         *  fetched from other endpoints of the API.
         *
         *  All those datas are movie's
         *  complementary information
         *
         * */
        private MovieCredit movieCredit;
        private MovieDetail movieDetail;
        private MovieReviews movieReviews;
        private MovieTrailers movieTrailers;

        /**Getters for the POJO*/
        public MovieCredit getMovieCredit() {
            return movieCredit;
        }

        public MovieDetail getMovieDetail() {
            return movieDetail;
        }

        public MovieReviews getMovieReviews() {
            return movieReviews;
        }

        public MovieTrailers getMovieTrailers() {
            return movieTrailers;
        }

        /**Setters for the POJO*/
        public void setMovieCredit(MovieCredit movieCredit) {
            this.movieCredit = movieCredit;
        }

        public void setMovieDetails(MovieDetail movieDetail) {
            this.movieDetail = movieDetail;
        }

        public void setMovieReviews(MovieReviews movieReviews) {
            this.movieReviews = movieReviews;
        }

        public void setMovieTrailers(MovieTrailers movieTrailers) {
            this.movieTrailers = movieTrailers;
        }
    }
}
