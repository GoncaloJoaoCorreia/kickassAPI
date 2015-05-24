package goncalojoaocorreia.kickass.api.categories;

public enum Xxx implements Category {

	ALL("xxx"), VIDEO("xxx-video"), HD_VIDEO("xxx-hd-video"), ULTRA_HD("xxx-ultrahd"),
	PICTURES("xxx-pictures"), MAGAZINES("xxx-magazines"), BOOKS("xxx-books"), HENTAI("hentai"),
	GAMES("xxx-games"), OTHER("other-xxx");

	private final String url;

	Xxx(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
