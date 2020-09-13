package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;
import ru.buseso.dreamtime.dtcommands.commands.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class DTCommands extends Plugin implements Listener {

    public static Set<String> msgDisabled = ConcurrentHashMap.newKeySet();
    //public static List<Reply> reply = new ArrayList<>();
    private static Map<String, String> reply = new ConcurrentHashMap<>();
    private static Configuration config;
    private static DTCommands instance;
    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getProxy().getPluginManager();

        loadConfig();

        pm.registerCommand(this, new ListCmd("list"));
        pm.registerCommand(this, new MsgCmd("msg", null, "m","tell","w","t"));
        pm.registerCommand(this, new ReplyCmd("reply", null, "r"));
        pm.registerCommand(this, new MsgtoggleCmd("msgtoggle"));
        pm.registerCommand(this, new AlertCmd("alert"));
        pm.registerCommand(this, new ContactCmd("contacts", null, "contact"));
        pm.registerCommand(this, new GlobalListCmd("globallist", "dreamtime.cmd.globallist", "glist"));
        pm.registerCommand(this, new DTCommandsCmd("dtcommands", "dreamtime.cmd.admin", "dtc"));
        pm.registerCommand(this, new RulesCommand("rules"));
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

    public static Configuration getConfig() {
        return config;
    }

    public void loadConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DTCommands getInstance() {
        return instance;
    }
}
