
package liikuntapaivakirja.dao;

import java.util.List;
import liikuntapaivakirja.domain.Diary;


public interface DiaryDao {
    Diary create(Diary diary) throws Exception;
    List<Diary> getAll();
    void setDone(int id) throws Exception;

}
