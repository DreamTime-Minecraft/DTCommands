package ru.buseso.dreamtime.dtcommands.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;

public class Utils {
    public static BaseComponent[] coloredComponents(String msg) {
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static String coloredText(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
