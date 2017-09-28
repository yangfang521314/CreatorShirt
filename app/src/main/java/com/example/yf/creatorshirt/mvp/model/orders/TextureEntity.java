package com.example.yf.creatorshirt.mvp.model.orders;

/**
 * Created by yangfang on 2017/9/28.
 */

public class TextureEntity {
    private String texture;

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    @Override
    public String toString() {
        return "TextureEntity{" +
                "texture='" + texture + '\'' +
                '}';
    }
}
