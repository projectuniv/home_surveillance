package com.initi.thierry.homesecurity;

/**
 * Created by Thierry on 01/12/2016.
 */
public class UserData {
    private String id ;
    private String idCapteur;
    private String date;
    private String userId;
    private String image;
    private String imageName;
    private byte [] imageBytes;

    public UserData(){}

    public UserData(String id, String userId, String idCapteur, byte[] imageBytes, String imageName, String date,  String image){
        this.id = id;
        this.userId = userId;
        this.idCapteur = idCapteur;
        this.imageBytes = imageBytes;
        this.imageName = imageName;
        this.date = date;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getIdCapteur() {
        return idCapteur;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public String getImageName() {
        return imageName;
    }

}