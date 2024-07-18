package org.pahappa.pettycashapp.systems.petty_cash_app.models;

import lombok.Builder;

@Builder
public class ImageBuilder {
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
