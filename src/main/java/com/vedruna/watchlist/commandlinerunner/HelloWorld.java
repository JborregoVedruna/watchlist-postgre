package com.vedruna.watchlist.commandlinerunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloWorld implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        log.info("----------------------------------- HELLO WORLD -----------------------------------");
    }
    
}
