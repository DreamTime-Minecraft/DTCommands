package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class AlertCmd extends Command {
    public AlertCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("dreamtime.cmd.alert")) {
            sender.sendMessage(new TextComponent(TextComponent.fromLegacyText("§cУ Вас недостаточно прав!")));
            return;
        }

        if(args.length == 0) {
            sender.sendMessage(new TextComponent(TextComponent.fromLegacyText("§cВведите сообщение!")));
        } else {
            String msg = String.join(" ", args).replaceAll("&","§");

            ProxyServer.getInstance().broadcast(new TextComponent(TextComponent.fromLegacyText("§8[§cОБЪЯВЛЕНИЕ ОТ §6"+sender.getName()+"§8] §7"+msg)));
        }
    }
}
