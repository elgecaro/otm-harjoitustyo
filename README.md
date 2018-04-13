# Liikuntapäiväkirja #
*Ohjelmistotekniikan menetelmät*-kurssin harjoitustyö keväällä 2018, joka on toteutettu **Liikuntapäiväkirja**-sovelluksena. Sovelluksen tarkoitus on, niin kuin nimikin kertoo, toimia liikuntapäiväkirjana. Sovellukseen rekisteröitytään käyttäjänimellä ja salasanalla, voi käyttäjä kirjata päivien liikuntamäärät sovelluksella, jonka avulla voi seurata omaa liikuntaa ja asettaa itselleen viikkotavoitteen. Tämän lisäksi sovellus näyttää käyttäjälle vanhemmat viikkopisteet, oman tuloslistan sekä tuloslistan sovelluksen kaikista käyttäjistä. Viikkopisteet ja -tavoite koostuu tietystä määrästä pisteestä; jokaisesta liikuntatunnista käyttäjä ansaitsee 10 pistettä.

## Dokumentointi ##
[Vaatimusmäärittely](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

Arkkitehtuuri (tulossa)

## Komentorivitoiminnot ##
### Testaus ###
Testit suoritetaan komennolla: **`mvn test`**

Testikattavuusraportti luodaan komennolla: **`mvn jacoco:report`**

Kattavuusraporttia voi tarkastella avaamalla tiedosto *target/site/jacoco/index.html*

### Checkstyle ###

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla: **`mvn jxr:jxr checkstyle:checkstyle`**

Ohjelman mahdolliset virheilmoitukset selviävät avaamalla tiedosto *target/site/checkstyle.html*
