package net.ion.entity;

public class BotMessage {
    private String req;
    private String res;

    @SuppressWarnings("unused")
    private BotMessage() { }

    public BotMessage(String req, String res) {
        this.req = req;
        this.res = res;
    }

    public String getRes() {
        return res;
    }
    public String getReq() {
        return req;
    }
	
}