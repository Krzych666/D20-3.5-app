package com.aurora.core.dao.settingspecific;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import com.aurora.core.helper.BaseDaO;
import com.aurora.core.models.helpers.Item;
import com.aurora.core.models.settingspecific.Races;

@Dao
public abstract class RacesDaO implements BaseDaO<Races> {

  @Query("SELECT COUNT(*) from Races")
  public abstract int countAllItems();

  @Query("SELECT Item_ID FROM Races")
  public abstract List<Integer> getAllIds();

  @Query("SELECT Name FROM Races")
  public abstract List<String> getAllNames();

  @Query("SELECT DISTINCT Source FROM Races")
  public abstract List<String> getAllSources();

  @Transaction
  public List<Races> getAllObjectsAsMergedObjectItem() {
    ArrayList<Races> result = new ArrayList<>(getAllObjectsAsObject());
    ArrayList<Item> resultItem = new ArrayList<>(getAllObjectsAsItem());
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Races")
  public abstract List<Races> getAllObjectsAsObject();

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM Races")
  public abstract List<Item> getAllObjectsAsItem(); // above doesn't show Item fields (but they are created/loaded)

  @Transaction
  public List<Races> getObjectsWithIdsAsMergedObjectItem(List<Integer> ids) {
    ArrayList<Races> result = new ArrayList<>(getObjectsWithIdsAsObject(ids));
    ArrayList<Item> resultItem = new ArrayList<>(getObjectsWithIdsAsItem(ids));
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Races WHERE Item_ID IN (:ids)")
  public abstract List<Races> getObjectsWithIdsAsObject(List<Integer> ids);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM Races WHERE Item_ID IN (:ids)")
  public abstract List<Item> getObjectsWithIdsAsItem(List<Integer> ids);

  @Query("SELECT * FROM Races WHERE Source == :source")
  public abstract List<Races> getObjectsWithSource(String source);

  @Query("SELECT * FROM Races WHERE Item_ID == :itemID")
  public abstract Races getObjectWithId(int itemID);

  @Query("SELECT * FROM Races WHERE Name == :name")
  public abstract Races getObjectWithName(String name);

  @Query("DELETE FROM Races")
  public abstract void deleteAll();
}