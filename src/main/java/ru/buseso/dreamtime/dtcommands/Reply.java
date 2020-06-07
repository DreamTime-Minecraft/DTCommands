package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Reply {
    ProxiedPlayer one;
    ProxiedPlayer two;

    public ProxiedPlayer getOne() {
        return one;
    }

    public ProxiedPlayer getTwo() {
        return two;
    }

    public void setOne(ProxiedPlayer one) {
        this.one = one;
    }

    public void setTwo(ProxiedPlayer two) {
        this.two = two;
    }
}
