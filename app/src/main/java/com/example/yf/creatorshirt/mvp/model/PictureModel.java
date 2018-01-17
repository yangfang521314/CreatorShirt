package com.example.yf.creatorshirt.mvp.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hendricks on 2017/6/12.
 * 一个要处理的图片的数据模型，包含镂空部分数据
 */

public class PictureModel implements Parcelable{
    private Bitmap bitmapPicture;
    private HollowModel hollowModel;

    private int pictureX;
    private int pictureY;

    private float scaleX = 0.7F;
    private float scaleY = 0.7F;

    protected PictureModel(Parcel in) {
        bitmapPicture = in.readParcelable(Bitmap.class.getClassLoader());
        pictureX = in.readInt();
        pictureY = in.readInt();
        scaleX = in.readFloat();
        scaleY = in.readFloat();
        rotateDelta = in.readFloat();
        isSelect = in.readByte() != 0;
        isLastSelect = in.readByte() != 0;
    }

    public PictureModel() {
    }

    public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel in) {
            return new PictureModel(in);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
        }
    };

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    private float rotateDelta;

    boolean isSelect;

    public boolean isLastSelect() {
        return isLastSelect;
    }

    public void setLastSelect(boolean lastSelect) {
        isLastSelect = lastSelect;
    }

    boolean isLastSelect;


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }



    public void setScale(float v, float scale) {
        this.scaleX = scale;
        this.scaleY = scale;
    }

    public float getRotate() {
        return rotateDelta;
    }

    public void setRotate(float rotateDelta) {
        this.rotateDelta = rotateDelta;
    }

    public int getPictureX() {
        return pictureX;
    }

    public void setPictureX(int pictureX) {
        this.pictureX = pictureX;
    }

    public int getPictureY() {
        return pictureY;
    }

    public void setPictureY(int pictureY) {
        this.pictureY = pictureY;
    }

    public Bitmap getBitmapPicture() {
        return bitmapPicture;
    }

    public void setBitmapPicture(Bitmap bitmapPciture) {
        this.bitmapPicture = bitmapPciture;
    }

    public HollowModel getHollowModel() {
        return hollowModel;
    }

    public void setHollowModel(HollowModel hollowModel) {
        this.hollowModel = hollowModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bitmapPicture, flags);
        dest.writeInt(pictureX);
        dest.writeInt(pictureY);
        dest.writeFloat(scaleX);
        dest.writeFloat(scaleY);
        dest.writeFloat(rotateDelta);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeByte((byte) (isLastSelect ? 1 : 0));
    }
}
