package com.gethealthy.gethealthy.zone;

import com.gethealthy.gethealthy.domain.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    @PostConstruct//빈이 초기화 되고 실행되는 애너테이션
    public void initZoneData() throws IOException {
        if(zoneRepository.count()==0){
            Resource resource = new ClassPathResource("zones_kr.csv");
            List<Zone> zoneList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream().map(line -> {
                String[] split = line.split(",");
                return Zone.builder().city(split[0]).localNameOfCity(split[1]).province(split[2]).build();
            }).collect(Collectors.toList());
            zoneRepository.saveAll(zoneList);
        }
    }
}
