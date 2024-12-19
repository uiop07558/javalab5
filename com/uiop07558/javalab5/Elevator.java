package com.uiop07558.javalab5;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

class Elevator {
  static final int floorTravelTime = 200;

  private final int id; // read only
  private final BlockingQueue<ElevatorRequest> requests; // recieve only
  private int currentFloor = 0; // move only
  private boolean movingUp = true; // move only
  private final TreeSet<ElevatorRequest> requestsUp = new TreeSet<>(); // race
  private final TreeSet<ElevatorRequest> requestsDown = new TreeSet<>(); // race

  public Elevator(int id, int numFloors, BlockingQueue<ElevatorRequest> requests) {
    this.id = id;
    this.requests = requests;
  }

  public void start() {
    new Thread(new RequestReciever()).start();
    new Thread(new Mover()).start();
  }

  class RequestReciever implements Runnable {
    @Override
    public void run() {
      while (true) {
        try {
          ElevatorRequest request = requests.take();

          if (request.isUp()) synchronized (requestsUp) {
            requestsUp.add(request);
          }
          else synchronized (requestsDown) {
            requestsDown.add(request);
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }
  }

  class Mover implements Runnable {
    @Override
    public void run() {
      while (true) {
        try {
          ElevatorRequest start;
          if (movingUp) {
            NavigableSet<Integer> destinations = new TreeSet<>();
            synchronized(requestsUp) {
              start = requestsUp.ceiling(ElevatorRequest.getMin());
              if (start == null) {
                movingUp = !movingUp;
                continue;
              }

              destinations.add(start.getTo());
              for (ElevatorRequest req: requestsUp) {
                destinations.add(req.getFrom());
                destinations.add(req.getTo());
              }
              requestsUp.clear();
            }

            int distance = Math.abs(currentFloor - start.getFrom());
            if (distance > 0) {
              Thread.sleep(floorTravelTime * distance);
              currentFloor = start.getFrom();
              System.out.println("Elevator " + id + " moved down to floor " + currentFloor);
            }

            for (int destination: destinations) {
              distance = Math.abs(currentFloor - destination);
              if (distance > 0) {
                Thread.sleep(floorTravelTime * distance);
                currentFloor = destination;
                System.out.println("Elevator " + id + " moved up to floor " + currentFloor);
              }
            }
          }
          else {
            NavigableSet<Integer> destinations = new TreeSet<>((a1, a2) -> a2.compareTo(a1));
            synchronized(requestsDown) {
              start = requestsDown.floor(ElevatorRequest.getMax());
              if (start == null) {
                movingUp = !movingUp;
                continue;
              }

              destinations.add(start.getTo());
              for (ElevatorRequest req: requestsDown) {
                destinations.add(req.getFrom());
                destinations.add(req.getTo());
              }
              requestsDown.clear();
            }

            int distance = Math.abs(currentFloor - start.getFrom());
            if (distance > 0) {
              Thread.sleep(floorTravelTime * distance);
              currentFloor = start.getFrom();
              System.out.println("Elevator " + id + " moved up to floor " + currentFloor);
            }

            for (int destination: destinations) {
              distance = Math.abs(currentFloor - destination);
              if (distance > 0) {
                Thread.sleep(floorTravelTime * distance);
                currentFloor = destination;
                System.out.println("Elevator " + id + " moved down to floor " + currentFloor);
              }
            }
          }

          movingUp = !movingUp;
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }
  }
}
