# Käyttöohje # 
Lataa tiedosto [liikuntapaivakirja.jar](https://github.com/elgecaro/otm-harjoitustyo/releases/tag/loppupalautus)

## Konfigurointi ##
Ohjelma tulee olettaamaan, että sen käynnistyshakemistossa on konfiguraatiotiedosto *config.properties*, joka määrittelee tietokannan nimen. 
Tiedoston muoto on seuraava
```
databaseAddress=jdbc:sqlite:database.db
```

## Ohjelman käynnistäminen ##
Ohjelma käynnistetään komennolla 
```
java -jar liikuntapaivakirja.jar
```
(Tai tuplaklikkaamalla tiedostoa)

## Kirjautuminen ##
Sovellus käynnistyy aloitusnäkymään:

![aloitusnäkymä](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/aloitusnakyma.PNG)

Jos käyttäjällä on jo olemassa oleva käyttäjätunnus (sekä salasana), klikataan *kirjaudu sisään*-nappulaa, joka johtaa kirjautumisnäkymään:

![kirjautuminen](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/kirjautuminen.PNG)

Kirjautuminen onnistuu kirjoittamalla olemassa oleva käyttäjätunnus sekä salasana niihin kuuluviin kenttiin ja painamalla *Kirjaudu sisään*.

## Uuden käyttäjän luominen ##
Käyttäjä voi luoda itselleen uuden käyttäjätunnuksen klikkaamalla *luo uusi käyttäjä*-nappulaa aloitusnäkymässä. 
Tällöin siirrytään uuteen näkymään, jossa uusi käyttäjä luodaan syöttämällä käyttäjätunnus (3 merkkiä tai enemmän) sekä salasana (6 merkkiä tai enemmän) ja painamalla *Luo käyttäjä*-nappulaa:

![UusiKayttaja](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/uuden_kayttajan_luominen.PNG)

Jos uuden käyttäjän luominen onnistuu (käyttäjätunnuksen ja salasanan kriteerit täyttyvät), palataan kirjautumisnäkymään, jossa uusi käyttäjä voi kirjautua sisään.

## Liikuntapäiväkirjaan toiminallisuudet ##
Kirjauduttuaan sovellukseen käyttäjä: 
* voi lisätä uusi päiväkirjamerkintä sovellukseen
* näkee 15 viimeiset kirjoitukset viikkojärjestyksessä
* näkee oman viikkopisteensä & tavoitteensa
* näkee omat ja kaikkien käyttäjien 3 parhaat viikkopisteet

![Liikuntapaivakirja_kirjautunut](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/liikuntapaivakirja_kirj.PNG)

* Käyttäjä voi myös nähdä kaikki merkinnät, poistaa näitä sekä nähdä tilastoja merkinnöistä

### Päiväkirjamerkinnän lisääminen ###
Käyttäjän kirjouduttua sisään, voi hän kirjoittaa liikuntapäiväkirjaan uuden merkinnän. Käyttäjä syöttää tällöin ensin liikunnan tunteina, päivän, viikon sekä kuvauksen. Tässä sovelluksessa seurataan omaa liikuntaa viikko-muodossa, eli käyttäjän 
aloittaessa viikko on 1, ja päivä merkitään numerolla 1-7 (eli viikonpäivät). 

Kun käyttäjä on kirjoittanut liikuntaan tarvittavat tiedot, kirjoitus lisätään ”lisää”-painikkeella:

![uusi_merkinta](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/uusi_merkinta.PNG)

Käyttäjä voi nähdä kaikki tekemänsä kirjoitukset klikkaamalla *näe kaikki kirjoitukset*, jolloin uusi näkymä tulee esiin, 
jossa listataan kaikki kirjoitukset:

![kaikki_merkinnöt](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/kaikki_merkinnat.PNG)

Tällä sivulla käyttäjä näkee myös kaikkien viikkojen pisteet, ja voi myös poistaa merkintöjä.

### Viikkotavoitteen valitseminen ja/tai vaihto ###
Kirjauduttuaan käyttäjä voi myös asettaa itselleen oman viikkotavoitteen, joka koostuu tietystä pistemäärästä. 
Tämän voi tehdä kirjoittamalla tavoite-tekstialueeseen ja klikkaamalla *aseta uusi tavoite*, jolloin tavoite lisätään. 
Haluttaessa voi myöhemmin samalla tavalla myös vaihtaa kyseisen viikkotavoitteen, mutta muuten se pysyy samana koko ajan. 

![viikkotavoite](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/viikkotavoite.png)

### Päiväkirjamerkintöjen tilastot ###
Kun käyttäjä klikkaa *tilastoja*-nappulaa päänäkymässä, käyttäjä voi nähdä kaavion viikkopisteistään, kaikkien viikkojen pisteet sekä viikkopisteiden keskiarvon, pienin arvo sekä suurin arvo:

![tilastoja](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/tilastoja.PNG)
