package com.avast.example.android.github.model;

/**
 * @author Tomáš Kypta (kypta)
 */
public class Repo {

    private long id;
    private String name;
    private String description;
    private String url;

    public Repo(long id, String name, String description, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Repo{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
