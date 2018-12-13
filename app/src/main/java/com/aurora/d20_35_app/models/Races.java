package com.aurora.d20_35_app.models;

import com.aurora.d20_35_app.helper.Item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = "Races", inheritSuperIndices = true)
public class Races extends Item {

    @Ignore
    public Races() {
        super();
    }

    public Races(String name, String source, String raceDescription, String raceAttributeModifiers, String raceSize, String raceSpeed, String raceFeats, String raceSkills, String raceLanguages, String favouriteClass) {
        super(name, source);
        this.raceDescription = raceDescription;
        this.raceAttributeModifiers = raceAttributeModifiers;
        this.raceSize = raceSize;
        this.raceSpeed = raceSpeed;
        this.raceFeats = raceFeats;
        this.raceSkills = raceSkills;
        this.raceLanguages = raceLanguages;
        this.favouriteClass = favouriteClass;
    }

    @Ignore
    public static final String raceDescriptionColumnName = "RaceDescription";
    @Ignore
    public static final String raceAttributeModifiersColumnName = "RaceAttributeModifiers";
    @Ignore
    public static final String raceSizeColumnName = "RaceSize";
    @Ignore
    public static final String raceSpeedColumnName = "RaceSpeed";
    @Ignore
    public static final String raceFeatsColumnName = "RaceFeats";
    @Ignore
    public static final String raceSkillsColumnName = "RaceSkillModifiers";
    @Ignore
    public static final String raceLanguagesColumnName = "RaceLanguages";
    @Ignore
    public static final String favouriteClassColumnName = "FavouriteClass";

    @Getter
    @Setter
    @ColumnInfo(name = raceDescriptionColumnName)
    private String raceDescription;

    @Getter
    @Setter
    @ColumnInfo(name = raceAttributeModifiersColumnName)
    private String raceAttributeModifiers;

    @Getter
    @Setter
    @ColumnInfo(name = raceSizeColumnName)
    private String raceSize;

    @Getter
    @Setter
    @ColumnInfo(name = raceSpeedColumnName)
    private String raceSpeed;

    @Getter
    @Setter
    @ColumnInfo(name = raceFeatsColumnName)
    private String raceFeats;

    @Getter
    @Setter
    @ColumnInfo(name = raceSkillsColumnName)
    private String raceSkills;

    @Getter
    @Setter
    @ColumnInfo(name = raceLanguagesColumnName)
    private String raceLanguages;

    @Getter
    @Setter
    @ColumnInfo(name = favouriteClassColumnName)
    private String favouriteClass;

    public Races clone() {
        return new Races(getName(), getSource(),this.raceDescription, this.raceAttributeModifiers, this.raceSize, this.raceSpeed, this.raceFeats, this.raceSkills, this.raceLanguages, this.favouriteClass);
    }

}
