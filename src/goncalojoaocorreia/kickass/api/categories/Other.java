package goncalojoaocorreia.kickass.api.categories;

public enum Other implements Category {

	ALL("other"), PICTURES("pictures"), SOUND_CLIPS("sound-clips"), COVERS("covers"),
	WALLPAPERS("wallpapers"), TUTORIALS("tutorials"), SUBTITLES("subtitles"), FONTS("fonts"),
	UNSORTED("unsorted");

	private final String url;

	Other(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
