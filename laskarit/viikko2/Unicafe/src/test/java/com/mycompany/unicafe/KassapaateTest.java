
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    
    Kassapaate kassapaate;
    
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }
 
    @Test
    public void rahanMaaraOikea() {
        assertEquals(100000, kassapaate.kassassaRahaa());
        
    }
    
    @Test
    public void myytyjenEdullisienLounaidenMaaraOikea() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenMaukkaidenLounaidenMaaraOikea() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisOstoToimiiEdullinenLounas() {
        kassapaate.syoEdullisesti(240);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());    }

}
