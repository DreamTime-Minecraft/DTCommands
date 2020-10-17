package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import ru.buseso.dreamtime.dtcommands.DTCommands;

public class DTCommandsCmd extends Command {

    public DTCommandsCmd() {
        super("dtcommands", "dreamtime.cmd.admin", "dtc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("dreamtime.cmd.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {

                    DTCommands.getInstance().loadConfig();

                    sender.sendMessage("§aКонфиг перезагружен!");
                }
            }
        }
    }
}
