
package liikuntapaivakirja.dao;

import liikuntapaivakirja.domain.User;
import java.util.List;

/**
 * DAO-rajapinta käyttäjille.
 */

public interface UserDao {
    
    /**
     * Metodi luo uuden käyttäjän.
     * @param user Sovelluksen käyttäjä
     * @return Käyttäjän User-muodossa
     * @throws Exception jos tapahtuu virhe?
     */
    User create(User user) throws Exception;
    
    /**
     * Metodi hakee tietyn käyttäjän käyttäjänimen perusteella.
     * @param username Käyttäjän käyttäjänimi
     * @return Käyttäjän User-muodossa
     * @throws Exception jos tapahtuu virhe?
     */
    User findByUsername(String username) throws Exception;
    
    /**
     * Metodi hakee kaikki käyttäjät.
     * @return listan kaikista käyttäjistä
     * @throws Exception jos tapahtuu virhe?
     */
    List<User> findAll() throws Exception;
    
    /**
     * Metodi tarkistaa, että käyttäjänimi ja salasana täsmäävät.
     * @param username Käyttäjän käyttäjänimi 
     * @param password Käyttäjän salasana
     * @return true jos käyttäjänimi ja salasana täsmäävät, muuten false
     * @throws Exception jos tapahtuu virhe?
     */
    boolean usernameAndPasswordMatch(String username, String password)throws Exception;
    
    /**
     * Metodi luo käyttäjälle uuden viikkotavoitteen.
     * @param goal Käyttäjän antama piste-tavoite
     * @param username Käyttäjän käyttäjänimi
     * @throws Exception jos tapahtuu virhe?
     */
    void setWeeklyGoal(double goal, String username) throws Exception;
    
    /**
     * Metodi hakee käyttäjän viikkotavoitteen.
     * @param username Käyttäjän käyttäjänimi
     * @return käyttäjän viikkotavoite
     * @throws Exception jos tapahtuu virhe?
     */
    double getWeeklyGoal(String username) throws Exception;
    
}