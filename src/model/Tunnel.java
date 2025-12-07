package model;

import java.awt.Color;

public class Tunnel {
    private Position entrance;
    private Position exit;
    private String id;
    private Color color;

    public Tunnel(Position entrance, Position exit, String id, Color color) {
        this.entrance = entrance;
        this.exit = exit;
        this.id = id;
        this.color = color;
    }

    public Position getEntrance() {
        return entrance;
    }

    public void setEntrance(Position entrance) {
        this.entrance = entrance;
    }

    public Position getExit() {
        return exit;
    }

    public void setExit(Position exit) {
        this.exit = exit;
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

    public Position getOtherEnd(Position current) {
        if (current.equals(entrance)) {
            return exit;
        } else if (current.equals(exit)) {
            return entrance;
        }
        return current;
    }

    @Override
    public String toString() {
        return "Tunnel{" + "id='" + id + "', " + entrance + " → " + exit + '}';
    }
}