package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.buseso.dreamtime.dtcommands.DTCommands;

public class ReplyCmd extends Command {

    public ReplyCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(args.length == 0) {
                sender.sendMessage(TextComponent.fromLegacyText("§cВведите сообщение!"));
                return;
            }

            // Если сообщения отключены
            if (DTCommands.msgDisabled.contains(sender.getName())) {
                sender.sendMessage(TextComponent.fromLegacyText("§cУ Вас выключены сообщения! Включить их можно командой /msgtoggle"));
            }
            // Если ни с кем не переписывался
            String target = DTCommands.getReply().get(sender.getName());
            if (target == null){
                sender.sendMessage(TextComponent.fromLegacyText("§cУ вас нет действующих диалогов!"));
                return;
            }
            // Если чел вышел
            ProxiedPlayer playerTarget = ProxyServer.getInstance().getPlayer(target);
            if (playerTarget == null) {
                sender.sendMessage(TextComponent.fromLegacyText("§cВаш собеседник вышел с сервера!"));
                return;
            }
            if (DTCommands.msgDisabled.contains(playerTarget.getName())) {
                sender.sendMessage(TextComponent.fromLegacyText("§cУ Вашего собеседника отключены личные сообщения!"));
                return;
            }
            String msg = String.join(" ", args);

            DTCommands.getReply().put(playerTarget.getName(), sender.getName());
            sender.sendMessage(TextComponent.fromLegacyText("§b"+sender.getName()+" §e-> §b"+playerTarget.getName()+"§e: §7"+msg));
            playerTarget.sendMessage(TextComponent.fromLegacyText("§b"+sender.getName()+" §e-> §b"+playerTarget.getName()+"§e: §7"+msg));
            System.out.println("§7Player "+sender.getName()+" sent to player "+playerTarget.getName()+" message §f"+msg);

        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§cКоманда доступна только игрокам!"));
        }
    }
}
