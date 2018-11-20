/**
 * gets and sets different body parts
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
