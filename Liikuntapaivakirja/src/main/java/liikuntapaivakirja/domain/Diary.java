
package liikuntapaivakirja.domain;


public class Diary {

    private User user;
    private double hour;
    private int week;
    private int day;
    private String content;

    public Diary(User user, double hour, int week, int day, String content) {
        this.user = user;
        this.hour = hour;
        this.week = week;
        this.day = day;
        this.content = content;
    }

    public Diary(User user, double hour, int week, int day) {
        this.user = user;
        this.hour = hour;
        this.week = week;
        this.day = day;
        this.content = "";
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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

}
