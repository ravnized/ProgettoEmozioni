package emotionalsongs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Playlist {
    private static final String filepathPlaylist = "data/Playlist.dati";
    static char CSV_SEPARATOR = ';';
    static char PLAYLIST_SEPARATOR = '-';
    String nomePlaylist = "";
    LinkedList<EmotionalSongs> brani = null;



    public Playlist (String nomePlaylist, LinkedList<EmotionalSongs> brani){
        this.nomePlaylist = nomePlaylist;
        this.brani = brani;
    }

    public static LinkedList<Playlist> readPlaylist() {
        LinkedList<Playlist> list = new LinkedList<>();
        File file = new File(filepathPlaylist);
        if (!file.exists()) return list;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepathPlaylist));
            String[] campiTotali;
            String[] singoleCanzoni;
            LinkedList<EmotionalSongs> canzoniList = new LinkedList<>();
            while (true) {
                int i = 0;
                String readerLiner = reader.readLine();
                if (readerLiner == null || readerLiner.equals("")) break;
                campiTotali = readerLiner.split(";");
                singoleCanzoni = campiTotali[i+1].split("-");
                for(int j=0; j< singoleCanzoni.length ; j++){
                    canzoniList.addLast(new EmotionalSongs(singoleCanzoni[j]));
                }
                Playlist playlist = new Playlist(campiTotali[i], canzoniList);
                list.addLast(playlist);
            }
            reader.close();

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writePlaylist(LinkedList<Playlist> playlist) {

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepathPlaylist), StandardCharsets.UTF_8));

            for (Playlist singolaPlaylist : playlist) {
                String oneLine = singolaPlaylist.nomePlaylist + CSV_SEPARATOR;
                for (EmotionalSongs brani : singolaPlaylist.brani){
                    oneLine = oneLine + brani.title + "-";
                }
                oneLine = oneLine + CSV_SEPARATOR;
                bw.write(oneLine);
                bw.newLine();

            }
            bw.flush();


            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean registraPlaylist(String titlePlaylist, LinkedList<EmotionalSongs> brani){
        Playlist playlist = new Playlist(titlePlaylist, brani);
        LinkedList<Playlist> playlistsRegistrate = readPlaylist();
        playlistsRegistrate.addLast(playlist);
        writePlaylist(playlistsRegistrate);
        return true;
    }






    public static void main(String args[]){

        LinkedList<EmotionalSongs> Songs = new LinkedList<>();
        Songs.add(new EmotionalSongs("new romam"));
        Songs.add(new EmotionalSongs("pippo inzaghi"));

        registraPlaylist("La mia wanderfgu", Songs);



    }


}
