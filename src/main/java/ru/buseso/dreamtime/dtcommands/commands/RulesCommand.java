package ru.buseso.dreamtime.dtcommands.commands;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import ru.buseso.dreamtime.dtcommands.DTCommands;

import java.util.ArrayList;
import java.util.List;

public class RulesCommand extends Command {
    public RulesCommand(String name)
    {
        super(name, null, "rule", "rul");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {

        Configuration config = DTCommands.getConfig();
        Configuration rulesConfSect = config.getSection("rules");
        List<String> sects = new ArrayList<>(rulesConfSect.getKeys());
        String before = null;
        String current = null;
        String next = null;

        if (args.length == 0) {
            if (sects.size() > 0) {
                current = sects.get(0);
                if (sects.size() > 1) {
                    next = sects.get(1);
                }
                printRules(sender, rulesConfSect, before, current, next, 1);
            }
        } else if (!sects.contains(args[0].toLowerCase())) {
            if (sects.size() > 0) {
                current = sects.get(0);
                if (sects.size() > 1) {
                    next = sects.get(1);
                }
                printRules(sender, rulesConfSect, before, current, next, 1);
            }
        } else if (sects.size() != 0) {
            int cntr = 0;
            for (String sect : sects) {
                if (current == null) {
                    cntr++;
                    if (args[0].equalsIgnoreCase(sect)) {
                        current = args[0];
                    } else {
                        before = sect;
                    }
                } else {
                    next = sect;
                    break;
                }
            }
            printRules(sender, rulesConfSect, before, current, next, cntr);
        }
    }

    private void printRules(CommandSender sender, Configuration rulesConfSect, String before, String current, String next, int pageNum)
    {
        if (current == null) {
            return;
        }
        int pagesCount = rulesConfSect.getKeys().size();
        if (pageNum <= 0) {
            pageNum = 1;
        }

        TextComponent nl = new TextComponent(compileComponent("\n&r"));

        TextComponent header = new TextComponent(compileComponent("\n&fПравила удобнее читать на сайте "));
        TextComponent link = new TextComponent(compileComponent("&adreamtime.su"));
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dreamtime.su"));
        link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.compileComponent("&eКликай!")));
        TextComponent page = new TextComponent(compileComponent("&7Страница &a" + pageNum + "&7/&a" + pagesCount));

        TextComponent title = new TextComponent(compileComponent(rulesConfSect.getString(current+".title")));

        TextComponent beforeLink;
        TextComponent sep = new TextComponent(compileComponent(" &8| "));
        TextComponent nextLink;
        if (before != null) {
            beforeLink = new TextComponent(compileComponent("&c&l[Назад]"));
            beforeLink.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rules " + before));
            beforeLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, compileComponent("&a&lНажми для просмотра предыдущей страницы.")));
        } else {
            beforeLink = new TextComponent(compileComponent("&8[Назад]"));
        }
        if (next != null) {
            nextLink = new TextComponent(compileComponent("&c&l[Далее]"));
            nextLink.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rules " + next));
            nextLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, compileComponent("&a&lНажми для просмотра следующей страницы.")));
        } else {
            nextLink = new TextComponent(compileComponent("&8[Далее]"));
        }

        List<String> lines = new ArrayList<>(rulesConfSect.getStringList(current + ".strings"));
        TextComponent compiledLines = new TextComponent(compileComponent(String.join("\n", lines)));

        sender.sendMessage(header, link, nl,  title, nl, compiledLines, nl, beforeLink, sep, page, sep, nextLink);
    }

    private BaseComponent[] compileComponent(String text) {
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text));
    }
}
