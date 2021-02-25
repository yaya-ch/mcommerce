package com.mproduits.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
public class AppConfigProps {

    private int limiteDeProduits;

    public int getLimiteDeProduits() {
        return limiteDeProduits;
    }

    public void setLimiteDeProduits(int limiteDeProduits) {
        this.limiteDeProduits = limiteDeProduits;
    }
}
