package com.hackoholics.onfrugal.main.presentation.searchList;

import com.hackoholics.onfrugal.main.domain.model.BasicModel;

public class ChildRow {
    private int icon;
    private BasicModel basicModel;

    public ChildRow(int icon, BasicModel basicModel) {
        this.icon = icon;
        this.basicModel = basicModel;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public BasicModel getBasicModel() {
        return basicModel;
    }

    public void setBasicModel(BasicModel basicModel) {
        this.basicModel = basicModel;
    }
}
