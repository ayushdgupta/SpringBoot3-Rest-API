package com.guptaji.springbootbasicrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConfigJsonData {

  @JsonProperty(value = "ConfigData")
  private List<ConfigModel> configData;

  public List<ConfigModel> getConfigData() {
    return configData;
  }

  public void setConfigData(List<ConfigModel> configData) {
    this.configData = configData;
  }
}
