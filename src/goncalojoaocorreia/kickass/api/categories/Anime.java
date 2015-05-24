package goncalojoaocorreia.kickass.api.categories;

public enum Anime implements Category {

	ALL("amv"), ANIME_MUSIC_VIDEO("amv"), ENGLISH_TRANSLATED("anglish-translated"),
	OTHER_ANIME("other-anime");

	private final String url;

	Anime(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
