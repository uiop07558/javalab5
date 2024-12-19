package com.uiop07558.javalab5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ElevatorManager {
  private final List<Elevator> elevators = new ArrayList<>();
  private final BlockingQueue<ElevatorRequest> requests = new ArrayBlockingQueue<>(10);

  public ElevatorManager(int numElevators, int numFloors) {
    for (int i = 0; i < numElevators; i++) {
      elevators.add(new Elevator(i, numFloors, requests));
    }
  }

  public void start() {
    for (Elevator elevator : elevators) {
      elevator.start();
    }
  }

  public void addRequest(ElevatorRequest request) {
    requests.offer(request);
  }
}
