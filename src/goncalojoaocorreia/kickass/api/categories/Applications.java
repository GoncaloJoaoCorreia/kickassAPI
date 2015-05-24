package goncalojoaocorreia.kickass.api.categories;

public enum Applications implements Category {

	ALL("applications"), WINDOWS("windows"), MAC("mac-software"), UNIX("unix"), LINUX("linux"),
	IOS("ios"), ANDROID("android"), HANDHELD("handheld-applications"), OTHER("other-applications");

	private final String url;

	Applications(String url) {
		this.url = url;
	}

	@Override
	public String url() {
		return this.url;
	}
}
