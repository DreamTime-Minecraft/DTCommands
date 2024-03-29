package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.buseso.dreamtime.dtcommands.msg.MsgSystem;

public class ReplyCmd extends Command {

    public ReplyCmd() {
        super("reply", null, "r");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(args.length == 0) {
                sender.sendMessage(TextComponent.fromLegacyText("§cВведите сообщение!"));
                return;
            }

            // Если сообщения отключены
            if (MsgSystem.msgDisabled.contains(sender.getName())) {
                sender.sendMessage(TextComponent.fromLegacyText("§cУ Вас выключены сообщения! Включить их можно командой /msgtoggle"));
            }
            // Если ни с кем не переписывался
            String target = MsgSystem.getReply().get(sender.getName());
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
            String msg = String.join(" ", args);
            MsgSystem.sendMessage((ProxiedPlayer)sender, playerTarget, msg);

        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§cКоманда доступна только игрокам!"));
        }
    }
}
