package com.aurora.core.models.constants;

import static com.aurora.core.database.DBTableNames.RULES_COMBAT;

import androidx.room.Entity;
import androidx.room.Ignore;
import com.aurora.core.models.helpers.Rules;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = RULES_COMBAT, inheritSuperIndices = true)
public class RulesCombat extends Rules {

  @Ignore
  public RulesCombat() {
    super();
  }

  public RulesCombat(String name) {
    super(name);
  }

  public RulesCombat clone() {
    return new RulesCombat(getName());
  }
}