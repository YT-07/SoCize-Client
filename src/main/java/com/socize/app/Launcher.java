package com.socize.app;

import java.io.InputStream;

import org.slf4j.LoggerFactory;

import com.socize.config.FilePath;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Launcher {
    
    public static void main(String[] args) throws Exception {
        InputStream logbackFileInputStream = Launcher.class.getClass().getResourceAsStream(FilePath.LOGBACK_CONFIG);

        configureLogback(logbackFileInputStream);
        MainApp.main(args);
    }

    /**
     * Perform configurations for the Logback logging framework.
     * 
     * @throws JoranException
     */
    private static void configureLogback(InputStream inputStream) throws JoranException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.doConfigure(inputStream);
    }
}
