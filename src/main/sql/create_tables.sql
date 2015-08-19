create table Team (
	name										varchar(100)	not null		primary key
);

create table TeamAlias (
	alias										varchar(100)	not null        primary key,
	name										varchar(100)	not null
)

create table Odds (
	fixtureId								bigint		not null		primary key,
	homeOdds								integer		not null,
	drawOdds								integer		not null,
	awayOdds								integer		not null
);

create table Prediction (
	fixtureId								bigint		not null		primary key,
	homeTeamScore						integer,
	awayTeamScore						integer
);

create table Fixture (
	fixtureId								bigint		not null		primary key,
	homeTeamName						varchar(100)	not null,
	awayTeamName						varchar(100)	not null,
	competitionName                     varchar(50),
	fixtureDate							datetime			not null,
);

create table Result (
	fixtureId								bigint		not null		primary key,
	homeTeamScore						integer,
	awayTeamScore						integer
);
