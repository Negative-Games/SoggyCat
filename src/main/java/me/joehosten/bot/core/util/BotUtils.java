package me.joehosten.bot.core.util;

import lombok.experimental.UtilityClass;
import me.joehosten.bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.text.DecimalFormat;
import java.util.Optional;

@UtilityClass
public class BotUtils {


    private final DecimalFormat df;

    static {
        df = new DecimalFormat("###,###,###,###,###,###,###,###.##");
    }

    /**
     * Takes an Integer and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(int i) {
        return df.format(i);
    }

    /**
     * Takes a Double and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(double i) {
        return df.format(i);
    }

    /**
     * Takes a Float and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(float i) {
        return df.format(i);
    }

    /**
     * Takes an Object and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(Object i) {
        return df.format(i);
    }

    public void sendEmbed(EmbedBuilder builder, String channelId) {
        sendEmbed(builder, channelId, false);
    }

    public void sendEmbed(EmbedBuilder builder, String channelId, boolean isSuggestion) {
        Guild guildById = Bot.getInstance().getJda().getGuildById(ProductionDiscord.getId());
        assert guildById != null;
        TextChannel textChannelById = guildById.getTextChannelById(channelId);
        Optional.ofNullable(textChannelById).ifPresent(textChannel -> textChannel.sendMessageEmbeds(builder.build()).queue(message -> {
            if (isSuggestion) {
                message.addReaction("U+2705").queue();
                message.addReaction("U+274C").queue();
            }
        }));
    }
}
