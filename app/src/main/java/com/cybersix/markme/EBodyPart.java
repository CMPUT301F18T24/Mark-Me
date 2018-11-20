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
package com.cybersix.markme;
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
    LEFTHAND("Left Hand",new PointF(0.72598267f,0.50606865f), new PointF(0.7996826f,0.551845f),true),
    LEFTARM("Left Arm",new PointF(0.61608887f,0.2983311f), new PointF(0.7690735f,0.49826485f),true),
    LEFTLEG("Left Leg",new PointF(0.5326538f,0.6702532f), new PointF(0.59802246f,0.8601161f),true),
    LEFTFOOT("Left Foot",new PointF(0.5368347f,0.86391174f), new PointF(0.6731262f,0.9158325f),true),
    RIGHTHAND("Right Hand",new PointF(0.20858765f,0.49167633f), new PointF(0.2698059f,0.5473709f),true),
    RIGHTARM("Right Arm",new PointF(0.23086548f,0.29232025f), new PointF(0.38576514f,0.48819405f),true),
    RIGHTLEG("Right Leg",new PointF(0.3977356f,0.66661835f), new PointF(0.47146606f,0.8601161f),true),
    RIGHTFOOT("Right Foot",new PointF(0.33099365f,0.8638409f), new PointF(0.47146606f,0.9158325f),true),
    HEAD("Head",new PointF(0.3980005f,0.12315204f), new PointF(0.6000f,0.245f),true),
    NECK("Neck",new PointF(0.45895386f,0.24929047f), new PointF(0.5415698f,0.2796173f),true),
    CHEST("Chest",new PointF(0.3880005f,0.281f), new PointF(0.610f,0.44574738f),true),
    ABDOMEN("Abdomen",new PointF(0.3880005f,0.4466847f), new PointF(0.610f,0.66f),true),
    UPPERBACK("Upper Back",new PointF(0.3880005f,0.281f), new PointF(0.610f,0.44574738f),false),
    LOWERBACK("Lower Back",new PointF(0.3880005f,0.4466847f), new PointF(0.610f,0.66f),false),
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
