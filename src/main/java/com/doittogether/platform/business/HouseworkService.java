package com.doittogether.platform.business;

import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HouseworkService {
    public List<Housework> getAllHouseworks() {
        return null;
    }

    public List<Housework> getHouseworksByCategory(String category) {
        return null;
    }

    public Housework addHousework(Housework housework) {
        return null;
    }

    public void updateHousework(Long id, HouseworkRequest request) {
    }

    public void deleteHousework(Long id) {
    }
}
