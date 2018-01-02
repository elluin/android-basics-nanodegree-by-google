package com.example.android.tourguidetatahungary;


public class Sights {

    /*Attributes of class Sights */
    private String mSightsName;
    private String mSightsAddress;
    private String mSightsOpeningHours;
    private int mImageDrawableId = NO_IMAGE_PROVIDED;

    public static final int NO_IMAGE_PROVIDED = -1;


    /*The Sights constructor*/
    public Sights(String sightsName, String sightsAddress, String sightsOpeningHours, int sightsImmageDrawableId) {
        mSightsName = sightsName;
        mSightsAddress = sightsAddress;
        mSightsOpeningHours = sightsOpeningHours;
        mImageDrawableId = sightsImmageDrawableId;
    }

    /*Get methods*/
    public String getSightsName() {
        return mSightsName;
    }

    public String getSightsAddress() {
        return mSightsAddress;
    }

    public String getSightsOpeningHours() {
        return mSightsOpeningHours;
    }

    public int getImageDrawableId() {

        return mImageDrawableId;
    }

    public boolean hasImage() {
        return mImageDrawableId != NO_IMAGE_PROVIDED;
    }


}
