package org.vaadin.johannes;

public class Song {

    String name;
    String artist;
    Boolean pop = false;

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public Song(String name, String artist, boolean pop) {
        this.name = name;
        this.artist = artist;
        this.pop = pop;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPop() {
        return pop;
    }

    public void setPop(Boolean pop) {
        this.pop = pop;
    }

    public Boolean getPop() {
        return pop;
    }
}
