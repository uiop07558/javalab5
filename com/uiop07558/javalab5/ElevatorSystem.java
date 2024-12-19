package com.uiop07558.javalab5;

public class ElevatorSystem {
  public static final int NUM_LIFTS = 4;
  public static final int NUM_FLOORS = 20;

  public static void main(String[] args) {
    ElevatorManager manager = new ElevatorManager(NUM_LIFTS, NUM_FLOORS);
    manager.start();

    RequestGenerator generator = new RequestGenerator(manager, NUM_FLOORS);
    generator.start();
  }
}