package com.company;

public class action {

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private int x;
    private int y;
    private int direction;

    public action(int x, int y, int direction)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String toString()
    {
        String str ="[ " + x + " : " + y + " | ";

        if(direction == 'U')
        {
            str += "UP";
        }
        else if(direction == 'R')
        {
            str += "RIGHT";
        }
        else if(direction == 'D')
        {
            str += "DOWN";
        }
        else if(direction == 'L')
        {
            str += "LEFT";
        }
        str += " ]";
        return str;
    }


}
