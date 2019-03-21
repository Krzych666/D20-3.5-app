package com.aurora.core.models.typeHelpers;

import com.aurora.core.database.DatabaseHolder;
import com.aurora.core.helper.BaseDAO;
import com.aurora.core.models.helpers.CoreHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface CoreTypeHelper<E extends Enum<E>, CH extends CoreHelper> {

  static <E extends Enum<E> & CoreTypeHelper<E, CH>, CH extends CoreHelper> E fromString(String itemTypeString, Class<E> type) {
    for (E itemType : type.getEnumConstants()) {
      if (itemType.toString().equalsIgnoreCase(itemTypeString)) {
        return itemType;
      }
    }
    throw new IllegalArgumentException("No ItemType with value " + itemTypeString + " found");
  }

  static <E extends Enum<E> & CoreTypeHelper<E, CH>, CH extends CoreHelper> boolean contains(String name, Class<E> type) {
    for (E itemType : type.getEnumConstants()) {
      if (itemType.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  BaseDAO<CH> getDAO(DatabaseHolder databaseHolder);

  List<? extends CH> getDatabaseList(DatabaseHolder databaseHolder);

  Map<Integer, ? extends CH> getDatabaseMap(DatabaseHolder databaseHolder);

  default List<CH> getAllFromDatabase(DatabaseHolder databaseHolder) {
    return getDAO(databaseHolder).getAllObjectsAsObject();
  }

  CoreHelper getNewObject();

  void fromHolderToDatabase(DatabaseHolder databaseHolder);

  void fromDatabaseToHolder(DatabaseHolder databaseHolder);

  default void deleteAll(DatabaseHolder databaseHolder) {
    deleteAllFromDatabase(databaseHolder);
    deleteAllFromHolder(databaseHolder);
  }

  default void deleteAllFromHolder(DatabaseHolder databaseHolder) {
    getDatabaseList(databaseHolder).clear();
    getDatabaseMap(databaseHolder).clear();
  }

  default void deleteAllFromDatabase(DatabaseHolder databaseHolder) {
    getDAO(databaseHolder).deleteAll();
  }

  static List<? extends CoreTypeHelper> values() {
    List<CoreTypeHelper> out = new ArrayList<>();
    out.addAll(Arrays.asList(RulesType.values()));
    out.addAll(Arrays.asList(ItemType.values()));
    return out;
  }
}
