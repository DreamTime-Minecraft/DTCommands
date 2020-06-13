package ru.buseso.dreamtime.dtcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ContactCmd extends Command {
    public ContactCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BaseComponent[] text = TextComponent.fromLegacyText("§0\n§aСвязь с нами:" +
                "\n§7Сайт §5https://dreamtime.su" +
                "\n§7Группа ВК §9https://vk.com/mc_dreamtime" +
                "\n§7Сервер DS §bhttps://discord.gg/jWQZ2Wy" +
                "\n");
        sender.sendMessage(text);
    }
}
