package com.aurora.core.models.userData;

import static com.aurora.core.database.DBColumnNames.HERO_AGE_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_EYES_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_HAIR_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_HEIGHT_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_PARENT_ITEM_ID_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_PLAYER_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_SKIN_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.HERO_WEIGHT_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.ITEM_ID_COLUMN_NAME;
import static com.aurora.core.database.DBColumnNames.SOURCE_COLUMN_NAME;
import static com.aurora.core.database.DBTableNames.HERO_DESCRIPTION;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import com.aurora.core.models.Databases;
import com.aurora.core.models.helpers.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = HERO_DESCRIPTION, inheritSuperIndices = true,
    indices = {@Index(SOURCE_COLUMN_NAME), @Index(HERO_PARENT_ITEM_ID_COLUMN_NAME)},
    foreignKeys = {
        @ForeignKey(entity = Databases.class, parentColumns = SOURCE_COLUMN_NAME, childColumns = SOURCE_COLUMN_NAME, onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = HeroPlayer.class, parentColumns = ITEM_ID_COLUMN_NAME, childColumns = HERO_PARENT_ITEM_ID_COLUMN_NAME, onDelete = ForeignKey.CASCADE)}
)
public class HeroDescription extends Item {

  @Getter
  @Setter
  @ColumnInfo(name = HERO_PARENT_ITEM_ID_COLUMN_NAME)
  private Integer heroParentItemID;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_PLAYER_COLUMN_NAME)
  private String heroPlayer;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_AGE_COLUMN_NAME)
  private Integer heroAge;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_HEIGHT_COLUMN_NAME)
  private String heroHeight;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_WEIGHT_COLUMN_NAME)
  private String heroWeight;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_EYES_COLUMN_NAME)
  private String heroEyes;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_HAIR_COLUMN_NAME)
  private String heroHair;
  @Getter
  @Setter
  @ColumnInfo(name = HERO_SKIN_COLUMN_NAME)
  private String heroSkin;

  @Ignore
  public HeroDescription() {
    super();
  }

  public HeroDescription(String name,
      String source,
      String idAsNameBackup,
      Integer heroParentItemID,
      String heroPlayer,
      Integer heroAge,
      String heroHeight,
      String heroWeight,
      String heroEyes,
      String heroHair,
      String heroSkin) {
    super(name, source, idAsNameBackup);
    this.heroParentItemID = heroParentItemID;
    this.heroPlayer = heroPlayer;
    this.heroAge = heroAge;
    this.heroHeight = heroHeight;
    this.heroWeight = heroWeight;
    this.heroEyes = heroEyes;
    this.heroHair = heroHair;
    this.heroSkin = heroSkin;
  }

  public HeroDescription clone() {
    return new HeroDescription(
        getName(),
        getSource(),
        getIdAsNameBackup(),
        getHeroParentItemID(),
        getHeroPlayer(),
        getHeroAge(),
        getHeroHeight(),
        getHeroWeight(),
        getHeroEyes(),
        getHeroHair(),
        getHeroSkin());
  }
}