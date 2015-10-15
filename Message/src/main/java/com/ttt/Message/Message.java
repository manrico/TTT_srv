package com.ttt.Message;

import java.io.Serializable;

/**
 * Created by urmet on 9.10.15.
 */
public class Message implements Serializable {
    public Command cmd;
    public String payload;
    public Message(Command c, String pl) {
        cmd = c;
        payload = pl;
    }

    public String toString() {
        return "Command : " + cmd.toString() + " payload : " + payload;
    }
}
