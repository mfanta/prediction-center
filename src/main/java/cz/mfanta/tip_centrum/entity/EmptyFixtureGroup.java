package cz.mfanta.tip_centrum.entity;

public class EmptyFixtureGroup implements IFixtureGroup {

    public void merge(IFixtureGroup anotherGroup) {
        throw new UnsupportedOperationException();
    }

    public int getCount() {
        return 0;
    }

    public Fixture getAt(int index) {
        throw new UnsupportedOperationException();
    }
}
