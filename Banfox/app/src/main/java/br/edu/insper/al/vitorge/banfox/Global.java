package br.edu.insper.al.vitorge.banfox;

import android.app.Application;
import android.location.Location;

public class Global extends Application {

    private String userName;
    private Location userLocation;
    private int pictureNumber;
    private byte[] facePicture;
    private byte[] idPicture;
    private byte[] groupPicture;

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

    public Location getUserLocation() {
        return this.userLocation;
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
    }

    public byte[] getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(byte[] facePicture) {
        this.facePicture = facePicture;
    }

    public byte[] getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(byte[] idPicture) {
        this.idPicture = idPicture;
    }

    public byte[] getGroupPicture() {
        return groupPicture;
    }

    public void setGroupPicture(byte[] groupPicture) {
        this.groupPicture = groupPicture;
    }
}
