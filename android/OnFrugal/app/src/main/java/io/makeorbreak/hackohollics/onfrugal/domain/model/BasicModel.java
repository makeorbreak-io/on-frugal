package io.makeorbreak.hackohollics.onfrugal.domain.model;

import java.io.Serializable;

public class BasicModel implements Serializable{
    protected String id;
    protected String name;

    public BasicModel() {
    }

    public BasicModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || ((User) obj).getUid().equals(this.id);

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
