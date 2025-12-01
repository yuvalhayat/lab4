package main.java.com.loginapp;

import java.util.Timer;
import java.util.TimerTask;

public class UnblockUserThread extends Thread{
    private String username;
    private int t;

    public UnblockUserThread(String username, int t) {
        this.username = username;
        this.t = t;
    }

    @Override
    public void run() {

        try{
            this.sleep(t*1000);
            UserDataBase db = UserDataBase.getInstance();
            db.unblockUser(username);
        }
        catch(InterruptedException e){
            System.out.println("Couldn't sleep");
        }
        /*
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                db.unblockUser(username);
                timer.cancel();
            }
        }, t*1000);
        */
    }

}
