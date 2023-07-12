package com.example.reservation.config;

import com.example.reservation.entity.Device;
import com.example.reservation.entity.respository.DeviceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataLoadRunner implements CommandLineRunner {

    private final DeviceRepository deviceRepository;

    public DataLoadRunner(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Device> devices = Stream.of(
                "Samsung Galaxy S9",
                "Samsung Galaxy S8",
                "Samsung Galaxy S8",
                "Motorola Nexus 6",
                "Oneplus 9",
                "Apple iPhone 13",
                "Apple iPhone 12",
                "Apple iPhone 11",
                "iPhone X",
                "Nokia 3310"
        ).map(Device::new).toList();
        deviceRepository.saveAll(devices);
    }
}
