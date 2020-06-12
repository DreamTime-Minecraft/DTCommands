package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class DTCommands extends Plugin implements Listener {

    public static Set<String> msgDisabled = ConcurrentHashMap.newKeySet();
    //public static List<Reply> reply = new ArrayList<>();
    private static Map<String, String> reply = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();

        pm.registerCommand(this, new ListCmd("list"));
        pm.registerCommand(this, new MsgCmd("msg", null, "m"));
        pm.registerCommand(this, new ReplyCmd("reply", null, "r"));
        pm.registerCommand(this, new MsgtoggleCmd("msgtoggle"));
        pm.registerCommand(this, new AlertCmd("alert"));
        pm.registerCommand(this, new ContactCmd("contact"));
        pm.registerCommand(this, new GlobalListCmd("globallist", null, "glist"));
        pm.registerListener(this, this);
    }

    public static Map<String, String> getReply() {
        return reply;
    }

    @EventHandler
    public void Disconnect(PlayerDisconnectEvent event) {
        reply.remove(event.getPlayer().getName());
        msgDisabled.remove(event.getPlayer().getName());
    }
}
