package com.ttt.Message;

import java.io.Serializable;

/**
 * Created by urmet on 9.10.15.
 */
public class Message implements Serializable {
    public Command cmd;
    public String payload;
    public int idMark;
    public Message(Command c, String pl, int idMark) {
        this.cmd = c;
        this.payload = pl;
        this.idMark = idMark;
    }

    public String toString() {
        return "Command : " + cmd.toString() + " payload : " + payload + " idMark : "+ idMark;
    }
}
