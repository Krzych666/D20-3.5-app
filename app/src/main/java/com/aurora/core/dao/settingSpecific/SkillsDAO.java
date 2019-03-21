package com.aurora.core.dao.settingSpecific;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import com.aurora.core.helper.BaseDAO;
import com.aurora.core.models.helpers.Item;
import com.aurora.core.models.settingSpecific.Skills;
import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class SkillsDAO implements BaseDAO<Skills> {

  @Query("SELECT COUNT(*) from Skills")
  public abstract int countAllItems();

  @Query("SELECT Item_ID FROM Skills")
  public abstract List<Integer> getAllIds();

  @Query("SELECT Name FROM Skills")
  public abstract List<String> getAllNames();

  @Query("SELECT DISTINCT Source FROM Skills")
  public abstract List<String> getAllSources();

  @Transaction
  public List<Skills> getAllObjectsAsMergedObjectItem() {
    ArrayList<Skills> result = new ArrayList<>(getAllObjectsAsObject());
    ArrayList<Item> resultItem = new ArrayList<>(getAllObjectsAsItem());
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Skills")
  public abstract List<Skills> getAllObjectsAsObject();

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM Skills")
  public abstract List<Item> getAllObjectsAsItem(); // above doesn't show Item fields (but they are created/loaded)

  @Transaction
  public List<Skills> getObjectsWithIdsAsMergedObjectItem(List<Integer> Ids) {
    ArrayList<Skills> result = new ArrayList<>(getObjectsWithIdsAsObject(Ids));
    ArrayList<Item> resultItem = new ArrayList<>(getObjectsWithIdsAsItem(Ids));
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Skills WHERE Item_ID IN (:Ids)")
  public abstract List<Skills> getObjectsWithIdsAsObject(List<Integer> Ids);

  @Query("SELECT * FROM Skills WHERE Item_ID IN (:Ids)")
  public abstract List<Item> getObjectsWithIdsAsItem(List<Integer> Ids);

  @Query("SELECT * FROM Skills WHERE Source == :source")
  public abstract List<Skills> getObjectsWithSource(String source);

  @Query("SELECT * FROM Skills WHERE Item_ID == :itemID")
  public abstract Skills getObjectWithId(int itemID);

  @Query("SELECT * FROM Skills WHERE Name > :name")
  public abstract Skills getObjectWithName(String name);

  @Query("DELETE FROM Skills")
  public abstract void deleteAll();
}
