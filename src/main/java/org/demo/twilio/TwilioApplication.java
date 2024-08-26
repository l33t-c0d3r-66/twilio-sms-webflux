package org.demo.twilio;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.demo.twilio.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwilioApplication {

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }


    public static void main(String[] args) {
        SpringApplication.run(TwilioApplication.class, args);
    }

}
