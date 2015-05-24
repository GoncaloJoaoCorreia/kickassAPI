package goncalojoaocorreia.kickass.api.categories;

public enum Books implements Category {

	ALL("books"), EBOOKS("ebooks"), COMICS("comics"), MAGAZINES("magazines"), TEXTBOOKS("textbooks"),
	FICTION("fiction"), NON_FICTION("non-fiction"), AUDIO_BOOKS("audio-books"), ACADEMIC("academic"),
	POETRY("poetry"), NEWSPAPERS("newspapers"), OTHER("other-books");
	private final String url;

	Books(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
