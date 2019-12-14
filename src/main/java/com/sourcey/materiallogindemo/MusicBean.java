package com.sourcey.materiallogindemo;


public class MusicBean {

    private int id;
    private String movieName;
    private String imageLink;
    private String movieGenre;
    private String url;

    public MusicBean(int id, String movieName, String imageLink, String movieGenre, String url) {
        this.id = id;
        this.movieName = movieName;
        this.imageLink = imageLink;
        this.movieGenre = movieGenre;
        this.url=url;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }
    public String getUrl(){
        return url;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

}
