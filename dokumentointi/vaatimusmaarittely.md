# Alustava määrittelydokumentti #

## Sovelluksen tarkoitus ##
Sovelluksen tarkoitus on toimia liikuntapäiväkirjana niin, että käyttäjä voi kirjata liikuntamäärän sovelluksessa, jonka avulla voi seurata omaa liikuntaa ja asettaa itselleen viikko-tavoitteet. Sovellusta on mahdollista käyttää useamman rekisteräityneen käyttäjän, joilla kaikilla on omat yksilölliset "päiväkirjat" tietoineen ja tavoitteineen.

## Käyttäjät ##
Sovelluksella on (ainakin alkuvaiheessa) vain yksi käyttäjärooli, normaali käyttäjä, mutta myöhemmin olisi mahdollisesti mahdollista lisätä "trainer-rooli", joka esimerkiksi voisi nähdä tiettyjen käyttäjien tiedot.

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
   * Käyttäjä voi myös nähdä vanhemmat tiedot + yksittäiset päivät (?)
* Käyttäjä näkee tuloslistoja:
   * Käyttäjän omat 3 parasta viikkotulosta (tunnit ja pisteet)
   * Mahdollisesti myös 3 parasta viikkotulosta sovelluksen kaikkien käyttäjien kesken
* Käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita ##
Tulossa
