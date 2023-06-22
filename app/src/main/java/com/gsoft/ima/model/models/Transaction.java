package com.gsoft.ima.model.models;

public class Transaction {
    public String numSender;
    public String nameSender;
    public int idSender;
    public String tokenSender;
    public String numReceiver;
    public String nameReceiver;
    public int idReceiver;
    public String tokenReceiver;
    public int amount;
    public String sentBy;
    public int idTransaction;
    public String date;

    public Transaction(int idSender, String tokenSender, int idReceiver, String tokenReceiver) {
        this.idSender = idSender;
        this.tokenSender = tokenSender;
        this.tokenReceiver = tokenReceiver;
        this.idReceiver = idReceiver;
    }
}
