package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public final class DTCommands extends Plugin {

    public static List<ProxiedPlayer> msgDisabled = new ArrayList<>();
    public static List<Reply> reply = new ArrayList<>();

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerCommand(this, new ListCmd("list"));
        pm.registerCommand(this, new MsgCmd("msg"));
        pm.registerCommand(this, new MsgCmd("m"));
        pm.registerCommand(this, new ReplyCmd("reply"));
        pm.registerCommand(this, new ReplyCmd("r"));
        pm.registerCommand(this, new MsgtoggleCmd("msgtoggle"));
        pm.registerCommand(this, new AlertCmd("alert"));
        pm.registerCommand(this, new ContactCmd("contact"));
    }
}
