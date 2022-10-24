package com.outlet.device.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.String;

@Entity
public class StateId implements Serializable {

  @PrimaryKey(autoGenerate = true)
  public int uid;

  @ColumnInfo(name = "stateName")
  private String stateName;

  @ColumnInfo(name = "stateId")
  private String stateId;

  @ColumnInfo(name = "typeName")
  private String typeName;

  @ColumnInfo(name = "typeId")
  private String typeId;

  @ColumnInfo(name = "typeDescription")
  private String typeDescription;

  @ColumnInfo(name = "stateDescription")
  private String stateDescription;

  public String getStateName() {
    return this.stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public String getStateId() {
    return this.stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeId() {
    return this.typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getTypeDescription() {
    return this.typeDescription;
  }

  public void setTypeDescription(String typeDescription) {
    this.typeDescription = typeDescription;
  }

  public String getStateDescription() {
    return this.stateDescription;
  }

  public void setStateDescription(String stateDescription) {
    this.stateDescription = stateDescription;
  }
}
