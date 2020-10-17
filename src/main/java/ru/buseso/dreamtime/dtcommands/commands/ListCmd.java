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

public class ListCmd extends Command {
    public ListCmd() {
        super("list", "dreamtime.cmd.list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(sender.hasPermission("dreamtime.cmd.list")) {
                ProxiedPlayer p = (ProxiedPlayer) sender;

                Collection<ProxiedPlayer> pl = p.getServer().getInfo().getPlayers();

                List<String> slist = new ArrayList<>();
                for (ProxiedPlayer pp : pl) {
                    if(!pp.hasPermission("dreamtime.cmd.list.bypass")) slist.add(pp.getName());
                }

                TextComponent text = new TextComponent(TextComponent.fromLegacyText("§eИгроки на реалме §8(%online%)§e: §7%players%"
                        .replaceAll("%online%",""+pl.size())
                        .replaceAll("%players%", String.join(", ", slist))));

                if (p.hasPermission("dreamtime.cmd.list.info")) {
                    BaseComponent[] tc = TextComponent.fromLegacyText("§7Реалм: §b"+p.getServer().getInfo().getName());
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tc));
                }

                p.sendMessage(text);
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("§cУ Вас недостаточно прав для данной команды!"));
            }
        } else {
            if(args.length == 0) {
                sender.sendMessage(TextComponent.fromLegacyText("§cВведите название реалма!"));
            } else {
                Collection<ProxiedPlayer> pl = ProxyServer.getInstance().getServerInfo(args[0]).getPlayers();

                StringBuilder sb = new StringBuilder();
                for (ProxiedPlayer pp : pl) {
                    sb.append(pp.getName()).append(", ");
                }

                BaseComponent[] text = TextComponent.fromLegacyText("§eИгроки на реалме §8(%online%)§e: §7%players%"
                        .replaceAll("%online%", "" + pl.size())
                        .replaceAll("%players%", sb.toString().substring(0, sb.toString().length() - 2)));
                sender.sendMessage(text);
            }
        }
    }
}
