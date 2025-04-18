package me.doublenico.rm.logger;

import org.slf4j.Logger;

public class BotLogger {

    public void redirect(Logger logger){
        System.setOut(new java.io.PrintStream(System.out) {
            @Override
            public void println(String x) {
                logger.info(x);
            }
        });
    }
}