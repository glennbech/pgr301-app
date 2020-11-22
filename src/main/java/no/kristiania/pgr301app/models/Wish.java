package no.kristiania.pgr301app.models;

import javax.persistence.*;

@Entity
@Table(name = "wishes")
public class Wish {
    private long id;
    private String title;
    private String description;
    private String url;
    private String userId;

    public Wish() {

    }

    public Wish(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
