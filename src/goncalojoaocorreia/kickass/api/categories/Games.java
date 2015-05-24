package goncalojoaocorreia.kickass.api.categories;

public enum Games implements Category {

	ALL("games"), PC("pc-games"), MAC("mac-games"), PS2("ps2"), XBOX360("xbox360"),
	XBOX_ONE("xbox-one"), WII("wii"), HANDHELD("handheld-games"), NSD("nds"), PSP("psp"),
	PS3("ps3"), PS4("ps4"), PS_VITA("ps-vita"), IOS("iso-games"), ANDROID("android-games"),
	OTHER("other-games");

	private final String url;

	Games(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
