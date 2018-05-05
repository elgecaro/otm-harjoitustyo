package liikuntapaivakirja.domain;

import liikuntapaivakirja.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import liikuntapaivakirja.dao.DiaryEntryDao;

/**
 * Sovelluslogiikasta vastaava luokka.
 */

public class DiaryService {
    private DiaryEntryDao diaryEntryDao;
    private UserDao userDao;
    private User loggedIn;
    
    /**
     * Metodi asettaa luokkaan diaryEntryDao:n ja UserDao:n.
     * @param diaryEntryDao Sovelluksen diaryEntryDao
     * @param userDao Sovelluksen userDao
     */
    public DiaryService(DiaryEntryDao diaryEntryDao, UserDao userDao) {
        this.userDao = userDao;
        this.diaryEntryDao = diaryEntryDao;
    }
    
    /**
     * Metodi luo käyttäjän ilmoittaman uuden liikunnan, eli merkinnän.
     * @param hour Käyttäjän antama tunti
     * @param day Käyttäjän antama päivä
     * @param week Käyttäjän antama viikko
     * @param content Käyttäjän antama kuvaus
     * @return true jos liikunnan lisääminen onnistui, muuten false
     */
    public boolean createExercise(double hour, int day, int week, String content) {
        DiaryEntry entry = new DiaryEntry(loggedIn, hour, day, week, content);

        try {
            diaryEntryDao.create(entry);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Käyttäjän ilmoittama tavoite asetetaan uudeksi viikkotavoitteeksi.
     * @param goal Käyttäjän ilmoittama tavoite
     * @return true jos tavoitteen lisääminen onnistui, muuten false
     */
    public boolean createWeeklyGoal(double goal) {
        try {
            userDao.setWeeklyGoal(goal, loggedIn.getUsername());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Metodi hakee käyttäjän viikkotavoitteen.
     * @return Käyttäjän tavoite
     * @throws Exception jos ilmestyy virhe?
     */
    public double getWeeklyGoal() throws Exception {
        double goal = userDao.getWeeklyGoal(loggedIn.getUsername());
        return goal;
    }
    
    /**
     * Metodi listaa käyttäjän kaikki päiväkirja-merkinnät.
     * @return listan kaikista merkinnöistä
     * @throws Exception jos ilmestyy virhe?
     */
    public List<DiaryEntry> getAll() throws Exception {
        List<DiaryEntry> diaryEntrys = new ArrayList<>();
        diaryEntrys = diaryEntryDao.getAll(loggedIn);
        return diaryEntrys;
    }
    
    /**
     * Metodi listaa käyttäjän 15 viimeistä merkintää.
     * @return listan 15 viimeisestä merkinnästä
     * @throws Exception jos ilmestyy virhe?
     */
    public List<DiaryEntry> get15Latest() throws Exception {
        List<DiaryEntry> diaryEntrys = new ArrayList<>();
        diaryEntrys = diaryEntryDao.get15Latest(loggedIn);
        return diaryEntrys;
    }
    
    /**
     * Käyttäjän kirjautuminen sovellukseen.
     * @param username Käyttäjän ilmoittama käyttäjätunnus
     * @param password Käyttäjän ilmoittama salasana
     * @return true jos kirjautumienn onnistuu, muuten false
     * @throws Exception jos tapahtuu virhe?
     */
    public boolean login(String username, String password) throws Exception {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        if (userDao.usernameAndPasswordMatch(username, password) == false) {
            return false;
        }
        
        loggedIn = user;
        return true;
    }
    /**
     * Metodi joka palauttaa kirjautuneen käyttäjän.
     * @return Käyttäjä joka on kirjautunut. 
     */
    public User getLoggedUser() {
        return loggedIn;
    }
    
    /**
     * Metodi joka hakee kirjautuneen käyttäjän käyttäjänimen.
     * @return Kirjautuneen käyttäjän käyttäjänimen
     */
    public String getUsername() {
        return loggedIn.getUsername();
    }
    
    /**
     * Metodin avulla käyttäjä kirjaudutaan ulos.
     */
    public void logout() {
        loggedIn = null;
    }
    
    /**
     * Metodi luo uuden käyttäjän.
     * @param username Käyttäjän ilmoittama käyttäjätunnus
     * @param password Käyttäjän ilmoittama salasana
     * @return true jos uuden käyttäjän luominen onnistui, muuten false
     * @throws Exception jos tapahtuu virhe?
     */
    public boolean createUser(String username, String password) throws Exception {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        if (password.length() < 7) {
            return false;
        }
        User user = new User(username, password);
        userDao.create(user);
        return true;
    }
    
    /**
     * Metodi palauttaa tietyn viikon pisteet.
     * @param week Käyttäjän ilmoittama viikko
     * @return viikon pisteet
     * @throws Exception jos tapahtuu virhe?
     */
    public double getPointsWeek(int week) throws Exception {
        return diaryEntryDao.userPointsWeek(loggedIn.getUsername(), week);
    }
    
    /**
     * Meotdi hakee käyttäjän kaikki viikkopisteet.
     * @return Käyttäjän kaikki viikkopisteet (viikko + piste)
     * @throws Exception jos tapahtuu virhe?
     */
    public Map getAllWeekPoints() throws Exception {
        return diaryEntryDao.allPointsWeeks(loggedIn.getUsername());
    }
    
    /**
     * Metodi hakee käyttäjän viimeisen viikon päiväkirjasta.
     * @return Käyttäjän viimeisen viikon
     * @throws Exception jos tapahtuu virhe
     */
    public int getLatestWeek() throws Exception {
        return diaryEntryDao.latestWeek(loggedIn.getUsername());
    }
    
    /**
     * Metodi hakee käyttäjän 3 parhaat viikot viikkopisteiden perusteella.
     * @return Käyttäjän 3 parhaat viikot
     * @throws Exception jos tapahtuu virhe?
     */
    public Map getUsersBestWeeks() throws Exception {
        return diaryEntryDao.bestUserPointsWeeks(loggedIn.getUsername());
    }
    
    /**
     * Metodi hakee kaikkien käyttäjien kesken 3 parhaat viikot viikkopisteiden
     * perusteella.
     * @return 3 parasta viikkoa kaikkien käyttäjien kesken
     * @throws Exception jos taoahtuu virhe?
     */
    public Map getBestWeeks() throws Exception {
        return diaryEntryDao.bestPointsWeeks();
    }
    
    /**
     * Metodi poistaa tietyn merkinnän.
     * @param entry Käyttäjän ilmoittama merkintä
     * @throws Exception jos tapahtuu virhe?
     */
    public void deleteEntry(DiaryEntry entry) throws Exception {
        diaryEntryDao.delete(entry);
    }
    
    /**
     * Metodi, joka tarkistaa jos syötetty teksti on liukuluku.
     * @param s Käyttäjän antama syöte
     * @return true jos on liukuluku, muuten false
     */
    public boolean isDouble(String s) {
        try { 
            Double.parseDouble(s); 
        } catch (NumberFormatException | NullPointerException e) { 
            return false; 
        }
        return true;
    }
    
    /**
     * Metodi tarkistaa jos syötetty teksti on kokonaisluku.
     * @param s Käyttäjän antama syöte
     * @return true jos on kokonaisluku, muuten false
     */
    public boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch (NumberFormatException | NullPointerException e) { 
            return false; 
        }
        return true;
    }
    
    /**
     * Metodi tarkistaa, jos käyttäjän viikkotavoite on saavutettu.
     * @param points Käyttäjän viimeisen viikon pisteet
     * @return true jos tavoite on saavutettu, muuten false
     * @throws Exception jos tapahtuu virhe?
     */
    public boolean weeklyGoalAchieved(double points) throws Exception {
        if (getWeeklyGoal() == 0.0) {
            // Jos käyttäjällä ei ole viikkotavoitetta (= 0.0)
            return false;
        } else if (getPointsWeek(getLatestWeek()) >= points) {
            return true;
        }
        return false;
    }

}