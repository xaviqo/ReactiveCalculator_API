package tech.xavi.webflux.service;

public class SleepUtil {

    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds* 1000L);
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
