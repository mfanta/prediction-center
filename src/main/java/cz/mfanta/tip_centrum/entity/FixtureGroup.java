package cz.mfanta.tip_centrum.entity;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class FixtureGroup extends HashMap<Long, Fixture> implements IFixtureGroup {

	private static final long serialVersionUID = 1L;
	
	private final List<Fixture> fixtureList;
	
	public FixtureGroup() {
		fixtureList = Lists.newArrayList();
	}

	public void addFixture(Fixture fixture) {
		fixtureList.add(fixture);
		put(fixture.getFixtureId(), fixture);
	}

	@Override
	public void merge(IFixtureGroup anotherGroup) {
		final int fixtureCount = anotherGroup.getCount();
		for (int i = 0; i < fixtureCount; i++) {
			final Fixture newFixture = anotherGroup.getAt(i);
			final Fixture existingFixture = get(newFixture.getFixtureId());
			// if the fixture already exists, do not add the new fixture!
			if (existingFixture == null) {
				addFixture(newFixture);
			}
		}
	}

	@Override
	public int getCount() {
		return fixtureList.size();
	}

	public Fixture getAt(int index) {
		return fixtureList.get(index);
	}

}
