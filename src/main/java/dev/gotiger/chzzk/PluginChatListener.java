package dev.gotiger.chzzk;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.r2turntrue.chzzk4j.chat.*;

public class PluginChatListener implements ChatEventListener {

    private final Donation plugin;

    public PluginChatListener(Donation plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onConnect(ChzzkChat chat, boolean isReconnecting) {
        if (!isReconnecting) {
            chat.requestRecentChat(50);
        }
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onChat(ChatMessage msg) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            String nickname = msg.getProfile() != null ? msg.getProfile().getNickname() : "익명";
            String message = "<[치지직] " + nickname + "> " + msg.getContent();
            String formattedMessage = ChatColor.GREEN + message;

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(formattedMessage);
            }
        });
    }

    @Override
    public void onDonationChat(DonationMessage msg) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            String nickname = msg.getProfile() != null ? msg.getProfile().getNickname() : "익명";
            String titleMessage = ChatColor.YELLOW + nickname + "님";
            String subtitleMessage = ChatColor.WHITE + String.valueOf(msg.getPayAmount()) + "원 후원!";

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
            }
        });
    }
}