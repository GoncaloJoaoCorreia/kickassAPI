package goncalojoaocorreia.kickass.api.categories;

public enum Movies implements Category {

	ALL("movies"), MOVIES_3D("3d-movies"), MUSIC_VIDEOS("music-videos"), MOVIE_CLIPS("movie-clips"),
	HANDHELD("handheld-movies"), IPAD("ipad-movies");

	private final String url;

	Movies(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}

}
