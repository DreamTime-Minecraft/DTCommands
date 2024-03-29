package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import ru.buseso.dreamtime.dtcommands.msg.MsgSystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MsgCmd extends Command implements TabExecutor {

    public MsgCmd() {
        super("msg", null, "m","tell","w","t");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;

            if(MsgSystem.msgDisabled.contains(p.getName())) {
                p.sendMessage(TextComponent.fromLegacyText("§cУ Вас выключены сообщения! Включить их можно командой §e/msgtoggle"));
            } else {
                if(args.length <= 1) {
                    p.sendMessage(TextComponent.fromLegacyText("§cПожалуйста, укажите ник и сообщение!"));
                } else {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if(target == null) {
                        p.sendMessage(TextComponent.fromLegacyText("§cИгрок не найден!"));
                    } else {
                        String msg = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                        MsgSystem.sendMessage(p, target, msg);
                    }
                }
            }
        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§cКоманда доступна только игрокам!"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> match = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (!MsgSystem.msgDisabled.contains(player.getName())) {
                    if (player.getName().toLowerCase().startsWith(search)) {
                        match.add(player.getName());
                    }
                }
            }
        }
        return match;
    }
}
