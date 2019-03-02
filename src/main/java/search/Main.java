package search;

public class Main {
    // The dataset to index and search.
    // MUST be the name of one of the folders in the 'data' folder.
<<<<<<< HEAD
    public static final String DATA_FOLDER_NAME = "C:/Users/tyler/Programming/Java Programs/CSE 373/HW5/hw5-rhinoceros/data/wikipedia-with-spam";
=======
    public static final String DATA_FOLDER_NAME = "gutenberg";
>>>>>>> 0998de5498b1f23952c5f5af988d5ed0eac120b9

    // The name of your search engine (feel free to change this).
    public static final String SITE_TITLE = "Noodle";

    // The port to serve your web server on.
    // You can ignore this constant. If you're familiar with web development
    // and know what ports are, feel free to change this if it's convenient.
    public static final int PORT = 8080;

    public static void main(String[] args) {
        System.out.println("Indexing web pages...");
        SearchEngine engine = new SearchEngine(DATA_FOLDER_NAME);

        System.out.println("Setting up web server...");
        Webapp app = new Webapp(engine, SITE_TITLE, PORT);

        System.out.println(String.format(
                "Ready! Open 'http://localhost:%d' in your web browser to access the search engine.",
                PORT));
        app.launch();
    }
}
