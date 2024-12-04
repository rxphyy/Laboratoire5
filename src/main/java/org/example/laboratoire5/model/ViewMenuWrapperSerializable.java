package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;
import org.example.laboratoire5.view.ViewMenuWrapper;

import java.io.Serializable;
import java.util.List;

public class ViewMenuWrapperSerializable implements Serializable {
    List<View> views;
    Image image;

    public ViewMenuWrapperSerializable(ViewMenuWrapper viewMenuWrapper) {
        this.views = viewMenuWrapper.getViews();
        this.image = viewMenuWrapper.getImage();
    }
}
