package ru.buseso.dreamtime.dtcommands.msg;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.buseso.dreamtime.dtcommands.DTCommands;
import ru.buseso.dreamtime.dtcommands.utils.Utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MsgSystem {
    public static final Set<String> msgDisabled = ConcurrentHashMap.newKeySet();
    public static final Map<String, Set<String>> ignoreList = new ConcurrentHashMap<>();
    //public static List<Reply> reply = new ArrayList<>();
    public static final Map<String, String> reply = new ConcurrentHashMap<>();
    public static final Set<String> socialspy = Collections.synchronizedSet(new HashSet<>());

    public static Map<String, String> getReply() {
        return reply;
    }

    public static void leave(ProxiedPlayer player) {
        reply.remove(player.getName());
        msgDisabled.remove(player.getName());
        socialspy.remove(player.getName());
        ignoreList.remove(player.getName());
    }

    private static final String BYPASS_IGNORE_PERM = "dreamtime.ignore.bypass";
    private static final String BYPASS_SOCIAL_SPY_PERM = "dreamtime.socspy.bypass";
    private static final String BYPASS_BYPASS_SOCIAL_SPY_PERM = "dreamtime.socspy.bypass.bypass";

    public static void sendMessage(ProxiedPlayer sender, ProxiedPlayer target, String msg) {

        if (MsgSystem.msgDisabled.contains(target.getName())) {
            sender.sendMessage(TextComponent.fromLegacyText("§cУ игрока выключены сообщения!"));
        } else {
            if (isIgnored(target, sender)) {
                sender.sendMessage(Utils.coloredComponents("&cВы не можете писать этому игроку!"));
            }
            BaseComponent[] msgToSender = TextComponent.fromLegacyText(Utils.coloredText("&8[&aВы &7-> &a" + target.getName() + "&8]: &f") + msg);
            BaseComponent[] msgToTarget = TextComponent.fromLegacyText(Utils.coloredText("&8[&a" + sender.getName() + " &7-> &aВам&8]: &f") + msg);
            BaseComponent[] msgToSocSpy = TextComponent.fromLegacyText(Utils.coloredText("&7(SocialSpy) &7[&8" + sender.getName() + " &7-> &8" + target.getName() + "&7]: ") + msg);
            String          msgToConsol = Utils.coloredText("&8[&b" + sender.getName() + " &7-> &b" + target.getName() + "&8]: &f") + msg;

            reply.put(sender.getName(), target.getName());
            reply.put(target.getName(), sender.getName());

            sender.sendMessage(msgToSender);
            target.sendMessage(msgToTarget);

            boolean bypassSpy = sender.hasPermission(BYPASS_SOCIAL_SPY_PERM) || target.hasPermission(BYPASS_SOCIAL_SPY_PERM);
            socialspy.forEach(s -> {
                try {
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(s);
                    if (!player.getName().equalsIgnoreCase(sender.getName()) && !player.getName().equalsIgnoreCase(target.getName())) { // Нам не нужно видеть свои же сообщения повторно
                        if (!bypassSpy || player.hasPermission(BYPASS_BYPASS_SOCIAL_SPY_PERM)) { // Обход обхода для самых важных
                            player.sendMessage(msgToSocSpy);
                        }
                    }
                } catch (Exception ignored) { }
            });

            DTCommands.getInstance().getLogger().info(msgToConsol);

        }
    }

    /**
     * true, if player "player" is ignored player "other"<br>
     * true, если игрок "player" игнорирует игрока "other"
     * @param player
     * @param other
     * @return
     */
    public static boolean isIgnored(ProxiedPlayer player, ProxiedPlayer other) {
        if (other.hasPermission(BYPASS_IGNORE_PERM)) {
            return false;
        }
        Set<String> ignored = ignoreList.get(player.getName());
        if (ignored != null) {
            return ignored.contains(other.getName());
        }
        return false;
    }

    /**
     * @return true если игрок player теперь игнорирует игрока other, иначе - false
     */
    public synchronized static boolean ignore(ProxiedPlayer player, ProxiedPlayer other) {
        Set<String> ignored = ignoreList.get(player.getName());
        if (ignored == null) {
            ignored = new HashSet<>();
            ignored.add(other.getName());
            return true;
        } else {
            // Если игрок был в игноре, убираем его оттуда
            if (ignored.remove(other.getName())) {
                return false;
            } else {
                // Иначе - добавляем
                ignored.add(other.getName());
                return true;
            }
        }
    }
}
