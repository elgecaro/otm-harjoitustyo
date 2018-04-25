# Alustava käyttöohje # 
Lataa tiedosto liikuntapaivakirja.jar

## Konfigurointi ##
Ohjelma tulee olettaamaan, että sen käynnistyshakemistossa on konfiguraatiotiedosto config.properties, joka määrittelee tietokannan nimen. 
Tiedoston muoto on seuraava
```
databaseAddress=database.db
```

## Ohjelman käynnistäminen ##
Ohjelma käynnistetään komennolla 
```
java -jar liikuntapaivakirja.jar
```

## Kirjautuminen ##
Sovellus käynnistyy aloitusnäkymään:
(kuva tulossa)

Jos käyttäjällä on jo olemassa oleva käyttäjätunnus (sekä salasana), klikataan *Kirjaudu*-nappulaa, joka johtaa kirjautumisnäkymään: (kuva tulossa)

Kirjautuminen onnistuu kirjoittamalla olemassa oleva käyttäjätunnus sekä salasana niihin kuuluviin kenttiin ja painamalla kirjaudu.

## Uuden käyttäjän luominen ##
Käyttäjä voi luoda itselleen uuden käyttäjätunnuksen klikkaamalla *Luo uusi käyttäjä*-nappulaa aloitusnäkymässä. 
Tällöin siirrytään uuteen näkymään, jossa uusi käyttäjä luodaan syöttämällä käyttäjätunnus sekä salasana ja painamalla *Luo käyttäjä*.
(kuva tulossa)
Jos uuden käyttäjän luominen onnistuu (käyttäjätunnuksen ja salasanan kriteerit täyttyvät), palataan kirjautumisnäkymään.

## Liikuntapäiväkirjaan toiminallisuudet ##
Kirjauduttuaan sovellukseen käyttäjä: 
* voi lisätä liikuntakirjoitus päiväkirjaan
* näkee 15 viimeiset kirjoitukset viikkojärjestyksessä
* näkee oman viikkopisteensä + tavoitteensa
* näkee omat ja kaikkien käyttäjien 3 parhaat viikkopisteet

(Kuva tulossa)

### Liikunnan lisääminen / liikuntapäiväkirjaan kirjoittaminen ###
Käyttäjän kirjouduttua sisään, voi hän kirjoittaa liikuntapäiväkirjaan omat liikuntatunnit. Käyttäjä syöttää tällöin ensin liikunnan 
tunteina, päivän, viikon sekä kuvauksen. Tässä sovelluksessa seurataan omaa liikuntaa viikko-muodossa, eli käyttäjän 
aloittaessa viikko on 1, ja päivä merkitään numerolla 1-7 (eli viikonpäivät).

Kun käyttäjä on kirjoittanut liikuntaan tarvittavat tiedot, kirjoitus lisätään ”lisää”-painikkeella. 
(Kuva tulossa)
Käyttäjä voi nähdä kaikki tekemänsä kirjoitukset klikkaamalla *näe kaikki kirjoitukset*, jolloin uusi näkymä tulee esiin, 
jossa listataan kaikki kirjoitukset:
(Kuva tulossa)

### Viikkotavoitteen valitseminen ja/tai vaihto ###
Kirjauduttuaan käyttäjä voi myös asettaa itselleen oman viikkotavoitteen, joka koostuu tietystä pistemäärästä. 
Tämän voi tehdä kirjoittamalla tavoite-tekstialueeseen ja klikkaamalla *aseta uusi tavoite*, jolloin tavoite lisätään. 
Haluttaessa voi myöhemmin samalla tavalla myös vaihtaa kyseisen viikkotavoitteen, mutta muuten se pysyy samana koko ajan. 
(Kuva tulossa)

(Lisää käyttäohjeita tulossa)
