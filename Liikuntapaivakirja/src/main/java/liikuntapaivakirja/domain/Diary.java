
package liikuntapaivakirja.domain;


public class Diary {

    private User user;
    private double hour;
    private int week;
    private int day;
    private String content;
    private int weeklyGoal;

    public Diary(User user, double hour, int day, int week, String content) {
        this.user = user;
        this.hour = hour;
        this.day = day;
        this.week = week;
        this.content = content;
    }
    
    public void setWeeklyGoal(int goal) {
        this.weeklyGoal = goal;
    }
    
    public int getWeeklyGoal() {
        return this.weeklyGoal;
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
        return "Tunteja: " + this.hour + ", päivä: " + this.day + ", viikko: " + this.week + ", kuvaus: " + this.content;
    }
    
}
