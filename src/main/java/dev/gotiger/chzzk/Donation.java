package dev.gotiger.chzzk;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.ChzzkBuilder;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;
import xyz.r2turntrue.chzzk4j.exception.ChannelNotExistsException;

public final class Donation extends JavaPlugin {

    private Chzzk chzzk;
    private ChzzkChat chat;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        chzzk = new ChzzkBuilder()/*.withDebugMode()*/.build();
        String channelId = getConfig().getString("channelId");

        try {
            var channel = chzzk.getChannel(channelId);
            System.out.println("connect: " + channel.getChannelName());

            chat = chzzk.chat(channelId)
                    .withChatListener(new PluginChatListener(this))
                    .build();

            chat.connectBlocking();
        } catch (ChannelNotExistsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            if (chat != null) {
                chat.closeBlocking();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
