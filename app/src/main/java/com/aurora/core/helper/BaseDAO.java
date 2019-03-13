package com.aurora.core.helper;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import com.aurora.core.models.helpers.Item;
import java.util.List;


public interface BaseDAO<T> {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
//or replace?
  long insert(T object);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
//or replace?
  List<Long> insertAll(List<T> objects);

  @Update
  void update(T object);

  @Delete
  void delete(T object);

  int countItems();

  List<Integer> getIds();

  List<String> getNames();

  List<String> getSources();

  List<T> getItemWithSuperFields();

  List<T> getItems();

  List<Item> getItemsAsItem();

  List<T> getItemsWithSource(String source);

  T getItemWithId(int id);

  T getItemWithName(String name);

  void deleteAll();
}
