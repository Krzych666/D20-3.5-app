package com.aurora.core.dao.userData;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import com.aurora.core.helper.BaseDAO;
import com.aurora.core.models.helpers.Item;
import com.aurora.core.models.userData.HeroDescription;
import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class HeroDescriptionDAO implements BaseDAO<HeroDescription> {

  @Query("SELECT COUNT(*) from HeroDescription")
  public abstract int countAllItems();

  @Query("SELECT Item_ID FROM HeroDescription")
  public abstract List<Integer> getAllIds();

  @Query("SELECT Name FROM HeroDescription")
  public abstract List<String> getAllNames();

  @Query("SELECT DISTINCT Source FROM HeroDescription")
  public abstract List<String> getAllSources();

  @Transaction
  public List<HeroDescription> getAllObjectsAsMergedObjectItem() {
    ArrayList<HeroDescription> result = new ArrayList<>(getAllObjectsAsObject());
    ArrayList<Item> resultItem = new ArrayList<>(getAllObjectsAsItem());
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM HeroDescription")
  public abstract List<HeroDescription> getAllObjectsAsObject();

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM HeroDescription")
  public abstract List<Item> getAllObjectsAsItem(); // above doesn't show Item fields (but they are created/loaded)

  @Transaction
  public List<HeroDescription> getObjectsWithIdsAsMergedObjectItem(List<Integer> Ids) {
    ArrayList<HeroDescription> result = new ArrayList<>(getObjectsWithIdsAsObject(Ids));
    ArrayList<Item> resultItem = new ArrayList<>(getObjectsWithIdsAsItem(Ids));
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM HeroDescription WHERE Item_ID IN (:Ids)")
  public abstract List<HeroDescription> getObjectsWithIdsAsObject(List<Integer> Ids);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM HeroDescription WHERE Item_ID IN (:Ids)")
  public abstract List<Item> getObjectsWithIdsAsItem(List<Integer> Ids);

  @Query("SELECT * FROM HeroDescription WHERE Source == :source")
  public abstract List<HeroDescription> getObjectsWithSource(String source);

  @Query("SELECT * FROM HeroDescription WHERE Item_ID == :itemID")
  public abstract HeroDescription getObjectWithId(int itemID);

  @Query("SELECT * FROM HeroDescription WHERE Name == :name")
  public abstract HeroDescription getObjectWithName(String name);

  @Query("DELETE FROM HeroDescription")
  public abstract void deleteAll();
}
