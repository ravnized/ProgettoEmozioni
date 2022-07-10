package emotionalsongs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Emotion {

    String title = "";
    int amazement = 0, solemnity = 0,  tenderness = 0, nostalgia = 0, calmness = 0, power = 0, joy = 0, tension = 0, sadness = 0;
    int intensitaEmozione = 0;
    String tipoEmozione = "";
    String note = "";
    static char CSV_SEPARATOR = ';';


    public Emotion(String title,String tipoEmozione ,int intensitaEmozione, String note){
        this.title = title;
        this.tipoEmozione = tipoEmozione;
        this.intensitaEmozione = intensitaEmozione;
        this.note = note;
    }


    private static final String filepathEmotion = "data/Emozioni.dati";
    public static LinkedList<Emotion> readEmozioni(){

            LinkedList<Emotion> list = new LinkedList<>();
            File file = new File(filepathEmotion);
            if (!file.exists()) return list;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filepathEmotion));
                String[] campiTotali;
                while (true) {
                    int i = 0;
                    String readerLiner = reader.readLine();
                    if (readerLiner == null || readerLiner.equals("")) break;
                    campiTotali = readerLiner.split(";");
                    Emotion emozione = new Emotion(campiTotali[i], campiTotali[i+1],Integer.parseInt(campiTotali[i+2]),campiTotali[i+3]);
                    list.addLast(emozione);
                }
                reader.close();

                return list;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

    }

    public static boolean writeEmozioni(LinkedList<Emotion> listEmotion) {
        final int arraySize = listEmotion.size();
        Emotion[] arrayEmotion = new Emotion[arraySize];
        int i = 0;



            for (Emotion emozione : listEmotion) {
                arrayEmotion[i] = emozione;
                i++;
            }

            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepathEmotion), StandardCharsets.UTF_8));

                for (Emotion emozione : arrayEmotion) {
                    String oneLine =
                                    emozione.title +
                                    CSV_SEPARATOR +
                                            emozione.tipoEmozione +
                                            CSV_SEPARATOR +
                                            emozione.intensitaEmozione +
                                            CSV_SEPARATOR +
                                            emozione.note +
                                            CSV_SEPARATOR ;

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
    public static boolean registraEmozioni (String author,int year, String userid, String password, String tipoEmozione, int intensitaEmozione, String note){
        boolean autenticato = false;
        autenticato = Registration.login(userid,password);
        if (!autenticato){
            return false;
        }

        LinkedList<Emotion> emotionList = readEmozioni();

        if (emotionList!=null){
            EmotionalSongs canzoneTrovata = EmotionalSongs.cercaBranoMusicale(author,year);
            Emotion emozione = new Emotion(canzoneTrovata.title,tipoEmozione,intensitaEmozione,note);
            if (emotionList!= null){
                emotionList.addLast(emozione);
                writeEmozioni(emotionList);
                return true;
            }
        }
        return false;
    }

    public static boolean inserisciEmozioniBrano (String title, String userid, String password, String tipoEmozione, int intensitaEmozione, String note){
        boolean autenticato = false;
        autenticato = Registration.login(userid,password);
        if (!autenticato){
            return false;
        }

        LinkedList<Emotion> emotionList = readEmozioni();

        if (emotionList!=null){

            Emotion emozione = new Emotion(title, tipoEmozione,  intensitaEmozione,  note);
            if (emotionList!= null){
                emotionList.addLast(emozione);
                writeEmozioni(emotionList);
                return true;
            }
        }
        return false;
    }
}
