package com.gsoft.ima.model.models;

import static com.gsoft.ima.constants.main.MainConstants.EMPTY;

public class Transaction {

    public String nameSender = EMPTY;
    public String numSender = EMPTY;
    public int amount = 0;
    public String nameReceiver = EMPTY;
    public String numReceiver = EMPTY;
    public String date = EMPTY;
    public String method = EMPTY;
    public String status = EMPTY;
    public String tokenSender = EMPTY;
    public String tokenReceiver = EMPTY;

    public Transaction(int amount) {
        this.amount = amount;
    }
}
