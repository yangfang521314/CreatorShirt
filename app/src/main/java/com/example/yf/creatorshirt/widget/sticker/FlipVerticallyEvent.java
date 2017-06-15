package com.example.yf.creatorshirt.widget.sticker;


public class FlipVerticallyEvent extends AbstractFlipEvent {

    @Override
    @StickerView.Flip
    protected int getFlipDirection() {
        return StickerView.FLIP_VERTICALLY;
    }
}
