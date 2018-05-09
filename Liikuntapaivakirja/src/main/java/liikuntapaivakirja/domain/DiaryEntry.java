package liikuntapaivakirja.domain;

/**
 * Päiväkirjamerkintää edustava luokka.
 */

public class DiaryEntry {

    private User user;
    private double hour;
    private int week;
    private int day;
    private String content;

    /**
     * Metodi luo uuden kirjoituksen päiväkirjaan.
     * @param user käyttäjä
     * @param hour käyttäjän antama tunti
     * @param day käyttäjän antama päivä
     * @param week käyttäjän antama viikko
     * @param content käyttäjän antama kuvaus
     */
    public DiaryEntry(User user, double hour, int day, int week, String content) {
        this.user = user;
        this.hour = hour;
        this.day = day;
        this.week = week;
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
    public String getUsername() {
        return user.getUsername();
    }

    public double getHour() {
        return hour;
    }

    public int getWeek() {
        return week;
    }

    public int getDay() {
        return day;
    }
    
    public String getContent() {
        return content;
    }
    
    @Override
    public String toString() {
        return "Tunteja: " + this.hour + "        Päivä: " + this.day + "        Viikko: " + this.week + "\n" + "Kuvaus: " + this.content;
    }
}
