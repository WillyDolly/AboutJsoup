package com.popland.pop.aboutjsoup;

/**
 * Created by hai on 24/07/2016.
 */
public class RSSitems {
    public String title;
    public String description;
    public String pubDate;
    public String media_thumbnail;

    public RSSitems(String title, String description, String pubDate, String media_thumbnail) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.media_thumbnail = media_thumbnail;
    }
}
