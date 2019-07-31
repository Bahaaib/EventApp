package com.bahaa.eventapp.models;

public class NotificationModel {

    private String id;

    private String pointsDestination;

    private int points;


    public NotificationModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPointsDestination() {
        return pointsDestination;
    }

    public void setPointsDestination(String pointsDestination) {
        this.pointsDestination = pointsDestination;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
