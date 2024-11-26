package com.doittogether.platform.business;

import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HouseworkServiceImpl implements HouseworkService {
    @Override
    public List<Housework> getAllHouseworks() {
        return null;
    }

    @Override
    public List<Housework> getHouseworksByCategory(final String category) {
        return null;
    }

    @Override
    public Housework addHousework(final Housework housework) {
        return null;
    }

    @Override
    public void updateHousework(final Long id, final HouseworkRequest request) {
    }

    @Override
    public void deleteHousework(final Long id) {
    }
}
