
package liikuntapaivakirja.dao;

import java.util.List;
import java.util.Map;
import liikuntapaivakirja.domain.DiaryEntry;
import liikuntapaivakirja.domain.User;

/**
 * DAO-rajapinta päiväkirjamerkinnöille.
 */

public interface DiaryEntryDao {
    /**
     * Metodi joka luo uuden merkinnän.
     * @param entry Käyttäjän kirjoittama merkintä
     * @throws Exception jos tapahtuu virhe?
     */
    void create(DiaryEntry entry) throws Exception;
    
    /**
     * Metodi poistaa merkinnän.
     * @param entry Käyttäjän valitsema merkintä
     * @throws Exception jos tapahtuu virhe?
     */
    void delete(DiaryEntry entry) throws Exception;
    
    /**
     * Metodi hakee käyttäjän kaikki merkinnät.
     * @param user Sovelluksen käyttäjä
     * @return lista, joka koostuu käyttäjän kirjoitetuista merkinnöistä
     * @throws Exception jos tapahtuu virhe?
     */
    List<DiaryEntry> getAll(User user) throws Exception;
    
    /**
     * Metodi hakee käyttäjän viikkopisteen tietystä viikosta.
     * @param user Sovelluksen käyttäjä
     * @param week Viikon numero
     * @return viikon pisteen
     * @throws Exception jos tapahtuu virhe?
     */
    double userPointsWeek(String user, int week) throws Exception;
    
    /**
     * Metodi hakee käyttäjän viimeisen kirjoittama viikon merkinnöistä.
     * @param user Sovelluksen käyttäjä
     * @return viimeisen viikon
     * @throws Exception jos tapahtuu virhe?
     */
    int latestWeek(String user) throws Exception;
    
    /**
     * Metodi hakee käyttäjän 3 parasta viikkopistettä ja viikkoa.
     * @param user Sovelluksen käyttäjä
     * @return käyttäjän 3 parasta viikkopistettä ja viikkoa
     * @throws Exception jos tapahtuu virhe?
     */
    Map bestUserPointsWeeks(String user) throws Exception;
    
    /**
     * Metodi hakee kaikkien käyttäjien kesken 3 parasta viikkopistettä.
     * @return 3 parasta käyttäjää ja niiden parhaat viikkopisteet
     * @throws Exception jos tapahtuu virhe?
     */
    Map bestPointsWeeks() throws Exception;
    
    /**
     * Metodi hakee käyttäjän kaikki viikottaiset viikkopisteet.
     * @param user Sovelluksen käyttäjä
     * @return käyttäjän kaikki viikot ja niiden pisteet
     * @throws Exception jos tapahtuu virhe?
     */
    Map allPointsWeeks(String user) throws Exception;
    
    /**
     * Metodi hakee käyttäjän 15 viimeiset merkinnät.
     * @param user Sovelluksen käyttäjä
     * @return lista 15 viimeisestä merkinnästä
     * @throws Exception jos tapahtuu virhe?
     */
    List<DiaryEntry> get15Latest(User user) throws Exception;

}
