
package liikuntapaivakirja.dao;

import java.util.List;
import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.User;


public interface DiaryDao {
    void create(Diary diary) throws Exception;
    List<Diary> getAll(User user) throws Exception;
    void setWeeklyGoal(int goal, User user) throws Exception;

}
