package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.buseso.dreamtime.dtcommands.msg.MsgSystem;

public class MsgtoggleCmd extends Command {
    public MsgtoggleCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;

            if(MsgSystem.msgDisabled.remove(p.getName())) {
                p.sendMessage(TextComponent.fromLegacyText("§aВы включили личные сообщения!"));
            } else {
                p.sendMessage(TextComponent.fromLegacyText("§cВы выключили личные сообщения!"));
                MsgSystem.msgDisabled.add(p.getName());
            }
        }
    }
}
