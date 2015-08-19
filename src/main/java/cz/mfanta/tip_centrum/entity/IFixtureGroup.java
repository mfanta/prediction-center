package cz.mfanta.tip_centrum.entity;

public interface IFixtureGroup {

	public void merge(IFixtureGroup anotherGroup);

	public int getCount();

	public Fixture getAt(int index);

}
