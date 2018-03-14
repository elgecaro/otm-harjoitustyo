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
        kortti.otaRahaa(2);
        assertTrue(kortti.saldo() ==8);
    }
    
    @Test
    public void rahanOttaminenEiTarpeeksiRahaa() {
        kortti.otaRahaa(15);
        assertFalse(kortti.saldo() !=10);
    }
    
    
    
//    @Test
//    public void konstruktoriAsettaaSaldonOkein() {
//        Maksukortti kortti = new Maksukortti(10);
//        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void syoEdullisestiVahentaaSaldoaOikein() {
//        Maksukortti kortti = new Maksukortti(10);
//        kortti.syoEdullisesti();
//        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void syoMaukkaastiVahentaaSaldoaOikein() {
//        Maksukortti kortti = new Maksukortti(10);
//        kortti.syoMaukkaasti();
//        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void kortilleVoiLadataRahaa() {
//        kortti.lataaRahaa(25);
//        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void kortinSaldoEiYlitaMaksimiarvoa() {
//        kortti.lataaRahaa(200);
//        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
//        Maksukortti kortti = new Maksukortti(10);
//        
//        kortti.syoMaukkaasti();
//        kortti.syoMaukkaasti();
//        // nyt kortin saldo on 2
//        kortti.syoEdullisesti();
//        
//        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
//        
//    }
//    
//    @Test
//    public void negaatiivinenLatausEiMuutaSaldoa() {
//        kortti.lataaRahaa(-5);
//        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
//        // Koska kortilla on valmiiksi 10 euroa, summan ei pitäisi muuttua
//    }
//    
//    @Test
//    public void edullisenLounaanPystyyOstamaan() {
//        Maksukortti kortti = new Maksukortti(2.5);
//        kortti.syoEdullisesti();
//        
//        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
//    }
//    
//
//    @Test
//    public void maukkaanLounaanPystyyOstamaan() {
//        Maksukortti kortti = new Maksukortti(4);
//        kortti.syoMaukkaasti();
//        
//        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
}
