package model;

public class DeliveryInfo {
    private Position startPosition;
    private Position endPosition;
    private String directions;

    public DeliveryInfo(Position startPosition, Position endPosition, String directions) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.directions = directions;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "Delivery: " + startPosition + " → " + endPosition;
    }
}