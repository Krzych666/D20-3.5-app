package com.aurora.core.models.settingspecific;

import static com.aurora.core.database.DbColumnNames.SOURCE_COLUMN_NAME;
import static com.aurora.core.database.DbTableNames.SPELLS;

import lombok.Data;
import lombok.EqualsAndHashCode;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.aurora.core.models.Databases;
import com.aurora.core.models.helpers.Item;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = SPELLS, inheritSuperIndices = true,
    indices = {@Index(value = {SOURCE_COLUMN_NAME})},
    foreignKeys = @ForeignKey(entity = Databases.class,
        parentColumns = SOURCE_COLUMN_NAME, childColumns = SOURCE_COLUMN_NAME, onDelete = ForeignKey.CASCADE))
public class Spells extends Item {

  @Ignore
  public Spells() {
    super();
  }

  public Spells(String name,
      String source,
      String idAsNameBackup) {
    super(name, source, idAsNameBackup);
  }

  public Spells clone() {
    return new Spells(
        getName(),
        getSource(),
        getIdAsNameBackup());
  }
}