package com.aurora.core.models.settingSpecific;

import static com.aurora.core.database.DBColumnNames.SOURCE_COLUMN_NAME;
import static com.aurora.core.database.DBTableNames.RACES;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import com.aurora.core.database.DBColumnNames;
import com.aurora.core.models.Databases;
import com.aurora.core.models.helpers.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = RACES, inheritSuperIndices = true,
    indices = {@Index(value = {SOURCE_COLUMN_NAME})},
    foreignKeys = @ForeignKey(entity = Databases.class, parentColumns = SOURCE_COLUMN_NAME, childColumns = SOURCE_COLUMN_NAME, onDelete = ForeignKey.CASCADE))
public class Races extends Item {

  @Ignore
  public Races() {
    super();
  }

  public Races(String name,
      String source,
      String idAsNameBackup,
      String raceDescription,
      String raceAttributeModifiers,
      String raceSize,
      String raceSpeed,
      String raceFeats,
      String raceSkills,
      String raceLanguages,
      String favouriteClass) {
    super(name, source, idAsNameBackup);
    this.raceDescription = raceDescription;
    this.raceAttributeModifiers = raceAttributeModifiers;
    this.raceSize = raceSize;
    this.raceSpeed = raceSpeed;
    this.raceFeats = raceFeats;
    this.raceSkills = raceSkills;
    this.raceLanguages = raceLanguages;
    this.favouriteClass = favouriteClass;
  }

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_DESCRIPTION_COLUMN_NAME)
  private String raceDescription;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_ATTRIBUTE_MODIFIERS_COLUMN_NAME)
  private String raceAttributeModifiers;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_SIZE_COLUMN_NAME)
  private String raceSize;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_SPEED_COLUMN_NAME)
  private String raceSpeed;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_FEATS_COLUMN_NAME)
  private String raceFeats;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_SKILLS_COLUMN_NAME)
  private String raceSkills;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_LANGUAGES_COLUMN_NAME)
  private String raceLanguages;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.RACE_FAVOURITE_CLASS_COLUMN_NAME)
  private String favouriteClass;

  public Races clone() {
    return new Races(getName(),
        getSource(),
        getIdAsNameBackup(),
        getRaceDescription(),
        getRaceAttributeModifiers(),
        getRaceSize(),
        getRaceSpeed(),
        getRaceFeats(),
        getRaceSkills(),
        getRaceLanguages(),
        getFavouriteClass());
  }

}
