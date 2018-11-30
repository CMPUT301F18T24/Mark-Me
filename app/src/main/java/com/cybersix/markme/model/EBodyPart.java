/**
 * CMPUT 301 Team 24
 *
 * Enumerable data class for all body parts.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Curtis Goud
 */
package com.cybersix.markme.model;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;

public enum EBodyPart {
    /*
        NOTE: The below values are attributes for the enumerables as follows:
        BODYPART("Name as String", Top left Point, Bottom right Point, frontFacing)
        Where Top left point and bottom right point are the two points
        that form the bounding rectangle that the body part represents on screen.

        X and Y values stored in these points are percentages of width (X) /height (Y) and
        should be multiplied against the width or height of the ImageView used to display the
        body to get a proper scaled X and Y coordinate
     */
    LEFTHAND("Left Hand",new PointF(0.71224756f,0.5048549f), new PointF(0.817749f,0.5691034f),true),
    LEFTARM("Left Arm",new PointF(0.6814575f,0.24038085f), new PointF(0.817749f,0.5048549f),true),
    LEFTLEG("Left Leg",new PointF(0.5326538f,0.5718889f), new PointF(0.6647644f,0.865127f),true),
    LEFTFOOT("Left Foot",new PointF(0.5326538f,0.8658409f), new PointF(0.6714575f,0.92840403f),true),
    RIGHTHAND("Right Hand",new PointF(0.18774414f,0.49055526f), new PointF(0.29205322f,0.5692034f),true),
    RIGHTARM("Right Arm",new PointF(0.18774414f,0.24038085f), new PointF(0.32196924f,0.49055526f),true),
    RIGHTLEG("Right Leg",new PointF(0.33654785f,0.5718889f), new PointF(0.47146606f,0.865127f),true),
    RIGHTFOOT("Right Foot",new PointF(0.33099365f,0.8658409f), new PointF(0.47561646f,0.9310547f),true),
    HEAD("Head",new PointF(0.36157227f,0.06435547f), new PointF(0.6425171f,0.19570312f),true),
    NECK("Neck",new PointF(0.44503784f,0.19570312f), new PointF(0.55908203f,0.22963867f),true),
    CHEST("Chest",new PointF(0.32681274f,0.2376953f), new PointF(0.6773071f,0.41194198f),true),
    ABDOMEN("Abdomen",new PointF(0.33654785f,0.41227817f), new PointF(0.6731262f,0.5705396f),true),
    UPPERBACK("Upper Back",new PointF(0.32681274f,0.2376953f), new PointF(0.6773071f,0.41194198f),false),
    LOWERBACK("Lower Back",new PointF(0.33654785f,0.2376953f), new PointF(0.6731262f,0.5745396f),false),
    UNLISTED("Unlisted",new PointF(0,0), new PointF(0,0),true);

    private final String name;
    private final PointF p1;
    private final PointF p2;
    private final boolean frontFacing;

    private EBodyPart(String name,PointF p1,PointF p2, boolean front){
        this.name=name;
        this.p1=p1;
        this.p2=p2;
        this.frontFacing = front;
    }

    public PointF getP1(){
        return this.p1;
    }

    public PointF getP2(){
        return this.p2;
    }

    public String toString(){
        return this.name;
    }

    public boolean getFace(){
        return this.frontFacing;
    }


}
