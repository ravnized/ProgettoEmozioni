package emotionalsongs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;

public class Playlist {
    private static final String filepathPlaylist = "data/Playlist.dati";
    static char CSV_SEPARATOR = ';';
    static char PLAYLIST_SEPARATOR = '-';
    String nomePlaylist = "";
    LinkedList<EmotionalSongs> brani;


    public Playlist(String nomePlaylist, LinkedList<EmotionalSongs> brani) {
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
                singoleCanzoni = campiTotali[i + 1].split("-");
                for (int j = 0; j < singoleCanzoni.length; j++) {
                    canzoniList.addLast(new EmotionalSongs(singoleCanzoni[j], "", 0, ""));
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
        Objects.requireNonNull(playlist);
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepathPlaylist), StandardCharsets.UTF_8));

            for (Playlist singolaPlaylist : playlist) {
                String oneLine = singolaPlaylist.nomePlaylist + CSV_SEPARATOR;
                for (EmotionalSongs brani : singolaPlaylist.brani) {
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


    public static boolean registraPlaylist(String titlePlaylist, LinkedList<EmotionalSongs> brani, String userid, String password) {
        Objects.requireNonNull(titlePlaylist);
        Objects.requireNonNull(brani);
        if (titlePlaylist.equals("") || titlePlaylist.equals(" ") || brani.size() == 0) {
            return false;
        }
        boolean autenticato = false;
        autenticato = Registration.login(userid, password);
        if (autenticato) {
            Playlist playlist = new Playlist(titlePlaylist, brani);
            LinkedList<Playlist> playlistsRegistrate = readPlaylist();
            playlistsRegistrate.addLast(playlist);
            writePlaylist(playlistsRegistrate);
            return true;
        }
        return false;


    }


}
