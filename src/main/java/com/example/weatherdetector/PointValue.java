package com.example.weatherdetector;

public class PointValue {
    int xValue, yValue;

    public PointValue(){
    }

    public PointValue(int xVal, int yVal) {
        this.xValue = xValue;
        this.yValue = yValue;
    }
    public int getXValue(){
        return xValue;
    }
    public int getYValue(){
        return yValue;
    }
}
