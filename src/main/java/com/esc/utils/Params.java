package com.esc.utils;

import com.lexicalscope.jewel.cli.Option;

public interface Params {
    @Option(description = "prefix of the bot", shortName = "p", longName = "prefix", defaultValue = ".")
    String getPrefix();

    @Option(description = "token of the bot", shortName = "t", longName = "token", defaultValue = "")
    String getToken();

    @Option(helpRequest = true, description = "display help", shortName = "h")
    boolean getHelp();
}