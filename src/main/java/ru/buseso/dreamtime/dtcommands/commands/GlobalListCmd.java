package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GlobalListCmd extends Command {

    public GlobalListCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("dreamtime.cmd.globallist")) {

            Collection<ProxiedPlayer> pl = ProxyServer.getInstance().getPlayers();

            List<String> slist = new ArrayList<>();
            for (ProxiedPlayer pp : pl) {
                slist.add(pp.getName());
            }

            BaseComponent[] text = TextComponent.fromLegacyText("§eИгроки на сервере §8(%online%)§e: §7%players%"
                    .replaceAll("%online%", "" + pl.size())
                    .replaceAll("%players%", String.join(", ", slist)));

            sender.sendMessage(text);
        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§cУ Вас недостаточно прав для данной команды!"));
        }
    }
}
