package me.doublenico.rm;

import me.doublenico.rm.logger.BotLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RMBackend {

    private static final Logger logger = LoggerFactory.getLogger(RMBackend.class);

    public static void main(String[] args) {
        new BotLogger().redirect(logger);
    }
}
