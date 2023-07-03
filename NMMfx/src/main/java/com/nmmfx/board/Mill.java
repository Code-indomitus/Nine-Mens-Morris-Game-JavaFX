package com.nmmfx.board;

import java.util.ArrayList;

public class Mill {

  private ArrayList<Intersection> intersections = new ArrayList<>();

  /**
   *populates the intersection array with intersections. 
   */
  public Mill(ArrayList<Intersection> intersections) {
    for (Intersection inter: intersections) {
      addIntersection(inter);
    }
  }
  /**
   * adds the interseciton for Mill
   * @param inter Intersection
   */ 

  public void addIntersection(Intersection inter) {
    this.intersections.add(inter);
    inter.setMill(this);
    inter.setIsPartMill(true);
  }

  /** Remove intersections from the mill, and set the mill of each intersection to null
   * 
   */ 
  public void deleteMill() {
    for (Intersection inter: intersections) {
      inter.setIsPartMill(false);
      inter.setMill(null);
    }
    this.intersections.clear();
  }
  /**
   * gets the intersection of Mill
   * @return intersections of Mill
   */

  public ArrayList<Intersection> getIntersections() {
    return this.intersections;
  }
}
