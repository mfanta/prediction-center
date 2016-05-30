package cz.mfanta.tip_centrum.service.event;

import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FixturesSelectedEvent {

    private final IFixtureGroup selectedFixtures;
}
