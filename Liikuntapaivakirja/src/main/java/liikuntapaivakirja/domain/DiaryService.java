
package liikuntapaivakirja.domain;

import liikuntapaivakirja.dao.DiaryDao;
import liikuntapaivakirja.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import liikuntapaivakirja.dao.DbUserDao;

// Sovelluslogiikka, kirjautuminen, päiväkirjaan tuntien kirjaaminen ym

public class DiaryService {
    private DiaryDao diaryDao;
    private UserDao userDao;
    private User loggedIn;
    
    public DiaryService(DiaryDao diaryDao, UserDao userDao) {
        this.userDao = userDao;
        this.diaryDao = diaryDao;
    }
    
    public boolean createExcerise(double hour, int day, int week, String content) {
        Diary diary = new Diary(loggedIn, hour, day, week, content);
        try {
            diaryDao.create(diary);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean createWeeklyGoal(int goal) {
        try {
            diaryDao.setWeeklyGoal(goal, loggedIn.getUsername());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public int getWeeklyGoal() throws Exception {
        int goal = diaryDao.getWeeklyGoal(loggedIn.getUsername());
        return goal;
    }
    
    public List<Diary> getAll() throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        List<Diary> diaryEntrys = new ArrayList<>();
        diaryEntrys = diaryDao.getAll(loggedIn);
        return diaryEntrys;
    }
    
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
    
    public void logout() {
        loggedIn = null;
    }
    
    public boolean createUser(String username, String password) throws Exception {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username, password);
        userDao.create(user);
        return true;
    }
    
    public int getPointsWeek(int week) throws Exception {
        return diaryDao.userPointsWeek(loggedIn.getUsername(), week);
    }

}