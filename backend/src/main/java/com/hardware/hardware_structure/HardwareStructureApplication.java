package com.hardware.hardware_structure;

import com.hardware.hardware_structure.core.setup.EntityInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class HardwareStructureApplication implements CommandLineRunner  {
	private final EntityInitializer initializer;

	public static void main(String[] args) {
		SpringApplication.run(HardwareStructureApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (args.length > 0 && Arrays.asList(args).contains("--populate")) {
			initializer.initializeAll();
		}
	}
}
