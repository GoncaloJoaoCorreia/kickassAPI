package goncalojoaocorreia.kickass.api.categories;

public enum Music implements Category {

	ALL("music"), MP3("mp3"), AAC("aac"), LOSSLESS("lossless"), TRANSCODE("transcode"),
	SOUNDTRACK("soundrack"), RADIO_SHOWS("radio-shows"), KARAOKE("karaoke"), OTHER("other-music");

	private final String url;

	Music(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
