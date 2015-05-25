package goncalojoaocorreia.kickass.api;

import goncalojoaocorreia.kickass.api.categories.Category;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class represents a search for torrents. Use the static methods to create
 * new searches.
 */
public class Search extends ArrayList<Torrent> {

	private static final String SEARCH_URL = "https://kat.cr/usearch/";
	private static final String CATEGORY_SEARCH = " category:";
	private static final String ORDER_SIZE_DESC = "/?field=size&sorder=desc";
	private static final String ORDER_SIZE_ASC = "/?field=size&sorder=asc";
	private static final String ORDER_AGE_DESC = "/?field=time_add&sorder=desc"; //newest first
	private static final String ORDER_AGE_ASC = "/?field=time_add&sorder=asc"; //Oldest first
	private static final String ORDER_SEEDS_DESC = "/?field=seeders&sorder=desc";
	private static final String ORDER_SEEDS_ASC = "/?field=seeders&sorder=asc";
	private static final String ORDER_LEECH_DESC = "/?field=leechers&sorder=desc";
	private static final String ORDER_LEECH_ASC = "/?field=leechers&sorder=asc";

	private static final String TORRENT_DOWNLOAD = "[title=Download torrent file]";

	private static int timeout = 1000;

	public enum SortOption {

		SMALLEST_FIRST, LARGEST_FIRST, OLDEST_FIRST, YOUNGEST_FIRST, MOST_SEEDERS, LEAST_SEEDERS, MOST_LEECHES, LEAST_LEECHES
	}

	//TODO: .next() method to iterate through pages
	//Store search url in object when doing newSearch()
	private Search() {
		super();
	}

	private static Search runSearch(URL url) throws IOException {
		Document doc;
		try {
			doc = Jsoup.parse(url, timeout);
		} catch (HttpStatusException ex) {
			//404 error means no torrents found in search, so we return an empty list
			return new Search();
		}
		Search search = new Search();

		Elements torrents = doc.select("tr").not(".firstr");

		//First one in list repeats, so we skip it
		for (Element torrent : torrents.subList(1, torrents.size())) {
			URL download = new URL(torrent.select(TORRENT_DOWNLOAD).get(0).
				getAllElements().get(0).attr("href"));

			String title = torrent.select("a.cellMainLink").get(0).text();

			//TODO: Check if verified
			//Select info table cells
			Elements info = torrent.select("td.center");
			long size = Torrent.parseSize(info.get(0).text());
			long age = Torrent.parseAge(info.get(2).text());
			int seeds = Integer.parseInt(info.get(3).text());
			int leech = Integer.parseInt(info.get(4).text());

			String category = torrent.select("span[id]").get(0).text();

			search.
				add(new Torrent(download, title, category, age, seeds, leech, size));
		}
		return search;
	}

	private static String getSortQuery(SortOption sort) {
		String sortQuery = "";
		switch (sort) {
			case SMALLEST_FIRST:
				sortQuery = ORDER_SIZE_ASC;
				break;
			case LARGEST_FIRST:
				sortQuery = ORDER_SIZE_DESC;
				break;
			case OLDEST_FIRST:
				sortQuery = ORDER_AGE_ASC;
				break;
			case YOUNGEST_FIRST:
				sortQuery = ORDER_AGE_DESC;
				break;
			case MOST_SEEDERS:
				sortQuery = ORDER_SEEDS_DESC;
				break;
			case LEAST_SEEDERS:
				sortQuery = ORDER_SEEDS_ASC;
				break;
			case MOST_LEECHES:
				sortQuery = ORDER_LEECH_DESC;
				break;
			case LEAST_LEECHES:
				sortQuery = ORDER_LEECH_ASC;
				break;
		}
		return sortQuery;
	}

	public static Search newSearch(String query) throws IOException {
		String urlStr = SEARCH_URL + URLEncoder.encode(query, "UTF-8");
		URL url = new URL(urlStr);

		return Search.runSearch(url);
	}

	public static Search newSearch(String query, Category category) throws IOException {
		String urlStr = SEARCH_URL + URLEncoder.
			encode(query + CATEGORY_SEARCH + category.url(), "UTF-8");
		URL url = new URL(urlStr);

		return Search.runSearch(url);
	}

	public static Search newSearch(String query, SortOption sort) throws IOException {
		String urlStr = SEARCH_URL + URLEncoder.
			encode(query + getSortQuery(sort), "UTF-8");
		URL url = new URL(urlStr);
		return Search.runSearch(url);
	}

	public static Search newSearch(String query, Category category,
								   SortOption sort) throws IOException {
		String urlStr = SEARCH_URL + URLEncoder.
			encode(query + CATEGORY_SEARCH + category.url() + getSortQuery(sort), "UTF-8");
		URL url = new URL(urlStr);
		return Search.runSearch(url);
	}

	/**
	 * Sets the timeout used by the parsing methods. Default value is 1000.
	 *
	 * @param t Time in milliseconds
	 */
	public static void setTimeout(int t) {
		timeout = t;
	}

}
