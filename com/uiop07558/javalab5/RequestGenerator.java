package com.uiop07558.javalab5;

import java.util.Random;

class RequestGenerator {
  private final ElevatorManager manager;
  private final Random random;
  private final int numFloors;

  public RequestGenerator(ElevatorManager manager, int numFloors) {
    this.manager = manager;
    this.random = new Random();
    this.numFloors = numFloors;
  }

  public void start() {
    new Thread(() -> {
      while (true) {
        try {
          int from = random.nextInt(numFloors);
          int to = random.nextInt(numFloors);
          if (to == from) {
            continue;
          }
          manager.addRequest(new ElevatorRequest(from, to));
          System.out.println("New request for travel from " + from + " to " + to);
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }).start();
  }
}
