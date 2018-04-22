# Arkkitehtuurikuvaus # 

## Rakenne ##
Tulossa

## Käyttöliittymä ##
Tulossa

## Sovelluslogiikka ##
Tulossa

DiaryServicen ja ohjelman muiden osien suhdetta kuvaava luokka/pakkauskaavio:

![luokka-pakkauskaavio](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/luokka-pakkauskaavio.png)

## Tietojen pysyväistallennus ##
Tulossa

## Päätoiminnallisuudet ##

### Käyttäjän kirjaantuminen ###

Kun kirjautumisnäkymässä klikkaa painiketta *loginButton*, etenee sovellus seuraavasti:
![kirjautuminen](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_kirjaudu.png)

Painikkeen painamiseen reagoiva tapahtumankäsittelijä kutsuu metodia *loginUsername* ja asettaa tällöin uuden näkymän käyttäjälle. *loginUserButton*-painikkeen painamiseen reagoiva tapahtumakäsittelijä kutsuu sitten sovelluslogiikan diaryService metodia *login(username, password)* antaen parametriksi käyttäjän ilmoittama käyttäjätunnus ja salasana. Sovelluslogiikka etsii userDao:n avulla jos käyttäjänimi jo on olemassa, ja jos on, niin tarkistaa taas userDao:n avulla jos käyttäjänimi ja salasana täsmää *usernameAndPasswordMatch(username, password)*-metodin avulla. Jos tämä palauttaa arvon *true*, kirjautuminen onnistuu, ja näkymä vaihdetaan sovelluksen *loggedIn*-näkymään. 

### Uuden käyttäjän luominen ###

Kun kirjautumisnäkymässä klikkaa painiketta *createButton*, etenee sovellus seuraavasti:

![uusi käyttäjä](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_uusiKayttaja.png)

Painikkeen painamiseen reagoiva tapahtumankäsittelijä kutsuu metodia *createUser* ja asettaa tällöin uuden näkymän käyttäjälle. *createUserButton*-painikkeen painamiseen reagoiva tapahtumakäsittelijä kontrolloi ensin jos käyttäjätunnus ja salasana ovat oikean pituiset, ja kutsuu sitten sovelluslogiikan diaryService metodia *create(username, password)* antaen parametriksi käyttäjän ilmoittama käyttäjätunnus ja salasana. Sovelluslogiikka kontrolloi userDao:n avulla jos käyttäjätunnus on jo olemassa, joka palauttaa arvon *null* jos ei ole olemassa. Tällöin sovelluslogiikka luo uuden User-olion ja tallettaa sen kutsumalla userDao:n metodia *create(user)*. Tämä palauttaa arvon *true*:n, ja käyttöliittymän näkymä vaihdetaan *loginUser*-näkymään, eli kirjautumisnäkymään. 

### Liikunnan lisääminen/päiväkirjaan kirjoittaminen ###

Kun kirjautuneena oleva käyttäjä klikkaa painiketta *createExercise*, etenee sovellus seuraavasti:

![liikunnan lisääminen](https://github.com/elgecaro/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_lisaaLiikunta.png)

Tapahtumakäsittelijä tarkistaa ensin jos käyttäjän ilmoittaman tietojen muodot ovat oikeat (*isDouble* ja *isInteger*-metodien avulla), ja jos ovat, niin muuttavat nämä *String*-muodosta *Double* ja *Integer* muotoihin (*hour = parseDouble(hourS) mm*). Tämän jälkeen tapahtumakäsittelijä kutsuu sovelluslogiikan metodia *createExercise(hour, day, week, content)* annettujen parametrien mukaan. Sovelluslogiikka luo silloin uuden *Diary*-olion, ja tallettaa sen diaryDao:n avulla *create(diary)*-metodilla. Sovelluslogiikka palauttaa arvon *true* jos liikunnan lisääminen onnistui, ja tämän jälkeen käyttöliittymä päivittää näytettävät kirjoitukset/liikunnat metodilla *redrawDiaryList*, käyttäjän ja sovelluksen tuloslistat (*redrawHighscorelist* ja *redrawUserHighscoreList*) sekä käyttäjän viikkopisteet (*redrawWeeklyPoints*)

Tästä seurauksena on se, että käyttöliittymä päivittää näytettävät todot kutsumalla omaa metodiaan redrawTodolist.

### Muut toiminallisuudet ###
Tulossa

## Ohjelman rakenteeseen jääneet heikkoudet ##
Tulossa
