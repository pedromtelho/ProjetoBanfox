package br.edu.insper.al.vitorge.banfox;

import android.app.Application;

import com.google.gson.JsonObject;

class Global extends Application {

    private int pictureNumber;
    private JsonObject userInfo;
    private String facePicture;
    private String idPicture;
    private String groupPicture;
    private String textPicture;
    private String textDetected;
    private boolean result;
    private double userLatitude;
    private double userLongitude;
    private Float faceMatch;
    private Float infoMatch;

    public int getPictureNumber() {
        return this.pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
    }

    public JsonObject getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(JsonObject userInfo) {
        this.userInfo = userInfo;
    }

    public String getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(String facePicture) {
        this.facePicture = facePicture;
    }

    public String getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(String idPicture) {
        this.idPicture = idPicture;
    }

    public String getGroupPicture() {
        return groupPicture;
    }

    public void setGroupPicture(String groupPicture) {
        this.groupPicture = groupPicture;
    }

    public Float getFaceMatch() {
        return faceMatch;
    }

    public void setFaceMatch(Float faceMatch) {
        this.faceMatch = faceMatch;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getTextDetected() {
        return textDetected;
    }

    public void setTextDetected(String textDetected) {
        this.textDetected = textDetected;
    }

    public String getTextPicture() {
        return textPicture;
    }

    public void setTextPicture(String textPicture) {
        this.textPicture = textPicture;
    }

    public Float getInfoMatch() {
        return infoMatch;
    }

    public void setInfoMatch(Float infoMatch) {
        this.infoMatch = infoMatch;
    }
}
