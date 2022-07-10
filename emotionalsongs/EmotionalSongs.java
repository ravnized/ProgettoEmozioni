package emotionalsongs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class EmotionalSongs {
    /*
    arraySong [0] -> titolo
    arraySong [1] -> autore
    arraySong [2] -> anno
    arraySong [3] -> genere
    */
    private static final String filepathSong= "data/Canzoni.dati";

    String title="",author = "", genre = "";
    int year=0;
    public EmotionalSongs(String title, String author,int year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }
    public EmotionalSongs(String title){
        this.title = title;
    }

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
        return null;
    }

    public static boolean canzoniVisualizer(){

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
    public static EmotionalSongs cercaBranoMusicale(String title){
        if (title== null || title.equals(" ")){
            return null;
        }
        EmotionalSongs canzoneTrovata = new EmotionalSongs("","",0, "");
       LinkedList<EmotionalSongs> canzoni = readSong();

       for (EmotionalSongs canzone: canzoni){
           if(canzone.title.equals(title)){
               canzoneTrovata.title = canzone.title;
               canzoneTrovata.author = canzone.author;
               canzoneTrovata.year =  canzone.year;
               canzoneTrovata.genre = canzone.genre;
               return canzoneTrovata;
           }
       }
        return null;
    }
    public static EmotionalSongs cercaBranoMusicale(String author, int year){
        if (author== null || author.equals( " ")|| year == 0){
            return null;
        }
        EmotionalSongs canzoneTrovata = new EmotionalSongs("","",0, "");
        LinkedList<EmotionalSongs> canzoni = readSong();

        for (EmotionalSongs canzone: canzoni){
            if(canzone.author.equals(author) && canzone.year == year ){
                canzoneTrovata.title = canzone.title;
                canzoneTrovata.author = canzone.author;
                canzoneTrovata.year =  canzone.year;
                canzoneTrovata.genre = canzone.genre;
                return canzoneTrovata;
            }
        }
        return null;
    }


    void visualizzaEmozioneBrano(){

    }



    public static void main(String[] args){
        //canzoniVisualizer();
        Emotion.inserisciEmozioniBrano("Smells like teen spirit","lorenzo.tramaglino","bobo1974","Amazement",5,"kekk");
        //cercaBranoMusicale("Billie Holiday",1939);
    }

}
