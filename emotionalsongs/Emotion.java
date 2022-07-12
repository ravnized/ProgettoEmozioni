package emotionalsongs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;

public class Emotion {

    private static final String filepathEmotion = "data/Emozioni.dati";
    static char CSV_SEPARATOR = ';';
    String title = "";
    int intensitaEmozione = 0;
    String tipoEmozione = "";
    String note = "";

    public Emotion(String title, String tipoEmozione, int intensitaEmozione, String note) {
        this.title = title;
        this.tipoEmozione = tipoEmozione;
        this.intensitaEmozione = intensitaEmozione;
        this.note = note;
    }

    public static LinkedList<Emotion> readEmozioni() {

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
                Emotion emozione = new Emotion(campiTotali[i], campiTotali[i + 1], Integer.parseInt(campiTotali[i + 2]), campiTotali[i + 3]);
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
                                CSV_SEPARATOR;

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

    public static boolean inserisciEmozioniBrano(String userid, String password, String tipoEmozione, int intensitaEmozione, String note, String title, String author, int year) {
        Objects.requireNonNull(userid);
        Objects.requireNonNull(password);
        Objects.requireNonNull(tipoEmozione);
        Objects.requireNonNull(note);
        if (intensitaEmozione == 0) return false;
        boolean autenticato = false;
        autenticato = Registration.login(userid, password);
        if (!autenticato) {
            return false;
        }
        LinkedList<Emotion> emotionList = readEmozioni();

        if (emotionList != null) {
            if (!title.equals("")) {
                Emotion emozione = new Emotion(title, tipoEmozione, intensitaEmozione, note);
                emotionList.addLast(emozione);
                writeEmozioni(emotionList);
                return true;

            } else if (!author.equals("") && year != 0) {
                EmotionalSongs canzoneTrovata = EmotionalSongs.cercaBranoMusicale(author, year);
                Emotion emozione = new Emotion(canzoneTrovata.title, tipoEmozione, intensitaEmozione, note);
                if (!canzoneTrovata.title.equals("")) {
                    emotionList.addLast(emozione);
                    writeEmozioni(emotionList);
                    return true;
                } else {
                    System.out.println("Canzone non trovata");
                }
            }
        }
        return false;

    }

    public static LinkedList<Emotion> cercaEmozioni(String title, String author, int year) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(author);
        LinkedList<Emotion> emozioneTrovata = new LinkedList<>();
        LinkedList<Emotion> emotionsList = new LinkedList<>();
        emotionsList = readEmozioni();
        if (emotionsList.size() != 0) {
            EmotionalSongs canzoneTrovata = new EmotionalSongs("", "", 0, "");
            if (!title.equals("")) {
                canzoneTrovata = EmotionalSongs.cercaBranoMusicale(title);
            } else if (!author.equals("") && year != 0) {
                canzoneTrovata = EmotionalSongs.cercaBranoMusicale(author, year);
            }
            if (canzoneTrovata == null) {
                return emozioneTrovata;
            }
            if (!canzoneTrovata.title.equals("")) {
                for (Emotion emozione : emotionsList) {
                    if (emozione.title.equals(canzoneTrovata.title)) {
                        emozioneTrovata.add(emozione);
                    }
                }
            }

        }

        return emozioneTrovata;
    }

    public static void riassuntoEmozione(String title) {
        Objects.requireNonNull(title);
        if (title.equals(" ")) {
            return;
        }
        boolean vuoto = true;
        LinkedList<Emotion> emotionLinkedList = new LinkedList<>();
        emotionLinkedList = cercaEmozioni(title, "", 0);
        int[] arrayEmozioni = new int[8];
        int[] qtyEmozioni = new int[8];
        if (emotionLinkedList.size() != 0) {
            for (Emotion emozione : emotionLinkedList) {
                switch (emozione.tipoEmozione.toUpperCase()) {
                    case "AMAZEMENT": {
                        arrayEmozioni[0] += emozione.intensitaEmozione;
                        qtyEmozioni[0]++;
                        break;
                    }
                    case "SOLEMNITY": {
                        arrayEmozioni[1] += emozione.intensitaEmozione;
                        qtyEmozioni[1]++;
                        break;
                    }
                    case "TENDERNESS": {
                        arrayEmozioni[2] += emozione.intensitaEmozione;
                        qtyEmozioni[2]++;
                        break;
                    }
                    case "CALMNESS": {
                        arrayEmozioni[3] += emozione.intensitaEmozione;
                        qtyEmozioni[3]++;
                        break;
                    }
                    case "NOSTALGIA": {
                        arrayEmozioni[4] += emozione.intensitaEmozione;
                        qtyEmozioni[4]++;
                        break;
                    }
                    case "POWER": {
                        arrayEmozioni[5] += emozione.intensitaEmozione;
                        qtyEmozioni[5]++;
                        break;
                    }
                    case "JOY": {
                        arrayEmozioni[6] += emozione.intensitaEmozione;
                        qtyEmozioni[6]++;
                        break;
                    }
                    case "TENSION": {
                        arrayEmozioni[7] += emozione.intensitaEmozione;
                        qtyEmozioni[7]++;
                        break;
                    }
                    case "SADNESS": {
                        arrayEmozioni[8] += emozione.intensitaEmozione;
                        qtyEmozioni[8]++;
                        break;
                    }

                }

            }
            for (int i = 0; i < arrayEmozioni.length; i++) {
                if (arrayEmozioni[i] != 0) {
                    arrayEmozioni[i] = arrayEmozioni[i] / qtyEmozioni[i];
                }
            }
            for (int i = 0; i < arrayEmozioni.length; i++) {
                if (arrayEmozioni[i] != 0) {
                    vuoto = false;
                }
            }
            System.out.println("Title: " + emotionLinkedList.get(0).title);
            if (vuoto) {
                System.out.println("Il brano selezionato non ha nessun'emozione inserita");
            } else {
                System.out.println("Media Emozioni: ");
                System.out.println("Amazement: " + arrayEmozioni[0] + " Secondo " + qtyEmozioni[0] + " Utenti");
                System.out.println("Solemnity: " + arrayEmozioni[1] + " Secondo " + qtyEmozioni[1] + " Utenti");
                System.out.println("Tenderness: " + arrayEmozioni[2] + " Secondo " + qtyEmozioni[2] + " Utenti");
                System.out.println("Nostalgia: " + arrayEmozioni[3] + " Secondo " + qtyEmozioni[3] + " Utenti");
                System.out.println("Calmness: " + arrayEmozioni[4] + " Secondo " + qtyEmozioni[4] + " Utenti");
                System.out.println("Power: " + arrayEmozioni[5] + " Secondo " + qtyEmozioni[5] + " Utenti");
                System.out.println("Joy: " + arrayEmozioni[6] + " Secondo " + qtyEmozioni[6] + " Utenti");
                System.out.println("Tension: " + arrayEmozioni[7] + " Secondo " + qtyEmozioni[7] + " Utenti");
                System.out.println("Sadness: " + arrayEmozioni[8] + " Secondo " + qtyEmozioni[8] + " Utenti");
                System.out.println("Note:");
                for (Emotion emozione : emotionLinkedList) {
                    if (!emozione.note.equals("")) {
                        System.out.println(emozione.note);
                    }
                }
            }
        } else {
            System.out.println("Brano non trovato");
        }


    }

    enum Emozioni {
        AMAZEMENT,
        SOLEMNITY,
        TENDERNESS,
        NOSTALGIA,
        CALMNESS,
        POWER,
        JOY,
        TENSION,
        SADNESS
    }


}
