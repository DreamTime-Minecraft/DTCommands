package ru.buseso.dreamtime.dtcommands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ContactCmd extends Command {

    public ContactCmd() {
        super("contacts", null, "contact");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        TextComponent component = new TextComponent(
                new TextComponent("§0\n§aСвязь с нами:"),
                new TextComponent("\n§7Сайт: "),
                new ComponentBuilder("§edreamtime.su").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dreamtime.su")).getCurrentComponent(),
                new TextComponent("\n§7Группа ВК: "),
                new ComponentBuilder("§evk.com/mc_dreamtime").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://vk.com/mc_dreamtime")).getCurrentComponent(),
                new TextComponent("\n§7Сервер DS: "),
                new ComponentBuilder("§ediscord.gg/xTwTYVX").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/xTwTYVX")).getCurrentComponent()
        );
        sender.sendMessage(component);
    }
}
