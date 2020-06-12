package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MsgCmd extends Command implements TabExecutor {

    public MsgCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;

            if(DTCommands.msgDisabled.contains(p.getName())) {
                p.sendMessage(new TextComponent("§cУ Вас выключены сообщения! Включить их можно командой /msgtoggle"));
            } else {
                if(args.length <= 1) {
                    p.sendMessage(new TextComponent("§cПожалуйста, укажите ник и сообщение!"));
                } else {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if(target == null) {
                        p.sendMessage(new TextComponent("§cИгрок не найден!"));
                    } else {
                        if(DTCommands.msgDisabled.contains(target.getName())) {
                            p.sendMessage(new TextComponent("§cУ игрока выключены сообщения!"));
                        } else {
                            String msg = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                            TextComponent text = new TextComponent("§b"+p.getName()+" §e-> §b"+target.getName()+"§e: §7"+msg);

                            p.sendMessage(text);
                            target.sendMessage(text);

                            DTCommands.getReply().put(p.getName(), target.getName());
                            DTCommands.getReply().put(target.getName(), p.getName());

                            System.out.println("§7Player "+p.getName()+" sent to player "+target.getName()+" message §f"+msg);
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(new TextComponent("§cКоманда доступна только игрокам!"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> match = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(search)) {
                    match.add(player.getName());
                }
            }
        }
        return match;
    }
}
