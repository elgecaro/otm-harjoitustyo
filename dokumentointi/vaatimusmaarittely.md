# Vaatimusmäärittely #

## Sovelluksen tarkoitus ##
Sovelluksen tarkoitus on toimia liikuntapäiväkirjana niin, että käyttäjä voi kirjata liikuntamäärän sovelluksessa, ja tämän avulla seurata omaa liikuntaa ja asettaa itselleen viikkotavoitteet. Sovellusta on mahdollista käyttää useamman rekisteräityneen käyttäjän, joilla kaikilla on omat yksilölliset "päiväkirjat" tietoineen ja tavoitteineen. 

## Käyttäjät ##
Sovelluksella on vain yksi käyttäjärooli, normaali käyttäjä (mutta myöhemmin olisi mahdollisesti mahdollista lisätä "trainer-rooli", joka esimerkiksi voisi nähdä tiettyjen käyttäjien tiedot, sekä ylläpitäjä.)

## Perusversion tarjoama toiminnallisuus ##

**Kirjautuminen**
* Kirjautuminen tapahtuu syöttämällä käyttäjän käyttäjätunnuksen sekä salasanan
* Jos käyttäjällä ei ole käyttäjätunnusta, voi hän luoda järjestelmään itselleen käyttäjätunnuksen sekä salasanan:
   * käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä
   * salasanan pitää olla pituudeltaan vähintään 6 merkkiä
* Käyttäjä voi sitten kirjautua järjestelmään:
    * kirjautuminen onnistuu kun käyttäjä syöttää olemassaolevan käyttäjätunnuksen sekä oikean salasanan

**Kirjautumisen jälkeen**

Päänäkymä:
* Käyttäjä voi lisätä päiväkirjamerkinnän, johon kirjataan:
  * Kuinka monta tuntia päivässä on harrastanut liikuntaa
  * Mikä päivä kyseessä (1-7, aina käyttäjän viikkojen mukaan)
  * Mikä viikko (1->)
  * Vapaaehtoinen lyhyt kuvaus (max. 200 merkkiä)
* Käyttäjä voi nähdä kyseisen viikon viikkopisteet:
   * Pistemäärä korreloituu käyttäjän syöttämisten liikuntatuntien mukaan, yhdestä liikuntatunnista saa 10 pistettä
* Käyttäjä voi luoda/muokata oman viikkotavoitteen, joka koostuu tietystä pistemäärästä
   * Kun tavoite täyttyy, käyttäjä näkee "tavoite saavutettu"-tekstin
* Käyttäjä näkee 15 viimeiset päiväkirjamerkinnät
* Käyttäjä näkee tuloslistoja:
   * Käyttäjän omat 3 parasta viikkotulosta (pisteet ja viikkonumero)
   * 3 parasta viikkotulosta kaikkien käyttäjien kesken
* Käyttäjä voi kirjautua ulos järjestelmästä

Kaikki merkinnät-näkymä:
* Käyttäjä näkee kaikki omat päiväkirjamerkinnät
   * Järjestetty vanhemmasta uudempaan 
* Käyttäjä voi poistaa tietyn päiväkirjamerkinnän
* Käyttäjä näkee kaikkien viikkojen pisteet 
* Käyttäjä voi kirjautua ulos järjestelmästä

Tilastoja-näkymä:
* Käyttäjä näkee kaikkien viikkojen pisteet
* Käyttäjä näkee kaavion kaikkien viikkojen pisteistä
* Käyttäjä näkee viikkopisteiden keskiarvo, pienin arvo sekä suurin arvo
* Käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita ##
* Ohjelma voisi kertoa kuinka paljon pisteitä käyttäjä tarvitsee siihen, että tavoitteeseen päästään ja jokaisella viikolla voisi olla oma viikkotavoite
* Erilaisista liikuntatapoista saisi eri määrän pisteen, esim. ”rankimmat” liikuntamuodot antaisivat enemmän pisteitä kuin esim. käveleminen
* Ylläpitäjä-käyttäjien luominen, joka voi esim. poistaa käyttäjiä
   * Voisi myös jotenkin valvoa käyttäjien antamat liikuntatunnit
* Trainer-käyttäjien luominen, joka voi "valvoa" tiettyjen käyttäjien liikuntaa, muokata näiden tavoitteita ym.
* Käyttäjätiimit, jotka näkevät muiden liikuntatunnit + pisteet ja jotka voivat asettaa yhteisiä tavoitteita
* Parempi systeemi salasanalle
* Käyttäjätunnuksen ja siihen liittyvien tietojen poisto
