# Arkkitehtuurikuvaus # 

## Rakenne ##
Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

![rakennekaavio](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/rakennekaavio.png)

Pakkaus liikuntapaivakirja.ui sisältää JavaFX:llä toteutetun **käyttöliittymän**, liikuntapaivakirja.domain **sovelluslogiikan** ja liikuntapaivakirja.dao **tietojen pysyväistallennuksesta** vastaavat luokat.


## Käyttöliittymä ##

Käyttöliittymä sisältää viisi erillistä näkymää:
* startScene: aloitusnäkymä
* loginScene: kirjautuminen
* newUserScene: uuden käyttäjän luominen
* loggedInScene: kirjautuneen käyttäjän näkymä
* allEntriesScene: kaikki merkinnät

Jokainen näistä on toteutettu omana metodina ja omalla Scene-oliona, ja näkymistä yksi kerrallaan on näkyvinä. Käyttöliittymä on rakennettu ohjelmallisesti luokassa *[liikuntapaivakirja.ui.Main](https://github.com/elgecaro/otm-harjoitustyo/blob/master/Liikuntapaivakirja/src/main/java/liikuntapaivakirja/ui/Main.java)*.

Käyttöliittymä on pyritty eristämään sovelluslogiiksta, eli se kutsuu *diaryService*-sovelluslogiikan metodeja.

Kun päiväkirjaan lisätään uusi merkintä, kutsutaan *redrawAll*-metodia, joka kutsuu *redrawDiaryList*, *redrawUserHighscoreList*, *redrawHighscoreList*, *redrawWeeklyPoint* sekä *redrawWeeklyGoal* -metodeja, jotka renderöivät sovelluksen kaikki merkintöihin liittyvät listat uudelleen. Kun käyttäjä lisää uuden merkinnän, uusi merkintä muuttaa listan kaikista merkinnöistä sekä käyttäjän viikottaiiset pisteet. Tämän lisäksi merkintä voi muuttaa tuloslistoja (käyttäjän oman sekä kaikkien käyttäjien kesken) sekä viikkotavoitteen (esim. jos käyttäjä saavuttaa viikkotavoitteen). 

Tämä tapahtuu myös kaikki merkinnät-näkymässä (*allEntriesScene*). Jos käyttäjä poistaa merkinnän, kutsutaan *redrawAllDiaryList* sekä *redrawAllWeekPoints*-metodeja, koska lista merkinnöistä sekä kaikkien viikkojen pisteet muuttuvat.


## Sovelluslogiikka ##
Sovelluksen loogisen datamallin muodostavat luokat User ja DiaryEntry, jotka kuvaavat käyttäjiä ja käyttäjien päiväkirjamerkintöjä:

![luokkakaavio1](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/luokkakaavio1.png)

Toiminnallisista kokonaisuuksista vastaa luokkan DiaryService ainoa olio. Luokka tarjoaa kaikille käyttäliittymän toiminnoille omat metodit. Näitä ovat esimerkiksi:
* boolean createUser(String username, String password)
* boolean login(String username, String password)
* createExercise(double hour, int day, int week, String content)
* boolean createWeeklyGoal(double goal)
* double getWeeklyGoal()
* List<DiaryEntry> get15Latest()
* Map getAllWeekPoints()
* void deleteEntry(DiaryEntry entry)

DiaryService pääsee käsiksi käyttäjiin ja päiväkirjamerkintöihin tietojen tallennuksesta vastaavaan pakkauksessa (*liikuntapaivakirja.dao*), sijaitsevien rajapintojen *DiaryEntryDao* sekä *UserDao* toteuttavien luokkien kautta. 

DiaryServicen ja ohjelman muiden osien suhdetta kuvaava luokka/pakkauskaavio:

![luokka-pakkauskaavio](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/luokka-pakkauskaavio.png)

## Tietojen pysyväistallennus ##
Tulossa

![tietokannan rakenne](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/relaatiokaavio_db.png)

## Päätoiminnallisuudet ##

### Käyttäjän kirjaantuminen ###

Kun kirjautumisnäkymässä klikkaa painiketta *loginButton*, etenee sovellus seuraavasti:
![kirjautuminen](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_kirjaudu.png)

Painikkeen painamiseen reagoiva tapahtumankäsittelijä kutsuu metodia *loginUsername* ja asettaa tällöin uuden näkymän käyttäjälle. *loginUserButton*-painikkeen painamiseen reagoiva tapahtumakäsittelijä kutsuu sitten sovelluslogiikan diaryService metodia *login(username, password)* antaen parametriksi käyttäjän ilmoittama käyttäjätunnus ja salasana. Sovelluslogiikka etsii userDao:n avulla jos käyttäjänimi jo on olemassa, ja jos on, niin tarkistaa taas userDao:n avulla jos käyttäjänimi ja salasana täsmää *usernameAndPasswordMatch(username, password)*-metodin avulla. Jos tämä palauttaa arvon *true*, kirjautuminen onnistuu, ja näkymä vaihdetaan sovelluksen *loggedIn*-näkymään. 

### Uuden käyttäjän luominen ###

Kun kirjautumisnäkymässä klikkaa painiketta *createButton*, etenee sovellus seuraavasti:

![uusi käyttäjä](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_uusiKayttaja.png)

Painikkeen painamiseen reagoiva tapahtumankäsittelijä kutsuu metodia *createUser* ja asettaa tällöin uuden näkymän käyttäjälle. *createUserButton*-painikkeen painamiseen reagoiva tapahtumakäsittelijä kontrolloi ensin jos käyttäjätunnus ja salasana ovat oikean pituiset, ja kutsuu sitten sovelluslogiikan diaryService metodia *create(username, password)* antaen parametriksi käyttäjän ilmoittama käyttäjätunnus ja salasana. Sovelluslogiikka kontrolloi userDao:n avulla jos käyttäjätunnus on jo olemassa, joka palauttaa arvon *null* jos ei ole olemassa. Tällöin sovelluslogiikka luo uuden User-olion ja tallettaa sen kutsumalla userDao:n metodia *create(user)*. Tämä palauttaa arvon *true*:n, ja käyttöliittymän näkymä vaihdetaan *loginUser*-näkymään, eli kirjautumisnäkymään. 

### Päiväkirjamerkinnän lisääminen ("Liikunnan lisääminen") ###

Kun kirjautuneena oleva käyttäjä klikkaa painiketta *createExercise*, etenee sovellus seuraavasti:

![Päiväkirjamerkinnän lisääminen](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_merkinnanLisaaminen.png)

Tapahtumakäsittelijä tarkistaa ensin jos käyttäjän ilmoittaman tietojen muodot ovat oikeat (*isDouble* ja *isInteger*-metodien avulla), ja jos ovat, niin muuttavat nämä *String*-muodosta *Double* ja *Integer* muotoihin (*hour = parseDouble(hourS) mm*). Tämän jälkeen tapahtumakäsittelijä kutsuu sovelluslogiikan metodia *createExercise(hour, day, week, content)* annettujen parametrien mukaan. Sovelluslogiikka luo silloin uuden *Diary*-olion, ja tallettaa sen diaryDao:n avulla *create(diary)*-metodilla. Sovelluslogiikka palauttaa arvon *true* jos liikunnan lisääminen onnistui, ja tämän jälkeen käyttöliittymä päivittää näytettävät kirjoitukset/liikunnat metodilla *redrawDiaryList*, käyttäjän ja sovelluksen tuloslistat (*redrawHighscorelist* ja *redrawUserHighscoreList*) sekä käyttäjän viikkopisteet (*redrawWeeklyPoints*)

Tästä seurauksena on se, että käyttöliittymä päivittää näytettävät todot kutsumalla omaa metodiaan redrawTodolist.

### Muut toiminallisuudet ###
Tulossa

## Ohjelman rakenteeseen jääneet heikkoudet ##
Tulossa
