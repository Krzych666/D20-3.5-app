package com.aurora.core.dao.usables;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import com.aurora.core.helper.BaseDAO;
import com.aurora.core.models.helpers.Item;
import com.aurora.core.models.usables.Equipment;
import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class EquipmentDAO implements BaseDAO<Equipment> {

  @Query("SELECT COUNT(*) from Equipment")
  public abstract int countAllItems();

  @Query("SELECT Item_ID FROM Equipment")
  public abstract List<Integer> getAllIds();

  @Query("SELECT Name FROM Equipment")
  public abstract List<String> getAllNames();

  @Query("SELECT DISTINCT Source FROM Equipment")
  public abstract List<String> getAllSources();

  @Transaction
  public List<Equipment> getAllObjectsAsMergedObjectItem() {
    ArrayList<Equipment> result = new ArrayList<>(getAllObjectsAsObject());
    ArrayList<Item> resultItem = new ArrayList<>(getAllObjectsAsItem());
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Equipment")
  public abstract List<Equipment> getAllObjectsAsObject();

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM Equipment")
  public abstract List<Item> getAllObjectsAsItem(); // above doesn't show Item fields (but they are created/loaded)

  @Transaction
  public List<Equipment> getObjectsWithIdsAsMergedObjectItem(List<Integer> Ids) {
    ArrayList<Equipment> result = new ArrayList<>(getObjectsWithIdsAsObject(Ids));
    ArrayList<Item> resultItem = new ArrayList<>(getObjectsWithIdsAsItem(Ids));
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Equipment WHERE Item_ID IN (:Ids)")
  public abstract List<Equipment> getObjectsWithIdsAsObject(List<Integer> Ids);

  @Query("SELECT * FROM Equipment WHERE Item_ID IN (:Ids)")
  public abstract List<Item> getObjectsWithIdsAsItem(List<Integer> Ids);

  @Query("SELECT * FROM Equipment WHERE Source == :source")
  public abstract List<Equipment> getObjectsWithSource(String source);

  @Query("SELECT * FROM Equipment WHERE Item_ID == :itemID")
  public abstract Equipment getObjectWithId(int itemID);

  @Query("SELECT * FROM Equipment WHERE Name == :name")
  public abstract Equipment getObjectWithName(String name);

  @Query("DELETE FROM Equipment")
  public abstract void deleteAll();
}
