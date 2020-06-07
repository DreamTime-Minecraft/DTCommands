package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;

public class ListCmd extends Command {
    public ListCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(sender.hasPermission("dreamtime.cmd.list")) {
                ProxiedPlayer p = (ProxiedPlayer) sender;

                Collection<ProxiedPlayer> pl = p.getServer().getInfo().getPlayers();

                StringBuilder sb = new StringBuilder();
                for (ProxiedPlayer pp : pl) {
                    sb.append(pp.getName()).append(", ");
                }

                TextComponent text = new TextComponent("§eИгроки на реалме §8(%online%)§e: §7%players%"
                        .replaceAll("%online%", "" + pl.size())
                        .replaceAll("%players%", sb.toString().substring(0, sb.toString().length() - 2)));

                if (p.hasPermission("dreamtime.cmd.list.info")) {
                    TextComponent[] tc = new TextComponent[1];
                    tc[0] = new TextComponent("§7Реалм: §b"+p.getServer().getInfo().getName());
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tc));
                }

                p.sendMessage(text);
            } else {
                sender.sendMessage(new TextComponent("§cУ Вас недостаточно прав для данной команды!"));
            }
        } else {
            if(args.length == 0) {
                sender.sendMessage(new TextComponent("§cВведите название реалма!"));
            } else {
                Collection<ProxiedPlayer> pl = ProxyServer.getInstance().getServerInfo(args[0]).getPlayers();

                StringBuilder sb = new StringBuilder();
                for (ProxiedPlayer pp : pl) {
                    sb.append(pp.getName()).append(", ");
                }

                TextComponent text = new TextComponent("§eИгроки на реалме §8(%online%)§e: §7%players%"
                        .replaceAll("%online%", "" + pl.size())
                        .replaceAll("%players%", sb.toString().substring(0, sb.toString().length() - 2)));
            }
        }
    }
}
