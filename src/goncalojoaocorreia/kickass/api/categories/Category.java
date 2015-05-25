package goncalojoaocorreia.kickass.api.categories;

/**
 * This interface represents a searchable torrent category. Each realization
 * must implement the url() method, used to construct the request url, typically
 * in the form of category:something.
 */
public interface Category {

	/**
	 * Gets the category request String.
	 *
	 * @return A http request string
	 */
	String url();
}
