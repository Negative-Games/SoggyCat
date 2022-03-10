package me.joehosten.bot.api.command.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommandType {

    DEV("912413951164289115"),
    GLOBAL(null);

    // Guild ID
    private final String id;
}
