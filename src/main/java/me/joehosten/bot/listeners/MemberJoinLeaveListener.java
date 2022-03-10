package me.joehosten.bot.listeners;

import me.joehosten.bot.core.util.DiscordLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;

public class MemberJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member member = event.getMember();

        EmbedBuilder builder = new EmbedBuilder();

        OffsetDateTime timeCreated = member.getTimeCreated();

        String dateCreated = TimeUtil.getDateTimeString(timeCreated);
        builder.setTitle(member.getUser().getName());
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        builder.setDescription(member.getAsMention() + " has joined the server! \n" +
                "Account created **" + dateCreated + "**!");
        builder.setColor(Color.GREEN);
        builder.setFooter("• User ID: " + member.getId() + " •");

        new DiscordLog(builder);
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        User user = event.getUser();
        String effectiveAvatarUrl = user.getEffectiveAvatarUrl();

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(user.getName());
        builder.setThumbnail(effectiveAvatarUrl);
        builder.setDescription(user.getAsMention() + " has left the server.");
        builder.setColor(Color.RED);
        builder.setFooter("• User ID: " + user.getId() + " •");

        new DiscordLog(builder);
    }

}
