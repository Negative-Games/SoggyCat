package me.joehosten.bot.listeners;

import net.dv8tion.jda.api.JDA;

public class BotLaunchListener {

    // Custom "Event" when the bot is launched
    public void onLaunch(JDA jda) {
        System.out.println("Bot on");
    }
}