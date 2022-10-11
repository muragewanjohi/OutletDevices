package com.outlet.device.models;

import java.io.Serializable;
import java.lang.String;

public class AssetStates implements Serializable {
  private String stateName;

  private String stateId;

  private String typeName;

  private String typeId;

  private String typeDescription;

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
