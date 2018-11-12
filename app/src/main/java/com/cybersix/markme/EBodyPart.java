package com.cybersix.markme;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;

public enum EBodyPart {
    LEFTHAND("Left Hand",new PointF(0.7649231f,0.5043793f), new PointF(0.83862305f,0.5434799f),true),
    LEFTARM("Left Arm",new PointF(0.6341858f,0.29815674f), new PointF(0.8121948f,0.48286438f),true),
    LEFTLEG("Left Leg",new PointF(0.5368347f,0.672493f), new PointF(0.6202698f,0.86013794f),true),
    LEFTFOOT("Left Foot",new PointF(0.5368347f,0.86891174f), new PointF(0.70370483f,0.9158325f),true),
    RIGHTHAND("Right Hand",new PointF(0.16131592f,0.49167633f), new PointF(0.23086548f,0.5473709f),true),
    RIGHTARM("Right Arm",new PointF(0.18414307f,0.3022165f), new PointF(0.37176514f,0.48221472f),true),
    RIGHTLEG("Right Leg",new PointF(0.3838501f,0.66661835f), new PointF(0.47146606f,0.8572006f),true),
    RIGHTFOOT("Right Foot",new PointF(0.30596924f,0.8728409f), new PointF(0.45477295f,0.9158325f),true),
    HEAD("Head",new PointF(0.3880005f,0.12615204f), new PointF(0.61608887f,0.23075104f),true),
    NECK("Neck",new PointF(0.45895386f,0.24929047f), new PointF(0.5465698f,0.2796173f),true),
    CHEST("Chest",new PointF(0.3880005f,0.29232025f), new PointF(0.6286316f,0.44574738f),true),
    ABDOMEN("Abdomen",new PointF(0.3880005f,0.4486847f), new PointF(0.6244507f,0.6539154f),true),
    UPPERBACK("Upper Back",new PointF(0.3880005f,0.29232025f), new PointF(0.6286316f,0.44574738f),false),
    LOWERBACK("Lower Back",new PointF(0.3880005f,0.4486847f), new PointF(0.6244507f,0.6539154f),false);

    private final String name;
    private final PointF p1;
    private final PointF p2;
    private final boolean frontFacing;

    //Point 1-4 Create a bounding Rectangle for the body part on screen
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
