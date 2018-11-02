package com.cybersix.markme;

import org.junit.Test;

import static org.junit.Assert.*;

public class BodyLocationTest {

    @Test
    public void testGetBodyPart() {
        EBodyPart EBP = EBodyPart.ABDOMEN;
        BodyLocation bl = new BodyLocation(EBP);
        assertEquals(bl.getBodyPart(),EBodyPart.ABDOMEN);
        EBodyPart getBP = bl.getBodyPart();
        assertEquals(getBP,EBP);
    }

    @Test
    public void testSetBodyPart() {
        BodyLocation bl = new BodyLocation(EBodyPart.ABDOMEN);
        assertEquals(bl.getBodyPart(),EBodyPart.ABDOMEN);
        EBodyPart newBP = EBodyPart.HEAD;
        bl.setBodyPart(newBP);
        assertEquals(newBP,bl.getBodyPart());
    }
}