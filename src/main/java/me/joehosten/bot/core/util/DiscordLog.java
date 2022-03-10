package me.joehosten.bot.core.util;

import me.joehosten.bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Optional;

public class DiscordLog {

    public DiscordLog(EmbedBuilder builder) {
        String logsChannelID = ProductionDiscord.getLogsChannelID();
        Guild guildById = Bot.getInstance().getJda().getGuildById(ProductionDiscord.getId());
        assert guildById != null;
        TextChannel textChannelById = guildById.getTextChannelById(logsChannelID);
        Optional.ofNullable(textChannelById).ifPresent(textChannel -> textChannel.sendMessageEmbeds(builder.build()).queue());
    }

}
