package goncalojoaocorreia.kickass.api.categories;

public enum Tv implements Category {

	ALL("tv");

	private final String url;

	Tv(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
