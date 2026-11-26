package com.sabotefm.samdan;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.TableModel;
import java.sql.*;

public class SamdanMechanic {

    // for database connection
    private static String url = "jdbc:mysql://localhost:3306/samdan_db?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private static String databaseUser = "kutuphane-admin";
    private static String databasePassword = "Passw0rd..";
    private static Connection con;

    // for user information
    private static String userName;
    private static String authorisation;

    // Getters

    // UserName
    public static String getUserName() {
        return userName;
    }

    // Authorization
    public static String getAuthorization() {
        return authorisation;
    }

    // Book Informations
    // Book Name
    public static String getBookName(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "Osman Kuru'nun Hayatı";

        ResultSet rs = null;

        try {

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kitapAdi
                    FROM KITAP
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kitapAdi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getWriterNumber(WindowBasedTextGUI gui, String selectedIsbn) {
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT yazarNumarasi
                    FROM YAZARI_OLMA
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("yazarNumarasi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getPublisherNumber(WindowBasedTextGUI gui, String selectedIsbn) {
        // TODO
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT yayineviNumarasi
                    FROM YAYINEVI_OLMA
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("yayineviNumarasi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getLibraryNumber(WindowBasedTextGUI gui, String selectedIsbn) {
        // TODO
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kutuphaneNumarasi
                    FROM KUTUPHANE_BULUNMA
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kutuphaneNumarasi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getLibraryLocation(WindowBasedTextGUI gui, String selectedIsbn) {
        // TODO
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kutuphaneKonumu
                    FROM KUTUPHANE_BULUNMA Y, KUTUPHANE K
                    WHERE kitapISBN='""" + selectedIsbn + "' AND K.kutuphaneNumarasi=Y.kutuphaneKonumu;";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kütüphaneKonumu");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getNumberOfPrint(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "Osman Kuru'nun Hayatı";

        ResultSet rs = null;

        try {

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kitapBaskiSayisi
                    FROM KITAP
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kitapBaskiSayisi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getReleaseDate(WindowBasedTextGUI gui, String selectedIsbn) {
        ResultSet rs = null;

        try {

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kitapCikisYili
                    FROM KITAP
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kitapCikisYili");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    // Writer Name
    public static String getWriterName(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "Melih Efe Tursun";

        ResultSet rs = null;

        try {

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT yazarAdi
                    FROM YAZAR Y, YAZARI_OLMA K
                    WHERE K.kitapISBN='""" + selectedIsbn + "' AND K.yazarNumarasi=Y.yazarNumarasi;";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("yazarAdi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    // Publisher Name
    public static String getPublisherName(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "Osman Kuru Yayınları";
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT yayineviAdi
                    FROM YAYINEVI_OLMA K, YAYINEVI Y
                    WHERE kitapISBN='""" + selectedIsbn + "' AND K.yayineviNumarasi=Y.yayineviNumarasi;";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("yayineviAdi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    // Library Name
    public static String getLibraryName(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "İskenderiye Kütüphanesi";
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kütüphaneAdi
                    FROM KUTUPHANE_BULUNMA K, KUTUPHANE Y
                    WHERE kitapISBN='""" + selectedIsbn + "' AND K.kutuphaneNumarasi=Y.kutuphaneNumarasi;";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kütüphaneAdi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    // Book Type
    public static String getTypeName(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "Roman";
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kitapTuru
                    FROM KITAP K
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kitapTuru");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;

    }

    // Page Count
    public static String getPageCount(WindowBasedTextGUI gui, String selectedIsbn) {
        // return "18";
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kitapSayfaSayisi
                    FROM KITAP K
                    WHERE kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            String answer = rs.getString("kitapSayfaSayisi");
            rs.close();
            return answer;

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    // Login
    public static void loginToSamdan(WindowBasedTextGUI gui, String username, String passWord) {

        userName = null;
        authorisation = null;

        String passAnswer = null;
        String authAnswer = null;

        try {
            ResultSet rs = null;
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT sifre, yetkiSeviyesi
                    FROM KULLANICI K
                    WHERE kullaniciAdi='""" + username + "';";

            rs = stmt.executeQuery(query);

            if (!rs.next()) {
                // No matching user found
                popUpMakerOK(gui, "Error: Yanlis kullanici adi veya sifre",
                        "Yanlis kullanici adi veya sifre" + "\nKullanıcı: " + username + "Sifre: " + passWord);
            } else {
                passAnswer = rs.getString("sifre");
                authAnswer = rs.getString("yetkiSeviyesi");

                if (null == passAnswer) {
                    // throw error "wrong password or username"
                    popUpMakerOK(gui, "Error: Yanlis kullanici adi veya sifre", "Yanlis kullanici adi veya sifre");
                } else if (!passWord.equals(passAnswer)) {
                    // throw error "wrong password or username"
                    popUpMakerOK(gui, "Error: Yanlis kullanici adi veya sifre", "Yanlis kullanici adi veya sifre");
                } else if (null == authAnswer) {
                    // throw error "user's auth level couldn't be found"
                    popUpMakerOK(gui, "Error: Yetki seviyesi bulunamadi", "Yetki seviyesi bulunamadi");
                } else {
                    userName = username;
                    authorisation = authAnswer;
                    SamdanDashboard.showDashboard();
                }
            }

            rs.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

    }

    // Send Ratings
    public static void saveRatings(WindowBasedTextGUI gui, String selectedIsbn, int points, String evalution) {
        try {
            ResultSet rs = null;

            establishConnection();

            Statement stmt = con.createStatement();

            String searchQuery = """
                    SELECT kitapSayfaSayisi
                    FROM DEGERLENDIRME K
                    WHERE kitapISBN='""" + selectedIsbn + "' AND kullaniciAdi='" + userName + "';";
            ;
            rs = stmt.executeQuery(searchQuery);

            String userAnswer = rs.getString("kullaniciAdi");

            if (null == userAnswer) {
                String insertQuery = """
                        INSERT INTO DEGERLENDIRME
                        VALUES ('""" + selectedIsbn + "', '" + userName + "', '" + evalution + "', '" + points + "');";
                stmt.executeQuery(insertQuery);
            } else {
                String updateQuery = null;
                if (null == evalution) {
                    updateQuery = """
                            UPDATE DEGERLENDIRME
                            SET puan = '""" + points + "'" + """
                            WHERE kitapISBN='""" + selectedIsbn + "' AND kullaniciAdi='" + userName + "';";
                } else {
                    updateQuery = """
                            UPDATE DEGERLENDIRME
                            SET puan = '""" + points + "', degerlendirme = '" + evalution + "'" + """
                            WHERE kitapISBN='""" + selectedIsbn + "' AND kullaniciAdi='" + userName + "';";
                }
                stmt.executeQuery(updateQuery);
            }
            rs.close();
        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }
    }

    // Load book table data
    public static void loadBookArchive(WindowBasedTextGUI gui, TableModel<String> tableModel) {
        tableModel.clear();

        try {
            ResultSet rs = null;

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT K.kitapISBN, kitapAdi, yazarAdi, yayineviAdi, kitapTuru, kitapSayfaSayisi
                    FROM KITAP K
                    INNER JOIN YAZARI_OLMA Y ON K.kitapISBN=Y.kitapISBN
                    INNER JOIN YAZAR B ON B.yazarNumarasi=Y.yazarNumarasi
                    INNER JOIN YAYINEVI_OLMA X ON K.kitapISBN=X.kitapISBN
                    INNER JOIN YAYINEVI Z ON X.yayineviNumarasi=Z.yayineviNumarasi;""";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String[] entry = new String[6];
                for (int i = 0; i < 6; i++) {
                    entry[i] = rs.getString(i + 1);
                }
                tableModel.addRow(entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]);
            }

            rs.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        /*
         * tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk",
         * "Millî Eğitim", "Tarih", "550");
         * tableModel.addRow("978-973...", "Kürk Mantadolu Madonna", "Sabahattin Ali",
         * "Yapı Kredi", "Roman", "160");
         * tableModel.addRow("978-970...", "Saatleri Ayarlama Enstitüsü",
         * "Ahmet HamdiTanpınar", "Dergah", "Roman", "396");
         */
    }

    // Load ratings table data for users
    public static void loadRatingsTableForUsers(WindowBasedTextGUI gui, TableModel<String> tableModel) {
        tableModel.clear();

        try {
            ResultSet rs = null;

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT kitapAdi, puan, degerlendirme
                    FROM KITAP K
                    INNER JOIN DEGERLENDIRME Y ON K.kitapISBN=Y.kitapISBN
                    WHERE Y.kullaniciAdi='""" + userName + "';";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String[] entry = new String[3];
                for (int i = 0; i < 3; i++) {
                    entry[i] = rs.getString(i + 1);
                }
                tableModel.addRow(entry[0], entry[1] + "/10", entry[2]);
            }

            rs.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        // tableModel.addRow("Nutuk", "8/10", "Tarihiydi");
        // tableModel.addRow("Kestane Gürgen Palamut", "7/10", "Altı Yaprak Üstü
        // Bulut");
        // tableModel.addRow("Kürk Mantadolu Madonna", "6/10", "Sabahattin Aliliydi");
    }

    // Load ratings table data for a single book
    public static void loadRatingsTableForABook(WindowBasedTextGUI gui, TableModel<String> tableModel,
            String selectedIsbn) {
        tableModel.clear();

        try {
            ResultSet rs = null;

            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT gercekAdi, puan, degerlendirme
                    FROM KITAP K
                    INNER JOIN DEGERLENDIRME Y ON K.kitapISBN=Y.kitapISBN
                    INNER JOIN KULLANICI A ON Y.kullaniciAdi=A.kullaniciAdi
                    WHERE K.kitapISBN='""" + selectedIsbn + "';";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String[] entry = new String[3];
                for (int i = 0; i < 3; i++) {
                    entry[i] = rs.getString(i + 1);
                }
                tableModel.addRow(entry[0], entry[1] + "/10", entry[2]);
            }

            rs.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        // tableModel.addRow("Emre Karaduman", "8/10", "no way");
        // tableModel.addRow("Melih Efe Tursun", "7/10", "Çok iyi");
        // tableModel.addRow("Osman Kuru", "6/10", "Osman");
    }

    // Handle book search functionality
    public static void searchBooks(WindowBasedTextGUI gui, TableModel<String> tableModel, String kitapAdi,
            String yazarAdi, String yayineviAdi, String kutuphaneAdi,
            String kitapTuru) {

        try {
            ResultSet rs = null;

            establishConnection();

            Statement stmt = con.createStatement();
            // working code above

            String query = "SELECT K.kitapISBN, K.kitapAdi, B.yazarAdi, Z.yayineviAdi, K.kitapTuru, K.kitapSayfaSayisi\n"
                    +
                    "FROM KITAP K\n" +
                    "INNER JOIN YAZARI_OLMA Y ON K.kitapISBN = Y.kitapISBN\n" +
                    "INNER JOIN YAZAR B ON B.yazarNumarasi = Y.yazarNumarasi\n" +
                    "INNER JOIN YAYINEVI_OLMA X ON K.kitapISBN = X.kitapISBN\n" +
                    "INNER JOIN YAYINEVI Z ON X.yayineviNumarasi = Z.yayineviNumarasi\n" +
                    "INNER JOIN KUTUPHANE_BULUNMA KB ON K.kitapISBN = KB.kitapISBN\n" +
                    "INNER JOIN KUTUPHANE KP ON KB.kutuphaneNumarasi = KP.kutuphaneNumarasi\n";

            String cond = "WHERE";

            if (!"0".equals(kitapAdi)) {
                cond += " K.kitapAdi LIKE '%" + kitapAdi + "%'";
            } else {
                cond += " K.kitapAdi LIKE '%'";
            }
            if (!"0".equals(yazarAdi)) {
                cond += " AND B.yazarAdi LIKE '%" + yazarAdi + "%'";
            }
            if (!"0".equals(yayineviAdi)) {
                cond += " AND Z.yayineviAdi LIKE '%" + yayineviAdi + "%'";
            }
            if (!"0".equals(kutuphaneAdi)) {
                cond += " AND KP.kutuphaneAdi LIKE '%" + kutuphaneAdi + "%'";
            }
            if (!"Hepsi".equals(kitapTuru)) {
                cond += " AND K.kitapTuru LIKE '%" + kitapTuru + "%'";
            }
            cond += "\nGROUP BY K.kitapISBN, K.kitapAdi, B.yazarAdi, Z.yayineviAdi, K.kitapTuru, K.kitapSayfaSayisi";

            cond += ";";

            query += cond;

            // working code below
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String[] entry = new String[6];
                for (int i = 0; i < 6; i++) {
                    entry[i] = rs.getString(i + 1);
                }
                tableModel.addRow(entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]);
            }

            rs.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        // kitapISBN, kitapAdi, yazarAdi, yayineviAdi, kitapTuru, kitapSayfaSayisi
        // tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî
        // Eğitim", "Tarih", "550");
        // tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî
        // Eğitim", "Tarih", "550");
        // tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî
        // Eğitim", "Tarih", "550");
    }

    public static void addNewBook(WindowBasedTextGUI gui, String kitapISBN, String kitapAdi, String kitapTuru,
            String kitapSayfaSayisi, String kitapCikisYili, String kitapBaskiSayisi) {
        // Function to add new book to database
    }

    public static void addNewUser(WindowBasedTextGUI gui, String kullaniciAdi, String gercekAdi, String sifre,
            String yetkiSeviyesi, String okurYasi, String TCNO) {
        // Function to add new user to database
    }

    // Handle marking a book as acquired by the user (Kitap Edinme)
    public static void markBookAsBorrowed(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            String title = "Seçim Hatası";
            String message = "Lütfen tablodan işlem yapmak için bir kitap seçin.";
            popUpMakerOK(gui, title, message);
            return;
        }
        // TODO: Insert/Update record in user ownership database relation

        // Kitabı kişiye ekle
        String title = "Başarılı";
        String message = "Seçilen kitap (ISBN: " + selectedIsbn + ") edindiğiniz olarak işaretlendi.";
        popUpMakerOK(gui, title, message);
    }

    // Check who owns the book right now (Ariyet)
    public static void checkWhoBorrowed(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            String title = "Seçim Hatası";
            String message = "Lütfen tablodan bir kitap seçin.";
            popUpMakerOK(gui, title, message);
            return;
        }
        // TODO: Query ARİYET TABLOSU from MySQL

        // Kitabın kime ait olduğu
        String ownerOfTheBook = "Ahmet";

        // Eğer Sahibi Varsa
        SamdanKullanici.showKullanici();

        // Eğer Sahibi Yoksa
        String messageToShow = "Seçilen " + selectedIsbn
                + " ISBN numaralı kitap, kimsede değil merak etme git ve kütüphaneden al";
        // Mesaj Göster
        String title = "Ariyet Durumu";
        String message = messageToShow;
        popUpMakerOK(gui, title, message);
    }

    public static String getWhoBorrowed(WindowBasedTextGUI gui, String selectedIsbn) {
        return null;
    }

    // View or write reviews/comments (Değerlendirmeler)
    public static void openReviews(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            String title = "Seçim Hatası";
            String message = "Lütfen tablodan bir kitap seçin.";
            popUpMakerOK(gui, title, message);
            return;
        }
        // TODO: Query reviews/comments table for this book

        // Değerlendirme Yoksa Bu Koşul
        String title = "Değerlendirmeler";
        String message = "Bu kitap için henüz yorum girilmemiş.\n(ISBN: " + selectedIsbn + ")";
        popUpMakerOK(gui, title, message);

        // Değerlendirme varsa bu koşul
        SamdanDegerlendirmeler.showDegerlendirmeler(selectedIsbn);
    }

    // Go to Book Information Page
    public static void openBookInformation(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            String title = "Seçim Hatası";
            String message = "Lütfen tablodan bir kitap seçin.";
            popUpMakerOK(gui, title, message);
            return;
        }
        SamdanKitap.showKitap(selectedIsbn);
    }

    public static void popUpMakerOK(WindowBasedTextGUI gui, String title, String message) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText(message)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
    }

    public static void popUpMakerRetry(WindowBasedTextGUI gui, String title, String message) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText(message)
                .addButton(MessageDialogButton.Retry)
                .build()
                .showDialog(gui);
    }

    // TODO: Establish connection to the database

    // https://www.geeksforgeeks.org/java/jdbc-tutorial/
    private static void establishConnection() {

        if (null != con) {
            return;
        }

        Connection connection = null;

        try {
            // Establish Connection
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }

        con = connection;
        return;
    }

    private static void getDatabaseUser() {
        // TODO: gets database user's username and password with some dark magic
    }
}
