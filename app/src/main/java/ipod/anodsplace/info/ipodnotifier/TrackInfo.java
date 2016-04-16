package ipod.anodsplace.info.ipodnotifier;

/**
 * @author alex
 * @date 2014-12-06
 */
public class TrackInfo {
    public String author;
    public String title;

    public static TrackInfo parse(String combined) {
        if (combined == null || "".equals(combined)) {
            return new TrackInfo();
        } else if (combined.contains(" - ")){
            String[] data = combined.split(" - ");
            return new TrackInfo(data[0], data[1]);
        }
        return new TrackInfo(null, combined);
    }

    public TrackInfo() {
    }

    public TrackInfo(String author, String title) {
         this.author = author;
         this.title = title;
    }

    public String render() {
        String text;
        if (author == null) {
            text = (title == null) ? "" : title;
        } else {
            text = title + " - " + author;
        }
        return text;
    }

    public String getTitleText() {
        return (this.title == null) ? "" : this.title;
    }

    public String getAuthorText() {
        return (this.author == null) ? "" : this.author;
    }
}
