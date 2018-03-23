# Alustava määrittelydokumentti #

## Sovelluksen tarkoitus ##
Sovelluksen tarkoitus on toimia liikuntapäiväkirjana niin, että käyttäjä voi kirjata liikuntamäärän sovelluksessa, jonka avulla voi seurata omaa liikuntaa ja asettaa itselleen viikkotavoitteet. Sovellusta on mahdollista käyttää useamman rekisteräityneen käyttäjän, joilla kaikilla on omat yksilölliset "päiväkirjat" tietoineen ja tavoitteineen.

## Käyttäjät ##
Sovelluksella on vain yksi käyttäjärooli, normaali käyttäjä (mutta myöhemmin olisi mahdollisesti mahdollista lisätä "trainer-rooli", joka esimerkiksi voisi nähdä tiettyjen käyttäjien tiedot, sekä ylläpitäjä.)

## Suunnitellut toiminnallisuudet ##

**Kirjautuminen**
* Kirjautuminen tapahtuu syöttämällä käyttäjän käyttäjätunnuksen (sekä salasanan?) 
* Jos käyttäjällä ei ole käyttäjätunnusta, voi hän luoda järjestelmään itselleen käyttäjätunnuksen (sekä salasanan?):
    * käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä
* Käyttäjä voi sitten kirjautua järjestelmään:
    * kirjautuminen onnistuu kun käyttäjä syöttää olemassaolevan käyttäjätunnuksen (sekä salasanan?)

**Kirjautumisen jälkeen**
* Käyttäjä voi lisätä kuinka monta tuntia päivässä on harrastanut liikuntaa
  * (Tämän voi tehdä monta kertaa päivässä, aina liikunnan jälkeen)
  * Käyttäjä voi myös lisätä lyhyen kuvauksen (max. XXX merkkiä)
* Käyttäjä näkee oman viikkotavoitteet, joka koostuu tietystä pistemäärästä
   * Pistemäärä korreloituu käyttäjän syöttämisten liikuntatuntien mukaan
* Käyttäjä näkee kyseisen viikon jo syötetyt tuntimäärät ja pisteet
   * Nämä ovat järjestetty aina päivän mukaan
* Käyttäjä näkee 4 viimeisten viikkojen yhteistunnit ja pisteet
* Käyttäjä näkee tuloslistoja:
   * Käyttäjän omat 3 parasta viikkotulosta (tunnit ja pisteet)
   * Mahdollisesti myös 3 parasta viikkotulosta sovelluksen kaikkien käyttäjien kesken
* Käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita ##
* Käyttäjä voisi muokata ja/tai poistaa jo täytettyjä tietoja
* Käyttäjä voisi nähdä kaikki edellisten viikkojen tiedot + tiedot yksittäisistä päivistä
* Ohjelma voisi merkitä kuinka paljon pisteitä käyttäjä tarvitsee siihen, että tavoitteeseen päästään + jos viikkotavoite toteutui
* Erilaisista liikuntatapoista saisi eri määrän pisteen, esim. ”rankimmat” liikuntamuodot antaisivat enemmän pisteitä kuin esim. perus juokseminen
* Ylläpitäjä-käyttäjien luominen, joka voi esim. poistaa käyttäjiä
   * Voisi myös jotenkin valvoa käyttäjien antamat liikuntatunnit
* Trainer-käyttäjien luominen, joka voi "valvoa" tiettyjen käyttäjien liikuntaa, muokata tavoitteita ym.
* Käyttäjätiimit, jotka näkevät muiden liikuntatunnit + pisteet ja jotka voivat asettaa yhteisiä tavoitteita

* Jos perusversiossa ei ole käyttäjätunnuksen yhteydessä salasana, sitä voisi lisätä
* Käyttäjätunnuksen ja siihen liittyvien tietojen poisto
