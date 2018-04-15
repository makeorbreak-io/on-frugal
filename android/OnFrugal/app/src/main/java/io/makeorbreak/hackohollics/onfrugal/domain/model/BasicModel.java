package io.makeorbreak.hackohollics.onfrugal.domain.model;

import java.io.Serializable;

public class BasicModel implements Serializable{
    protected String uid;
    protected String name;

    public BasicModel() {
    }

    public BasicModel(String id, String name) {
        this.uid = id;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || ((User) obj).getUid().equals(this.uid);

    }

    @Override
    public int hashCode() {
        return getUid().hashCode();
    }
}
