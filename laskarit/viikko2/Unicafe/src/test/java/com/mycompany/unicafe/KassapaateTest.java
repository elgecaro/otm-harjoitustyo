
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti kortti;
    
    
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
    public void kateisOstoEdullinenLounasMyytyjenMaaraOikea() {
        kassapaate.syoEdullisesti(240);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());    
    }
    
    @Test
    public void kateisOstoMaukasLounasMyytyjenMaaraOikea() {
        kassapaate.syoMaukkaasti(400);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());    
    }
    
    @Test
    public void kateisOstoJaVaihtorahaToimiiEdullinenLounas() {
        assertEquals(100, kassapaate.syoEdullisesti(340));
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());    
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kateisOstoJaVaihtorahaToimiiMaukasLounas() {
        assertEquals(200, kassapaate.syoMaukkaasti(600));
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());    
        assertEquals(100400, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kateisOstoEiRiittavaMaksuEdullinenLounas() {
        assertEquals(200, kassapaate.syoEdullisesti(200));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());    
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kateisOstoEiRiittavaMaksuMaukasLounas() {
        assertEquals(300, kassapaate.syoMaukkaasti(300));
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());    
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void korttiOstoEdullinenLounasMyytyjenMaaraOikea() {
        kortti = new Maksukortti(300);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());    
    }
    
    @Test
    public void korttiOstoMaukasLounasMyytyjenMaaraOikea() {
        kortti = new Maksukortti(500);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());    
    }
    
    @Test
    public void korttiOstoToimiiEdullinenLounas() {
        kortti = new Maksukortti(240);
        assertEquals(true, kassapaate.syoEdullisesti(kortti));
        assertEquals(100000, kassapaate.kassassaRahaa());    
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());   
    }
    
    @Test
    public void korttiOstoToimiiMaukasLounas() {
        kortti = new Maksukortti(400);
        assertEquals(true, kassapaate.syoMaukkaasti(kortti));
        assertEquals(100000, kassapaate.kassassaRahaa());    
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoEiRiittavaMaksuEdullinenLounas() {
        kortti = new Maksukortti(200);
        assertEquals(false, kassapaate.syoEdullisesti(kortti));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());    
    }
    
    @Test
    public void korttiOstoEiRiittavaMaksuMaukasLounas() {
        kortti = new Maksukortti(300);
        assertEquals(false, kassapaate.syoMaukkaasti(kortti));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());    
    }
    
    @Test
    public void korttiRahanLatausOnnistuu() {
        kortti = new Maksukortti(300);
        kassapaate.lataaRahaaKortille(kortti, 500);
        assertEquals(800, kortti.saldo());
        assertEquals(100500, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void korttiRahanLatausNegatiivinenSumma() {
        kortti = new Maksukortti(300);
        kassapaate.lataaRahaaKortille(kortti, -10);
        assertEquals(300, kortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
}

