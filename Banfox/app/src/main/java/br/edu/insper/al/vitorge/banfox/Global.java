package br.edu.insper.al.vitorge.banfox;

import android.app.Application;

public class Global extends Application {

    private int pictureNumber;
    private String userName;
    private String facePicture;
    private String idPicture;
    private String groupPicture;
    private String textPicture;
    private String textDetected;
    private boolean result;
    private double userLatitude;
    private double userLongitude;
    private boolean nameMatch;
    private boolean faceMatch;

    public int getPictureNumber() {
        return this.pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isFaceMatch() {
        return faceMatch;
    }

    public void setFaceMatch(boolean faceAndID) {
        this.faceMatch = faceAndID;
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

    public boolean isNameMatch() {
        return nameMatch;
    }

    public void setNameMatch(boolean nameMatch) {
        this.nameMatch = nameMatch;
    }
}
