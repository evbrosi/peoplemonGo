package com.example.budget.Models;

/**
 * Created by eaglebrosi on 11/9/16.
 */

public class ImageLoadedEvent {
    public String selectedImage;

    public ImageLoadedEvent(String selectedImage) {
        this.selectedImage = selectedImage;
    }

    public String getSelectedImage() {
        return selectedImage;
    }


}

