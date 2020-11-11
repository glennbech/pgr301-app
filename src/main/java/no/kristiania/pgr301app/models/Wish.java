package no.kristiania.pgr301app.models;

public class Wish {
    private final String title;
    private final String url;

    public Wish(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
