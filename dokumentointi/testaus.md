# Testausdokumentti #

Ohjelmaa on testattu automatisoiduin yksikkö- ja integraatiotestein JUnit:illa sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus ##
### Sovelluslogiikka ###
**Sovelluslogiikkaa**, eli pakkauksen *liikuntapaivakirja.domain* luokkia, testataan integraatiotesteillä *DiaryServiceUserTest* ja 
*DiaryServiceDiaryEntryTest*-testien avulla. Nämä testitapaukset simuloivat käyttöliittymän DiaryService-luokan toiminallsiuuksia. 
Testit käyttävät dataa pysyväistallennukseen sovelluksen sovelluksen ”oikeita” DAO-rajapintoja (*DiaryEntryDao* ja *UserDao*), 
mutta käytössä on testiluokkien oma testitietokanta. 

Myös sovelluslogiikkakerroksen luokille *User* ja *DiaryEntry* on tehty muutama yksikkötesti, jotka esim. tarkistavat että käyttäjän
syöttämät tiedot löytyvät ja ovat oikein.

### DAO-luokat ###
Kuten yllä mainittiin, DAO-luokkien toiminallisuutta on testattu luomalla testeissä tilapäinen tietokanta *test.db* hyödyntäen JUnitin 
*TemporaryFolder* ja *TemporaryFile*-tiedostoa, joka aina poistetaan testien lopussa.

## Testauskattavuus ###
Sovelluksen testauksen rivikattavuus on 100% ja haarautumakattavuus 100%. Käyttöliittymäkerrosta ei ole testattu. 
![testauskattavuus](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/testikattavuus.PNG)
 
## Järjestelmätestaus ##
Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

## Asennus ja konfigurointi ##
Sovelluksen asennus ja konfigurointi on manuaalisesti testattu käyttöohjeen mukaan Linux sekä Windows-ympäristöissä. 
Sovellusta on myös testattu tilanteessa, jossa tietokanta on ollut etukäteen jo olemassa, sekä tilanteessa missä sovellus luo sen itse.

## Toiminnallisuudet ##
[Määrittelydokumentin](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md) 
ja [käyttöohjeen](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md) listaamat toiminallisuudet on
käyty läpi ja tällöin testattu. Sovelluksen syötekentät on myös testattu virheellisillä arvoilla, kuten:
* Liian lyhyt salasana
* Liian lyhyt käyttäjätunnus
* Tunnin/päivän/viikon väärä muoto (ei integer/double)
* Tunnin/päivän/viikon negatiivinen arvo
* Liian pitkä kuvaus

## Sovellukseen jääneet laatuongelmat ##
Sovellus ei ilmoita käyttäjille kaikista mahdollista virheistä, esim. jos tietokanta poistetaan kun sovellusta käytetään, käyttäjä ei 
saa siitä virheilmoituksen. Jos käyttäjä tällöin yrittää lisätä uuden merkinnän, saa hän kuitenkaan virheilmoituksen, ettei merkintää 
voida lisätä.
