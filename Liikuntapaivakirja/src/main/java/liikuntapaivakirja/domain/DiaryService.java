
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
    
    public boolean createExcerise(double hour, int week, int day, String content) {
        Diary diary = new Diary(loggedIn, hour, week, day, content);
        try {
            diaryDao.create(diary);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean createWeeklyGoal(int goal) {
        try {
            diaryDao.setWeeklyGoal(goal, loggedIn);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Diary> getAll() throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return diaryDao.getAll(loggedIn);
    }
    
    public boolean login(String username) throws Exception {
        User user = userDao.findByUsername(username);
        if (user == null) {
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

}