package com.chukwu.MyStreamSite.model;

public class Channel {
  private String name;
  private int id;


  public Channel(String name) {
    this.name = name;
  }

  public Channel(int id) {
    this.id = id;
  }

  public Channel(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
