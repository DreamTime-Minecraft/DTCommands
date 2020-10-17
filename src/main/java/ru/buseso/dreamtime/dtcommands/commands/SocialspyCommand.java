package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.buseso.dreamtime.dtcommands.msg.MsgSystem;

public class SocialspyCommand extends Command {

    public SocialspyCommand() {
        super("socialspy", "dreamtime.cmd.socialspy");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cКоманда не может быть выполнена только игроком"));
            return;
        }
        if (MsgSystem.socialspy.remove(sender.getName())) {
            sender.sendMessage(TextComponent.fromLegacyText("§cТеперь вы не видите личные сообщения игроков!"));
        }
        else if (sender.hasPermission(this.getPermission())) {
            MsgSystem.socialspy.add(sender.getName());
            sender.sendMessage(TextComponent.fromLegacyText("§aТеперь вы видите личные сообщения игроков!"));
        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§cНедостаточно прав."));
        }
    }
}
