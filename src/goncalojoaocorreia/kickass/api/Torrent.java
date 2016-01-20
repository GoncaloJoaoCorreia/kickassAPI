package goncalojoaocorreia.kickass.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class Torrent {

    private final URL torrentUrl;
    private final String title;
    private final String category;
    private final long age;
    private final int seeders;
    private final int leeches;
    private final long size;
    private final boolean verified;
    private final String magnet;

    /**
     * Constructor for Torrent object.
     *
     * @param torrentUrl URL for the .torrent file
     * @param title Torrent title
     * @param category Torrent category (string format)
     * @param age Age of the torrent (in minutes)
     * @param seeders Amount of seeders
     * @param leeches Amount of leeches
     * @param size Size (in bytes)
     * @param verified whether the torrent is verified or not
     * @param magnet Magnet link
     */
    public Torrent(URL torrentUrl, String title, String category, long age,
            int seeders, int leeches, long size, boolean verified, String magnet) {
        this.torrentUrl = torrentUrl;
        this.title = title;
        this.category = category;
        this.age = age;
        this.seeders = seeders;
        this.leeches = leeches;
        this.size = size;
        this.verified = verified;
        this.magnet = magnet;
    }

    /**
     * Constructor for Torrent object. Assumes torrent is verified.
     *
     * @param torrentUrl URL for the .torrent file
     * @param title Torrent title
     * @param category Torrent category (string format)
     * @param age Age of the torrent (in minutes)
     * @param seeders Amount of seeders
     * @param leeches Amount of leeches
     * @param size Size (in bytes)
     * @param magnet Magnet link
     */
    public Torrent(URL torrentUrl, String title, String category, long age,
            int seeders, int leeches, long size, String magnet) {
        this.torrentUrl = torrentUrl;
        this.title = title;
        this.category = category;
        this.age = age;
        this.seeders = seeders;
        this.leeches = leeches;
        this.size = size;
        this.verified = true;
        this.magnet = magnet;
    }

    /**
     * Gets the torrent title.
     *
     * @return the torrent title
     */
    public String title() {
        return title;
    }

    /**
     * Gets the URL for the .torrent file.
     *
     * @return the torrent URL.
     */
    public URL torrentURL() {
        return this.torrentUrl;
    }

    /**
     * Gets the torrent age.
     *
     * @return time since the torrent was posted, in minutes.
     */
    public long age() {
        return this.age;
    }

    /**
     * Gets the amount of seeders.
     *
     * @return amount of seeders
     */
    public int seeders() {
        return this.seeders;
    }

    /**
     * Gets the amount of leeches.
     *
     * @return amount of leeches
     */
    public int leeches() {
        return this.leeches;
    }

    /**
     * Gets the size of the torrent, in bytes.
     *
     * @return the size of the torrent
     */
    public long size() {
        return this.size;
    }

    /**
     * Gets the torrent size in a human readable format. Source:
     * https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
     *
     * @param si True to use SI units, false to use binary units.
     * @return torrent size in a human readable format.
     */
    public String formatSize(boolean si) {
        int unit = si ? 1000 : 1024;
        if (size < unit) {
            return size + " B";
        }
        int exp = (int) (Math.log(size) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", size / Math.pow(unit, exp), pre);
    }

    /**
     * Check the verified status of the torrent.
     *
     * @return true if the torrent is verified, false otherwise.
     */
    public boolean isVerified() {
        return this.verified;
    }

    /**
     * Returns the torrent's magnet link.
     *
     * @return A string containing the torrent's magnet link
     */
    public String magnetLink() {
        return this.magnet;
    }

    /**
     * Downloads the .torrent file.
     *
     * @param pathToSave Path to directory where the .torrent will be saved
     * @return File object that represents the downloaded .torrent
     * @throws IOException
     */
    public File download(String pathToSave) throws IOException {
        new File(pathToSave).mkdirs();
        StringBuilder path = new StringBuilder(pathToSave);
        path.append(File.separator);
        path.append(this.title);
        path.append(".torrent");
        File f = new File(path.toString());

        try (InputStream is = new GZIPInputStream(torrentUrl.openStream())) {
            Files.copy(is, Paths.get(path.toString()));
            is.close();
        }

        return f;
    }

    /**
     * Returns a String containing basic info about the torrent.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.
                format("Title: %s\n\tURL: %s\n\tCategory: %s\n\tSeeders: %d\n\tLeeches: %d\n",
                        this.title, this.torrentUrl, this.category, this.seeders, this.leeches);
    }

    /**
     * Takes a torrent age in a format such as '2 weeks' and transforms it to a
     * numeric value for comparison purposes.
     *
     * @param age Human-readable representation of torrent age
     * @return Long value representing the given age in minutes
     */
    public static long parseAge(String age) {
        //A non-breaking space is used in the age, for some bloody reason
        String[] s = age.split("[\\s\\u00A0]+");
        long ageParsed = Long.parseLong(s[0]);
        switch (s[1]) {
            case "years":
            case "year":
                ageParsed *= 525_948;
            case "months":
            case "month":
                ageParsed *= 43_829;
                break;
            case "weeks":
            case "week":
                ageParsed *= 10_080;
                break;
            case "days":
            case "day":
                ageParsed *= 1440;
                break;
            case "hours":
            case "hour":
                ageParsed *= 60;
                break;
            case "minutes":
            case "minute":
                break;
        }

        return ageParsed;
    }

    /**
     * Takes a torrent size in a format such as '2.89 GB' and transforms it to a
     * numeric value for comparison purposes.
     *
     * @param size Human-readable representation of torrent size
     * @return Long value representing the given size in bytes
     */
    public static long parseSize(String size) {
        String[] s = size.split("\\s+");
        double sizeParsed = Double.parseDouble(s[0]);
        switch (s[1]) {
            case "TB":
                sizeParsed *= 1_000_000_000_000L;
                break;
            case "GB":
                sizeParsed *= 1_000_000_000L;
                break;
            case "MB":
                sizeParsed *= 1_000_000;
                break;
            case "KB":
                sizeParsed *= 1000;
                break;
            //TODO: Find out what's the byte string representation in kat
        }

        return (long) sizeParsed;
    }

}
