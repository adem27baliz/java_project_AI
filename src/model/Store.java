package model;

import java.awt.Color;

public class Store {
    private Position position;
    private String id;
    private Color color;

    public Store(Position position, String id, Color color) {
        this.position = position;
        this.id = id;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Store{" + "id='" + id + "', position=" + position + '}';
    }
}