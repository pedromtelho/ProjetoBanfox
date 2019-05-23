package br.edu.insper.al.vitorge.banfox;

import android.app.Application;
import android.location.Location;

import java.nio.ByteBuffer;

public class Global extends Application {

    private String userName;
    private Location userLocation;
    private int pictureNumber;
    private ByteBuffer facePicture;
    private ByteBuffer idPicture;
    private ByteBuffer groupPicture;

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

    public ByteBuffer getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(ByteBuffer facePicture) {
        this.facePicture = facePicture;
    }

    public ByteBuffer getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(ByteBuffer idPicture) {
        this.idPicture = idPicture;
    }

    public ByteBuffer getGroupPicture() {
        return groupPicture;
    }

    public void setGroupPicture(ByteBuffer groupPicture) {
        this.groupPicture = groupPicture;
    }
}
