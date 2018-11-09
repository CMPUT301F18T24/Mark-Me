package com.cybersix.markme;

public enum EBodyPart {
    LEFTHAND("Left Hand",276.86645,253.24219,300.39917,284.76562),
    LEFTARM("Left Hand",553.7329,531.4844,607.8076,560.5078),
    LEFTLEG("Left Hand",553.7329,531.4844,607.8076,560.5078),
    LEFTFOOT("Left Hand",553.7329,531.4844,607.8076,560.5078),
    RIGHTHAND("Left Hand",553.7329,531.4844,607.8076,560.5078),
    RIGHTARM("Left Hand",553.7329,531.4844,607.8076,560.5078),
    RIGHTLEG("Left Hand",553.7329,531.4844,607.8076,560.5078),
    RIGHTFOOT("Left Hand",553.7329,531.4844,607.8076,560.5078),
    HEAD("Left Hand",553.7329,531.4844,607.8076,560.5078),
    NECK("Left Hand",553.7329,531.4844,607.8076,560.5078),
    CHEST("Left Hand",553.7329,531.4844,607.8076,560.5078),
    ABDOMEN("Left Hand",553.7329,531.4844,607.8076,560.5078),
    UPPERBACK("Left Hand",553.7329,531.4844,607.8076,560.5078),
    LOWERBACK("Left Hand",553.7329,531.4844,607.8076,560.5078);

    private final String name;
    private final float x1;
    private final float x2;
    private final float y1;
    private final float y2;

    private EBodyPart(String name,double x1,double y1, double x2, double y2){
        this.name=name;
        this.x1=(float)x1;
        this.x2=(float)x2;
        this.y1=(float)y1;
        this.y2=(float)y2;
    }

    public float getX1(){
        return this.x1;
    }

    public float getX2(){
        return this.x2;
    }

    public float getY1(){
        return this.y1;
    }

    public float getY2(){
        return this.y2;
    }

    public String toString(){
        return this.name;
    }


}
