package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.buseso.dreamtime.dtcommands.msg.MsgSystem;
import ru.buseso.dreamtime.dtcommands.utils.Utils;

public class IgnoreCommand extends Command {

    public IgnoreCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
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
        if (p.hasPermission(this.getPermission())) {
            if (MsgSystem.ignore(p, other)) {
                sender.sendMessage(Utils.coloredComponents("&cТеперь вы игнорируете игрока &e" + other.getName()));
                sender.sendMessage(Utils.coloredComponents("&cЧтобы убрать его из игнора, напишите ещё раз &e/ignore " + other.getName()));
            } else {
                sender.sendMessage(Utils.coloredComponents("&aВы больше не игнорируете игрока &e" + other.getName()));
            }
        }
    }
}
