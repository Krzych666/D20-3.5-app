package com.aurora.core.dao.settingspecific;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import com.aurora.core.helper.BaseDaO;
import com.aurora.core.models.helpers.Item;
import com.aurora.core.models.settingspecific.Feats;

@Dao
public abstract class FeatsDaO implements BaseDaO<Feats> {

  @Query("SELECT COUNT(*) from Feats")
  public abstract int countAllItems();

  @Query("SELECT Item_ID FROM Feats")
  public abstract List<Integer> getAllIds();

  @Query("SELECT Name FROM Feats")
  public abstract List<String> getAllNames();

  @Query("SELECT DISTINCT Source FROM Feats")
  public abstract List<String> getAllSources();

  @Transaction
  public List<Feats> getAllObjectsAsMergedObjectItem() {
    ArrayList<Feats> result = new ArrayList<>(getAllObjectsAsObject());
    ArrayList<Item> resultItem = new ArrayList<>(getAllObjectsAsItem());
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Feats")
  public abstract List<Feats> getAllObjectsAsObject();

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM Feats")
  public abstract List<Item> getAllObjectsAsItem(); // above doesn't show Item fields (but they are created/loaded)

  @Transaction
  public List<Feats> getObjectsWithIdsAsMergedObjectItem(List<Integer> ids) {
    ArrayList<Feats> result = new ArrayList<>(getObjectsWithIdsAsObject(ids));
    ArrayList<Item> resultItem = new ArrayList<>(getObjectsWithIdsAsItem(ids));
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
      result.get(i).setSource(resultItem.get(i).getSource());
      result.get(i).setIdAsNameBackup(resultItem.get(i).getIdAsNameBackup());
    }
    return result;
  }

  @Query("SELECT * FROM Feats WHERE Item_ID IN (:ids)")
  public abstract List<Feats> getObjectsWithIdsAsObject(List<Integer> ids);

  @Query("SELECT * FROM Feats WHERE Item_ID IN (:ids)")
  public abstract List<Item> getObjectsWithIdsAsItem(List<Integer> ids);

  @Query("SELECT * FROM Feats WHERE Source == :source")
  public abstract List<Feats> getObjectsWithSource(String source);

  @Query("SELECT * FROM Feats WHERE Item_ID == :itemID")
  public abstract Feats getObjectWithId(int itemID);

  @Query("SELECT * FROM Feats WHERE Name == :name")
  public abstract Feats getObjectWithName(String name);

  @Query("DELETE FROM Feats")
  public abstract void deleteAll();
}