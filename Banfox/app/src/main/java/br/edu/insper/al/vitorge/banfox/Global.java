package br.edu.insper.al.vitorge.banfox;

import android.app.Application;
import android.location.Location;

public class Global extends Application {

    private String userName;
    private int pictureNumber;
    private String facePicture;
    private String idPicture;
    private String groupPicture;
    private boolean result;
    private double userLatitude;
    private double userLongitude;
    private boolean faceMatch;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPictureNumber() {
        return this.pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
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
}
