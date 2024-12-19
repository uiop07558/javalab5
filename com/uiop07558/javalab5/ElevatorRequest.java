package com.uiop07558.javalab5;

class ElevatorRequest implements Comparable<ElevatorRequest> {
  private final Integer from;
  private final Integer to;

  public ElevatorRequest(int from, int to) {
    this.from = from;
    this.to = to;
  }

  public int getFrom() {
    return from;
  }
  
  public int getTo() {
    return to;
  }
  @Override
  public int compareTo(ElevatorRequest arg) {
    return this.from.compareTo(arg.from);
  }

  public boolean isUp() {
    return to > from;
  }

  static ElevatorRequest getMin() {
    return new ElevatorRequest(0, 0);
  }

  static ElevatorRequest getMax() {
    return new ElevatorRequest(ElevatorSystem.NUM_FLOORS - 1, 0);
  }
}
