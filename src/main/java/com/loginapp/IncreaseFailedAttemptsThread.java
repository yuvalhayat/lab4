package main.java.com.loginapp;

public class IncreaseFailedAttemptsThread extends Thread{

    private String username;
    private int failedAttempts;
    private int n;
    private int t;

    public IncreaseFailedAttemptsThread( String username, int failedAttempts, int n, int t) {
        this.failedAttempts = failedAttempts;
        this.username = username;
        this.n = n;
        this.t = t;
    }

    @Override
    public void run() {
        UserDataBase db = UserDataBase.getInstance();
        db.incrementUserAttempts(this.username);
        if (failedAttempts+1 == n) {
            db.blockUser(this.username);
            UnblockUserThread unblockAfterTimeObj = new UnblockUserThread(username,t);
            unblockAfterTimeObj.start();

        }
    }

}
