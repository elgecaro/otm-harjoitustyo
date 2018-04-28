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
    public boolean createWeeklyGoal(int goal) {
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
    public int getWeeklyGoal() throws Exception {
        int goal = userDao.getWeeklyGoal(loggedIn.getUsername());
        return goal;
    }
    
    /**
     * Metodi listaa käyttäjän kaikki päiväkirja-merkinnät.
     * @return listan kaikista merkinnöistä
     * @throws Exception jos ilmestyy virhe?
     */
    public List<DiaryEntry> getAll() throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
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
        if (loggedIn == null) {
            return new ArrayList<>();
        }
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

    public User getLoggedUser() {
        return loggedIn;
    }
    
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
     * Meotdi hakee käyttäjän kaikki viikkopisteet
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

}