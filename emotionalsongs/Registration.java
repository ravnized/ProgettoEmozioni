package emotionalsongs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Registration {
    private static final String filepathRegistrati = "data/UtentiRegistrati.dati";
    static char CSV_SEPARATOR = ';';
    String nomeCognome = "", codiceFiscale = "", indirizzo = "", email = "", userId = "", password = "";


    public Registration(String nomeCognome, String codiceFiscale, String indirizzo, String email, String userId, String password) {
        this.nomeCognome = nomeCognome;
        this.codiceFiscale = codiceFiscale;
        this.indirizzo = indirizzo;
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    private static void merge(Registration[] arr, int l, int m, int r) {

        int n1 = m - l + 1;
        int n2 = r - m;


        Registration[] L = new Registration[n1];
        Registration[] R = new Registration[n2];


        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;


        int k = l;
        while (i < n1 && j < n2 && L[i] != null && R[j] != null) {
            if (L[i].userId.charAt(0) <= R[j].userId.charAt(0)) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }


        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }


        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    private static void sort(Registration[] arr, int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;


            sort(arr, l, m);
            sort(arr, m + 1, r);


            merge(arr, l, m, r);

        }
    }


    public static LinkedList<Registration> readRegistrati(String filepath) {
        LinkedList<Registration> list = new LinkedList<>();
        File file = new File(filepath);
        if (!file.exists()) return list;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String[] campiTotali;
            while (true) {
                int i = 0;
                String readerLiner = reader.readLine();
                if (readerLiner == null || readerLiner.equals("")) break;
                campiTotali = readerLiner.split(";");
                Registration utenteRegistrato = new Registration(campiTotali[i], campiTotali[i + 1], campiTotali[i + 2], campiTotali[i + 3], campiTotali[i + 4], campiTotali[i + 5]);
                list.addLast(utenteRegistrato);
            }
            reader.close();

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeRegistrati(LinkedList<Registration> utentiRegistrati) {
        final int arraySize = utentiRegistrati.size();
        Registration[] arrayRegistrati = new Registration[arraySize];
        int i = 0;
        for (Registration registrato : utentiRegistrati) {
            arrayRegistrati[i] = registrato;
            i++;
        }


        sort(arrayRegistrati, 0, arraySize - 1);


        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepathRegistrati), StandardCharsets.UTF_8));

            for (Registration registrato : arrayRegistrati) {
                String oneLine = registrato.nomeCognome + CSV_SEPARATOR + registrato.codiceFiscale + CSV_SEPARATOR + registrato.indirizzo + CSV_SEPARATOR + registrato.email + CSV_SEPARATOR + registrato.userId + CSV_SEPARATOR + registrato.password + CSV_SEPARATOR;
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


    private static boolean registratiVisualizer() {
        LinkedList<Registration> registrati = readRegistrati(filepathRegistrati);
        if (registrati != null) {
            for (Registration registration : registrati) {
                System.out.println("\n");
                System.out.println("Nome e cognome: " + registration.nomeCognome);
                System.out.println("Codice fiscale: " + registration.codiceFiscale);
                System.out.println("Indirizzo: " + registration.indirizzo);
                System.out.println("Email: " + registration.email);
                System.out.println("UserId: " + registration.userId);
                System.out.println("Password: " + registration.password);
                System.out.println("\n");
            }
            return true;
        }
        return false;
    }

    private static boolean registraUtente(String nomeCognome, String codiceFiscale, String indirizzo, String email, String userId, String password) {
        Registration utenteRegistrato = new Registration(nomeCognome, codiceFiscale, indirizzo, email, userId, password);
        LinkedList<Registration> utentiRegistratiList = Registration.readRegistrati(filepathRegistrati);
        utentiRegistratiList.addLast(utenteRegistrato);
        writeRegistrati(utentiRegistratiList);
        return true;
    }


    public static void main(String[] args) {
        registraUtente("pippo", "TRNGPL99C03D912N", "via don minzoni 9", "ravndeveloper@gmail.com", "ravnized", "030399");
        registratiVisualizer();

    }


}
