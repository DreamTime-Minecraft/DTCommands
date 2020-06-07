package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCmd extends Command {
    public ReplyCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(args.length == 0) {
                sender.sendMessage(new TextComponent("§cВведите сообщение!"));
                return;
            }
            ProxiedPlayer p = (ProxiedPlayer)sender;

            Reply rep = new Reply();
            for(Reply reply : DTCommands.reply) {
                if(reply.getOne().equals(p) || reply.getTwo().equals(p)) {
                    rep = reply;
                }
            }

            StringBuilder b = new StringBuilder();
            for (String arg : args) {
                b.append(arg).append(" ");
            }

            String msg = b.toString();

            //If one
            if(rep.getOne().equals(p)) {
                if(DTCommands.msgDisabled.contains(rep.getTwo())) {
                    p.sendMessage(new TextComponent("§cУ Вашего собеседника отключены личные сообщения!"));
                } else {
                    p.sendMessage(new TextComponent("§b"+p.getName()+" §e-> §b"+rep.getTwo().getName()+"§e: §7"+msg));
                }
            } /* If two */ else if(rep.getTwo().equals(p)) {
                if(DTCommands.msgDisabled.contains(rep.getOne())) {
                    p.sendMessage(new TextComponent("§cУ Вашего собеседника отключены личные сообщения!"));
                } else {
                    p.sendMessage(new TextComponent("§b"+p.getName()+" §e-> §b"+rep.getTwo().getName()+"§e: §7"+msg));
                }
            }

        } else {
            sender.sendMessage(new TextComponent("§cКоманда доступна только игрокам!"));
        }
    }
}
