package com.br.rodrigo.pereira.sensorlogger.util.configs;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class PropertiesConfig {
    private String headSalt;
    private String tailSalt;

    public PropertiesConfig(){
        this.headSalt = "rodrigo_salt";
        this.tailSalt = "@#!#@hjs97d079ae61chk";
    }


}
