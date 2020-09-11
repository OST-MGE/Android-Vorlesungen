package ch.ost.rj.mge.v06.myapplication.observer.domain;

import java.util.Date;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Stock extends Observable {

    private String name;
    private double value;
    private Date date;

    public Stock(String name, double value) {
        this.name = name;
        this.value = value;
        this.date = new Date();

        startFetchingPrices();
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public void updateValue(double delta) {
        this.date = new Date();
        this.value += delta;
        setChanged();
        notifyObservers();
    }

    private void startFetchingPrices() {
        Timer t = new Timer();
        int period = 1000 * (int) (1 + 5 * new Random().nextDouble());
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setNewRandomPrice();
            }
        }, 0, period);
    }

    private synchronized void setNewRandomPrice() {
        final double delta = new Random().nextDouble() - 0.45;
        updateValue(delta);
    }
}