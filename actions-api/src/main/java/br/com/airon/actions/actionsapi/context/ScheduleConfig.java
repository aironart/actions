package br.com.airon.actions.actionsapi.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("br.com.airon.actions.actionsapi")
public class ScheduleConfig {

	public ScheduleConfig() {
	}

}
