package controllers;

import services.factory.impl.ServiceFactoryImpl;


public class IngredientController implements Runnable {
    private final static long MILLIS_IN_DAY = 1000L * 60 * 60 * 24;

    @Override
    public void run() {
        try {
            while (true) {
                ServiceFactoryImpl.getServiceFactoryInstance().getIngredientService().checkExpirationDate();
                Thread.currentThread().sleep(MILLIS_IN_DAY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
