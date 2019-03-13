package com.aurora.core.dao.constants;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import com.aurora.core.helper.BaseDAO;
import com.aurora.core.models.constants.RulesCombat;
import com.aurora.core.models.helpers.Item;
import com.aurora.core.models.helpers.Rules;
import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class RulesCombatDAO implements BaseDAO<RulesCombat> {

  @Query("SELECT COUNT(*) from RulesCombat")
  public abstract int countItems();

  @Query("SELECT Item_ID FROM RulesCombat")
  public abstract List<Integer> getIds();

  @Query("SELECT Name FROM RulesCombat")
  public abstract List<String> getNames();

  @Override
  public List<String> getSources() {
    return null;
  }

  @Transaction
  public List<RulesCombat> getItemWithSuperFields() {
    ArrayList<RulesCombat> result = new ArrayList<>(getItems());
    ArrayList<Rules> resultItem = new ArrayList<>(getItemsAsRules());
    for (int i = 0; i < result.size(); i++) {
      result.get(i).setItemID(resultItem.get(i).getItemID());
      result.get(i).setName(resultItem.get(i).getName());
    }
    return result;
  }

  @Query("SELECT * FROM RulesCombat")
  public abstract List<RulesCombat> getItems();

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM RulesCombat")
  public abstract List<Rules> getItemsAsRules(); // above doesn't show Item fields (but they are created/loaded)

  @Override
  public List<Item> getItemsAsItem() {
    return null;
  }

  @Override
  public List<RulesCombat> getItemsWithSource(String source) {
    return null;
  }

  @Query("SELECT * FROM RulesCombat WHERE Item_ID == :itemID")
  public abstract RulesCombat getItemWithId(int itemID);

  @Query("SELECT * FROM RulesCombat WHERE Name == :name")
  public abstract RulesCombat getItemWithName(String name);

  @Query("DELETE FROM RulesCombat")
  public abstract void deleteAll();

}