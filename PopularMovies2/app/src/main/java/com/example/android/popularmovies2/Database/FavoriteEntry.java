package com.example.android.popularmovies2.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "favorite")
public class FavoriteEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String synopsis;

    // The following variable will
    // represent store the movie id
    // gotten from theMovieDataBase API.
    private int tmdbId;

    private float rating;
    private int numberOfTrailers;

    @ColumnInfo(name = "release_date")
    private String releaseDate;
    private byte[] posterImage;

    @Ignore
    public FavoriteEntry(String synopsis, int tmdbId, float rating,
                         int numberOfTrailers, String releaseDate, byte[] posterImage) {

        this.synopsis = synopsis;
        this.tmdbId = tmdbId;
        this.rating = rating;
        this.numberOfTrailers = numberOfTrailers;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }

    public FavoriteEntry(int id, String synopsis, int tmdbId, float rating,
                         int numberOfTrailers, String releaseDate, byte[] posterImage) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.synopsis = synopsis;
        this.rating = rating;
        this.numberOfTrailers = numberOfTrailers;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }

    /**
     * Getters
     */

    public int getId() {
        return id;
    }

    public byte[] getPosterImage() {
        return posterImage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public float getRating() {
        return rating;
    }

    public int getNumberOfTrailers() {
        return numberOfTrailers;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Setters
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The setters bellow were commented
     * because they are not used in the current version
     * of the app but could be necessary in the
     * next version.
     *
     * */
    /*
    public void setPosterImage(byte[] posterImage) {
        this.posterImage = posterImage;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setTmdbId (int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setNumberOfTrailers(int numberOfTrailers) {
        this.numberOfTrailers = numberOfTrailers;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }*/
}
