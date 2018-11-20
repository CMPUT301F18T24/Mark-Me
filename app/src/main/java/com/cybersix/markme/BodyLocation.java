/**
 * CMPUT 301 Team 24
 *
 * Data class for body locations.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Curtis Goud
 */
package com.cybersix.markme;


public class BodyLocation {
    private EBodyPart bodyPart;

    public BodyLocation(EBodyPart bp){
        this.bodyPart = bp;
    }

    public EBodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(EBodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }
}
