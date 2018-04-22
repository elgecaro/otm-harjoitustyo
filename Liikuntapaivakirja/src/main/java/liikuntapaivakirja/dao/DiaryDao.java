
package liikuntapaivakirja.dao;

import java.util.List;
import java.util.Map;
import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.User;


public interface DiaryDao {
    void create(Diary diary) throws Exception;
    List<Diary> getAll(User user) throws Exception;
    double userPointsWeek(String key, int week) throws Exception;
    int latestWeek(String key) throws Exception;
    Map bestUserPointsWeeks(String key) throws Exception;
    Map bestPointsWeeks() throws Exception;
    List<Diary> get15Latest(User user) throws Exception;

}
