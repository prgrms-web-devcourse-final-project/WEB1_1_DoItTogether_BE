package com.doittogether.platform.business.housework;

import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import java.util.List;

public interface HouseworkService {
    public List<Housework> getAllHouseworks();

    public List<Housework> getHouseworksByCategory(final String category);

    public Housework addHousework(final Housework housework);

    public void updateHousework(final Long id, final HouseworkRequest request);

    public void deleteHousework(final Long id);
}
