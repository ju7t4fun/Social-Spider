package testchat;

import com.epam.lab.spider.controller.websocket.Receiver;
import com.epam.lab.spider.controller.websocket.Sender;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Boyarsky Vitaliy on 19.06.2015.
 */
public class TestSender implements Sender {

    private Receiver receiver;

    public TestSender() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (receiver != null) {
                    receiver.send(111111, new Date().toString());
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void accept(Receiver receiver) {
        this.receiver = receiver;
    }

}
