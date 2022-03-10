package me.joehosten.bot.core.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.joehosten.bot.core.properties.PropertiesConfiguration;

// Utility class for things needed to identify or do things
// to the Production Discord environment
@UtilityClass
public class ProductionDiscord {

    @Getter
    private String id;
    @Getter
    private String mutedRoleID;
    @Getter
    private String logsChannelID;
    @Getter
    private String suggestionsChannel;

    public void init(PropertiesConfiguration configuration) {
        id = configuration.getString("main.discord");
        mutedRoleID = configuration.getString("muted.role");
        logsChannelID = configuration.getString("logs.channel");
        suggestionsChannel = configuration.getString("suggestions.channel");
    }
}
