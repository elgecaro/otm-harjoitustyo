
package liikuntapaivakirja.dao;

import java.util.List;
import java.util.Map;
import liikuntapaivakirja.domain.DiaryEntry;
import liikuntapaivakirja.domain.User;


public interface DiaryEntryDao {
    void create(DiaryEntry entry) throws Exception;
    List<DiaryEntry> getAll(User user) throws Exception;
    double userPointsWeek(String user, int week) throws Exception;
    int latestWeek(String user) throws Exception;
    Map bestUserPointsWeeks(String user) throws Exception;
    Map bestPointsWeeks() throws Exception;
    List<DiaryEntry> get15Latest(User user) throws Exception;

}
