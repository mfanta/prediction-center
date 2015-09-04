package cz.mfanta.tip_centrum.entity;

public interface IFixtureGroup {

	void merge(IFixtureGroup anotherGroup);

	int getCount();

	Fixture getAt(int index);

}
