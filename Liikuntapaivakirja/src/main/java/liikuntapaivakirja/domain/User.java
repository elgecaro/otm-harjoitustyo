
package liikuntapaivakirja.domain;

public class User {
    
    private String username;
    private String password;
    private int weeklyGoal;
    
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
    
//    public void setWeeklyGoal(int goal) {
//        this.weeklyGoal = goal;
//    }
//    
//    public int getWeeklyGoal() {
//        return weeklyGoal;
//    }
    
}
