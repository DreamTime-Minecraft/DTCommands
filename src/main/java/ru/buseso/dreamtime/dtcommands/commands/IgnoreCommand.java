package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import ru.buseso.dreamtime.dtcommands.msg.MsgSystem;
import ru.buseso.dreamtime.dtcommands.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class IgnoreCommand extends Command implements TabExecutor {

    public IgnoreCommand() {
        super("ignore");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cКоманда не может быть выполнена только игроком"));
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(Utils.coloredComponents("&cПожалуйста, укажите ник игрока!"));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;
        ProxiedPlayer other = ProxyServer.getInstance().getPlayer(args[0]);
        if (other == null) {
            p.sendMessage(TextComponent.fromLegacyText("§cИгрок не найден!"));
            return;
        }
        if (MsgSystem.ignore(p, other)) {
            sender.sendMessage(Utils.coloredComponents("&7Теперь вы игнорируете игрока &c" + other.getName()));
            sender.sendMessage(Utils.coloredComponents("&7Чтобы убрать его из игнора, напишите ещё раз &e/ignore " + other.getName()));
        } else {
            sender.sendMessage(Utils.coloredComponents("&7Вы больше не игнорируете игрока &a" + other.getName()));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> match = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (!MsgSystem.msgDisabled.contains(player.getName()) && !player.getName().equalsIgnoreCase(sender.getName())) {

                    if (player.getName().toLowerCase().startsWith(search.toLowerCase())) {
                        match.add(player.getName());
                    }
                }
            }
        }
        return match;
    }
}
