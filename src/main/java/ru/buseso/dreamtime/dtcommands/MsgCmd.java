package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgCmd extends Command {
    public MsgCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;

            if(DTCommands.msgDisabled.contains(p) && !p.hasPermission("dreamtime.cmd.msg")) {
                p.sendMessage(new TextComponent("§cУ Вас выключены сообщения!"));
            } else {
                if(args.length <= 1) {
                    p.sendMessage(new TextComponent("§cПожалуйста, укажите ник и сообщение!"));
                } else {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);

                    if(target == null) {
                        p.sendMessage(new TextComponent("§cИгрок не найден!"));
                    } else {
                        if(DTCommands.msgDisabled.contains(target)) {
                            p.sendMessage(new TextComponent("§cУ игрока выключены сообщения!"));
                        } else {
                            StringBuilder m = new StringBuilder();
                            for(int i = 1; i < args.length; i++) {
                                m.append(args[i]).append(" ");
                            }

                            String msg = m.toString();

                            TextComponent text = new TextComponent("§b"+p.getName()+" §e-> §b"+target.getName()+"§e: §7"+msg);

                            p.sendMessage(text);
                            target.sendMessage(text);

                            Reply reply = new Reply();
                            reply.setOne(p);
                            reply.setTwo(target);

                            DTCommands.reply.add(reply);

                            System.out.println("§7Player "+p.getName()+" sent to player "+target.getName()+" message §f"+msg);
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(new TextComponent("§cКоманда доступна только игрокам!"));
        }
    }
}
