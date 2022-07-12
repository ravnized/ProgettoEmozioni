package emotionalsongs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class EmotionalSongs {

    private static final String filepathSong = "data/Canzoni.dati";

//Initialize Every variable to empty

    String title, author = "", genre = "";
    int year = 0;

    //Costructor for full songs
    public EmotionalSongs(String title, String author, int year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }


    /*
    ReadSong():
    If the file doesn't exist return the empty list
    Create an instance of buffered reader with new FileReader
    while i have something written in the file,
    i create new instances of EmotionalSong and they get added to the final list of songs red
    i close the reader for safety
    returned the list of object EmotionalSong
     */

    public static LinkedList<EmotionalSongs> readSong() {
        LinkedList<EmotionalSongs> list = new LinkedList<>();
        File file = new File(filepathSong);
        if (!file.exists()) return list;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepathSong));
            String[] campiTotali;
            while (true) {
                int i = 0;
                String readerLiner = reader.readLine();
                if (readerLiner == null || readerLiner.equals("")) break;
                campiTotali = readerLiner.split(";");
                EmotionalSongs canzone = new EmotionalSongs(campiTotali[i], campiTotali[i + 1], Integer.parseInt(campiTotali[i + 2]), campiTotali[i + 3]);
                list.addLast(canzone);
            }
            reader.close();

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean canzoniVisualizer() {

        LinkedList<EmotionalSongs> canzoni = readSong();
        if (canzoni != null) {
            for (EmotionalSongs canzone : canzoni) {
                System.out.println("\n");
                System.out.println("Titolo: " + canzone.title);
                System.out.println("Autore: " + canzone.author);
                System.out.println("Anno: " + canzone.year);
                System.out.println("Genere: " + canzone.genre);
                System.out.println("\n");
            }
            return true;
        }
        return false;

    }

    public static EmotionalSongs cercaBranoMusicale(String title) {
        EmotionalSongs canzoneTrovata = new EmotionalSongs("", "", 0, "");
        Objects.requireNonNull(title);
        if (title.equals(" ")) {
            return canzoneTrovata;
        }
        LinkedList<EmotionalSongs> canzoni = readSong();
        Objects.requireNonNull(canzoni);
        if (canzoni.size() == 0) {
            return canzoneTrovata;
        }
        for (EmotionalSongs canzone : canzoni) {
            if (canzone.title.equals(title)) {
                canzoneTrovata.title = canzone.title;
                canzoneTrovata.author = canzone.author;
                canzoneTrovata.year = canzone.year;
                canzoneTrovata.genre = canzone.genre;
                return canzoneTrovata;
            }
        }
        return null;
    }

    public static EmotionalSongs cercaBranoMusicale(String author, int year) {
        EmotionalSongs canzoneTrovata = new EmotionalSongs("", "", 0, "");
        Objects.requireNonNull(author);
        if (author.equals(" ") || year == 0) {
            return canzoneTrovata;
        }

        LinkedList<EmotionalSongs> canzoni = readSong();
        Objects.requireNonNull(canzoni);
        if (canzoni.size() == 0) {
            return canzoneTrovata;
        }
        for (EmotionalSongs canzone : canzoni) {
            if (canzone.author.equals(author) && canzone.year == year) {
                canzoneTrovata.title = canzone.title;
                canzoneTrovata.author = canzone.author;
                canzoneTrovata.year = canzone.year;
                canzoneTrovata.genre = canzone.genre;
                return canzoneTrovata;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        //Emotion.inserisciEmozioniBrano("Smells like teen spirit","lorenzo.tramaglino","bobo1974","Amazement",5,"kekk");
        //cercaBranoMusicale("Billie Holiday",1939);
        //Emotion.riassuntoEmozione("Pippo");
    }

}
