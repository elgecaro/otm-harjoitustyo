 # Liikuntapäiväkirja :runner: #  
*Ohjelmistotekniikan menetelmät*-kurssin harjoitustyö keväällä 2018, joka on toteutettu **Liikuntapäiväkirja**-sovelluksena. Sovelluksen tarkoitus on, niin kuin nimikin kertoo, toimia liikuntapäiväkirjana. Sovellukseen rekisteröitytään käyttäjänimellä ja salasanalla, voi käyttäjä kirjata päivien liikuntamäärät sovelluksella, jonka avulla voi seurata omaa liikuntaa ja asettaa itselleen viikkotavoitteen. Tämän lisäksi sovellus näyttää käyttäjälle vanhemmat viikkopisteet, oman tuloslistan sekä tuloslistan sovelluksen kaikista käyttäjistä. Viikkopisteet ja -tavoite koostuu tietystä määrästä pisteestä; jokaisesta liikuntatunnista käyttäjä ansaitsee 10 pistettä.

## Dokumentointi ##

[Käyttöohje](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Tuntikirjanpito](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

## Releaset ##
[Release viikko 5](https://github.com/elgecaro/otm-harjoitustyo/releases/tag/viikko5)

[Release viikko 6](https://github.com/elgecaro/otm-harjoitustyo/releases/tag/viikko6)

## Komentorivitoiminnot ##
### Testaus ###
Testit suoritetaan komennolla:
```
mvn test
```
Testikattavuusraportti luodaan komennolla: 
```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla tiedosto *target/site/jacoco/index.html*


### Suoritettavan jarin generointi ###
jar-tiedoston generointi suoritetaan komennolla:
```
mvn package
```
Suoritettavan jar-tiedoston löytyy target-hakemistosta nimellä *Liikuntapaivakirja-1.0-SNAPSHOT.jar*


### Checkstyle ###

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla: 
```
mvn jxr:jxr checkstyle:checkstyle
```
Ohjelman mahdolliset virheilmoitukset selviävät avaamalla tiedosto *target/site/checkstyle.html*


### JavaDoc ###

JavaDoc generoidaan komennolla:
```
mvn javadoc:javadoc
```
JavaDocia voi tarkastella avaamalla tiedosto *target/site/apidocs/index.html*
