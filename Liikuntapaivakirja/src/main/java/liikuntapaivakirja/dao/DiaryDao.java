
package liikuntapaivakirja.dao;

import java.util.List;
import java.util.Map;
import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.User;


public interface DiaryDao {
    void create(Diary diary) throws Exception;
    List<Diary> getAll(User user) throws Exception;
    void setWeeklyGoal(int goal, String user) throws Exception;
    int getWeeklyGoal(String key) throws Exception;
    double userPointsWeek(String key, int week) throws Exception;
    int latestWeek(String key) throws Exception;
    Map bestUserPointsWeeks(String key) throws Exception;
    Map bestPointsWeeks() throws Exception;

}
