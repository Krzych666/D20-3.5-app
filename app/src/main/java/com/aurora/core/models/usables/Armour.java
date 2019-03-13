package com.aurora.core.models.usables;

import static com.aurora.core.database.DBColumnNames.SOURCE_COLUMN_NAME;
import static com.aurora.core.database.DBTableNames.ARMOUR;

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
@Entity(tableName = ARMOUR, inheritSuperIndices = true,
    indices = {@Index(value = {SOURCE_COLUMN_NAME})},
    foreignKeys = @ForeignKey(entity = Databases.class, parentColumns = SOURCE_COLUMN_NAME, childColumns = SOURCE_COLUMN_NAME, onDelete = ForeignKey.CASCADE))
public class Armour extends Item {

  @Ignore
  public Armour() {
    super();
  }

  public Armour(String name,
      String source,
      String idAsNameBackup,
      String armourPrice,
      String armourDeflection,
      String armourMaxDexterityBonus,
      String armourPenalty,
      String armourArcaneFailure,
      String armourMaxSpeed,
      String armourWeight,
      String armourSpecialProperties,
      String armourMaterial,
      String armourMagicImprovements) {
    super(name, source, idAsNameBackup);
    this.armourPrice = armourPrice;
    this.armourDeflection = armourDeflection;
    this.armourMaxDexterityBonus = armourMaxDexterityBonus;
    this.armourPenalty = armourPenalty;
    this.armourArcaneFailure = armourArcaneFailure;
    this.armourMaxSpeed = armourMaxSpeed;
    this.armourWeight = armourWeight;
    this.armourSpecialProperties = armourSpecialProperties;
    this.armourMaterial = armourMaterial;
    this.armourMagicImprovements = armourMagicImprovements;
  }

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_PRICE_COLUMN_NAME)
  private String armourPrice;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_DEFLECTION_COLUMN_NAME)
  private String armourDeflection;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_MAX_DEXTERITY_BONUS_COLUMN_NAME)
  private String armourMaxDexterityBonus;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_PENALTY_COLUMN_NAME)
  private String armourPenalty;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_ARCANE_FAILURE_COLUMN_NAME)
  private String armourArcaneFailure;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_MAX_SPEED_COLUMN_NAME)
  private String armourMaxSpeed;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_WEIGHT_COLUMN_NAME)
  private String armourWeight;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_SPECIAL_PROPERTIES_COLUMN_NAME)
  private String armourSpecialProperties;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_MATERIAL_COLUMN_NAME)
  private String armourMaterial;

  @Getter
  @Setter
  @ColumnInfo(name = DBColumnNames.ARMOUR_MAGIC_IMPROVEMENTS_COLUMN_NAME)
  private String armourMagicImprovements;

  public Armour clone() {
    return new Armour(
        getName(),
        getSource(),
        getIdAsNameBackup(),
        getArmourPrice(),
        getArmourDeflection(),
        getArmourMaxDexterityBonus(),
        getArmourPenalty(),
        getArmourArcaneFailure(),
        getArmourMaxSpeed(),
        getArmourWeight(),
        getArmourSpecialProperties(),
        getArmourMaterial(),
        getArmourMagicImprovements());
  }
}