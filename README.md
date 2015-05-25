# kickassAPI

An unofficial Java API for kickass.to partially inspired by https://github.com/fm4d/KickassAPI

## How to get it working

Compile the source or download the .jar binary directly and add it to your classpath.
You need the [jsoup binaries](http://jsoup.org/download) in your classpath to compile the source.

## Usage

``` Java
import goncalojoaocorreia.kickass.api.Search;
import goncalojoaocorreia.kickass.api.Search.SortOption;
import goncalojoaocorreia.kickass.api.Torrent;
import goncalojoaocorreia.kickass.api.categories.Movies;


//Find the first 25 results of 'avengers 1080p' search
Search.newSearch("avengers 1080p");

//Find the first 25 results of 'avengers 1080p' search, in the Movies > All category only
Search.newSearch("avengers 1080p", Movies.ALL);

//Find the first 25 results of 'avengers 1080p' search, ordered by amount of seeders
Search.newSearch("avengers 1080p", SortOption.MOST_SEEDERS);

//Find the first 25 results of 'avengers 1080p' search, in the Movies > All category only,
//ordered by amount of seeders
Search.newSearch("avengers 1080p", Movies.ALL, SortOption.MOST_SEEDERS);

//Print basic info about the search results
for (Torrent torrent : Search.newSearch("avengers")) {
	System.out.println(torrent);
}

//Download selected torrent to 'kickass/torrents' folder
torrent.download("kickass/torrents");

//Get various information about selected torrent
torrent.title();		//The torrent's title
torrent.torrentURL();	//URL to .torrent file
torrent.seeders();		//Amount of seeds
torrent.leeches();		//Amount of leeches
torrent.age();			//Age of torrent (in minutes)
torrent.size();			//Size of torrent (in bytes)
```
