package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgtoggleCmd extends Command {
    public MsgtoggleCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;

            if(DTCommands.msgDisabled.contains(p)) {
                p.sendMessage(new TextComponent("§aВы включили личные сообщения!"));
                DTCommands.msgDisabled.remove(p);
            } else {
                p.sendMessage(new TextComponent("§cВы выключили личные сообщения!"));
                DTCommands.msgDisabled.add(p);
            }
        }
    }
}
