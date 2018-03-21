package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikeinAlussa() {
        assertTrue(kortti.saldo() ==10);
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(20);
        assertTrue(kortti.saldo() ==30);
    }
    
    @Test
    public void rahanOttaminenTarpeeksiRahaa() {  
        assertEquals(true, kortti.otaRahaa(2));
        assertTrue(kortti.saldo() ==8);
    }
    
    @Test
    public void rahanOttaminenEiTarpeeksiRahaa() {
        assertEquals(false, kortti.otaRahaa(15));
        assertTrue(kortti.saldo() ==10); 
        // (Uuden kortin saldo on testissä 10, eli saldo ei muutu)
        
    }
    
}