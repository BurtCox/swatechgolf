package swatechgolf.utility;

public enum MessageType {
	VOLUNTEER("SWA Volunteer Signup"), 
	ADOPT_A_TEAM("Adopt-A-Team Sponsor"), 
	SPONSOR("Sponsor Registration"), 
	SWA_GOLFER("SWA Golfer Signup"), 
	GOLFER_UPDATE("Sponsor Golfer Update"), 
	CHANCE_TO_WIN("Raffle Donation"),
	EMAIL("Email"),
	UNKNOWN("Unknown Message Type");

	private final String _key;

	MessageType(String key) {
		_key = key;
	}

	public static MessageType getType(String key) {
		MessageType configuration = UNKNOWN;

		for (MessageType config : MessageType.values()) {
			if (key.contains(config._key)) {
				configuration = config;
			}
		}

		return configuration;
	}

}
