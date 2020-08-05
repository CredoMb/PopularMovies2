package com.example.android.popularmovies2.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "favorite")
public class FavoriteEntry {


    /*    */

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String synopsis;
    private float rating;

    private int numberOfTrailers;

    @ColumnInfo(name = "release_date")
    private String releaseDate;
    private byte[] posterImage;

    // Do you store the favorite as a Preference ?

    @Ignore
    public FavoriteEntry(String synopsis, float rating,
                         int numberOfTrailers, String releaseDate, byte[] posterImage) {

        this.synopsis = synopsis;
        this.rating = rating;
        this.numberOfTrailers = numberOfTrailers;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }

    public FavoriteEntry(int id, String synopsis, float rating,
                         int numberOfTrailers, String releaseDate, byte[] posterImage) {
        this.id = id;
        this.synopsis = synopsis;
        this.rating = rating;
        this.numberOfTrailers = numberOfTrailers;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }

    /** Getters */

    public int getId() {
        return id;
    }

    public byte[] getPosterImage() {
        return posterImage;
    }

    public String getSynopsis() {
        return synopsis;
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

    /**Setters */

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterImage(byte[] posterImage) {
        this.posterImage = posterImage;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setNumberOfTrailers(int numberOfTrailers) {
        this.numberOfTrailers = numberOfTrailers;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
