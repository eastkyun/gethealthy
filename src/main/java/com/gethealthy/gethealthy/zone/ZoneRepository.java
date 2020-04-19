package com.gethealthy.gethealthy.zone;

import com.gethealthy.gethealthy.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ZoneRepository extends JpaRepository<Zone,Long> {
    Zone findByCityAndProvince(String cityName, String provinceName);
}
