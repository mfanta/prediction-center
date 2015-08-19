package cz.mfanta.tip_centrum.entity.reader.provider.pinnacle;

public enum PinnacleVenue {

	Home {

		@Override
		public void defineVenue(PinnacleFixtureHandler handler) {
			handler.setHomeVenue();
		}

	},

	Draw {

		@Override
		public void defineVenue(PinnacleFixtureHandler handler) {
			handler.setDrawVenue();
		}

	},
	Visiting {

		@Override
		public void defineVenue(PinnacleFixtureHandler handler) {
			handler.setAwayVenue();
		}

	};

	public abstract void defineVenue(PinnacleFixtureHandler handler);

}
