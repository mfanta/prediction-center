package cz.mfanta.tip_centrum.entity.reader.provider.pinnacle;

public enum PinnacleElement {

	event {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startEventElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endEventElement();
		}

	},

	event_datetimeGMT {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startEventDateTimeElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endEventDateTimeElement();
		}

	},

	participant {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startParticipantElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endParticipantElement();
		}

	},

	participant_name {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startParticipantNameElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endParticipantNameElement();
		}

	},

	visiting_home_draw {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startVisitingHomeDrawElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endVisitingHomeDrawElement();
		}

	},

	moneyline {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startMoneylineElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endMoneylineElement();
		}

	},

	moneyline_visiting {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startMoneylineVisitingElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endMoneylineVisitingElement();
		}

	},

	moneyline_home {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startMoneylineHomeElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endMoneylineHomeElement();
		}

	},

	moneyline_draw {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startMoneylineDrawElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endMoneylineDrawElement();
		}

	},

	gamenumber {

		@Override
		public void startElement(PinnacleFixtureHandler handler) {
			handler.startGameNumberElement();
		}

		@Override
		public void endElement(PinnacleFixtureHandler handler) {
			handler.endGameNumberElement();
		}

	},
	
	// tags not used during parsing
	contestantnum,
	events,
	IsLive,
	lastContest,
	lastGame,
	league,
	moneyline_maximum,
	over_adjust,
	participants,
	period,
	period_description,
	period_number,
	period_status,
	period_update,
	periodcutoff_datetimeGMT,
	periods,
	pinnacle_line_feed,
	PinnacleFeedTime,
	rotnum,
	sporttype,
	spread,
	spread_adjust_home,
	spread_adjust_visiting,
	spread_home,
	spread_maximum,
	spread_visiting,
	total,
	total_maximum,
	total_points,
	under_adjust
	;

	public void startElement(PinnacleFixtureHandler handler) {}

	public void endElement(PinnacleFixtureHandler handler) {}

}
