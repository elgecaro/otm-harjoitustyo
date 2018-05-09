package liikuntapaivakirja.domain;

/**
 * Käyttäjää edustava luokka.
 */

public class User {
    
    private String username;
    private String password;
    private int weeklyGoal;
    
    /**
     * Metodi joka luo uuden käyttäjän.
     * @param username käyttäjän ilmoittama käyttäjänimi
     * @param password käyttäjän ilmoittama salasana
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setWeeklyGoal(int goal) {
        this.weeklyGoal = goal;
    }
    
    public int getWeeklyGoal() {
        return this.weeklyGoal;
    }
}
