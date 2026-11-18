package com.sabotefm.samdan;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.TableModel;
import java.sql.*;

public class SamdanMechanic {

    private static String url;
    private static String userName;
    private static String password;
    private static Connection con;

    // Getters

    // UserName
    public static String getUserName() {
        return userName;
    }

    // Authorization
    public static String getAuthorization() {
        return "Admin";
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
                    SELECT kitapAdı
                    FROM KİTAP
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kitapAdı");
                rs.close();
                return answer;
            }

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    public static String getWriterNumber(WindowBasedTextGUI gui, String selectedIsbn) {
        // TODO
        ResultSet rs = null;

        try {
            establishConnection();

            Statement stmt = con.createStatement();

            String query = """
                    SELECT yazarNumarası
                    FROM YAZARI_OLMA
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("yazarNumarası");
                rs.close();
                return answer;
            }

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
                    SELECT yayıneviNumarası
                    FROM YAYINEVİ_OLMA
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("yayıneviNumarası");
                rs.close();
                return answer;
            }

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
                    SELECT kütüphaneNumarası
                    FROM KÜTÜPHANEDE_BULUNMA
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kütüphaneNumarası");
                rs.close();
                return answer;
            }

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
                    SELECT kütüphaneKonumu
                    FROM KÜTÜPHANEDE_BULUNMA
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kütüphaneKonumu");
                rs.close();
                return answer;
            }

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
                    SELECT kitapBaskıSayısı
                    FROM KİTAP
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kitapBaskıSayısı");
                rs.close();
                return answer;
            }

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
                    SELECT kitapÇıkışYılı
                    FROM KİTAP
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kitapÇıkışYılı");
                rs.close();
                return answer;
            }

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
                    SELECT yazarAdı
                    FROM YAZAR Y, YAZARI_OLMA K
                    WHERE K.kitapISBN=""" + selectedIsbn + " AND K.yazarNumarası=Y.yazarNumarası;";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("yazarAdı");
                rs.close();
                return answer;
            }

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
                    SELECT yayıneviAdı
                    FROM YAYINEVİ_OLMA K, YAYINEVİ Y
                    WHERE kitapISBN=""" + selectedIsbn + " AND K.yayıneviNumarası=Y.yayıneviNumarası;";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("yayıneviAdı");
                rs.close();
                return answer;
            }

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
                    SELECT kütüphaneAdı
                    FROM KÜTÜPHANEDE_BULUNMA K, KÜTÜPHANE Y
                    WHERE kitapISBN=""" + selectedIsbn + " AND K.yayıneviNumarası=Y.yayıneviNumarası;";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kütüphaneAdı");
                rs.close();
                return answer;
            }

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
                    SELECT kitapTürü
                    FROM KİTAP K
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kitapTürü");
                rs.close();
                return answer;
            }

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
                    SELECT kitapSayfaSayısı
                    FROM KİTAP K
                    WHERE kitapISBN=""" + selectedIsbn + ";";

            rs = stmt.executeQuery(query);
            stmt.close();

            if (null == rs) {
                return null;
            } else {
                String answer = rs.getString("kitapSayfaSayısı");
                rs.close();
                return answer;
            }

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
        }

        return null;
    }

    // Login
    public static void loginToSamdan(WindowBasedTextGUI gui, String userName, String passWord) {
        SamdanDashboard.showDashboard();
    }

    // Send Ratings
    public static void saveRatings(WindowBasedTextGUI gui, String selectedIsbn, int points, String evalution) {
        // Save ratings
    }

    // Load book table data
    public static void loadBookArchive(WindowBasedTextGUI gui, TableModel<String> tableModel) {
        tableModel.clear();
        // TODO: Replace with MySQL database queries for KİTAP TABLOSU, YAZAR TABLOSU,
        // etc.
        tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî Eğitim", "Tarih", "550");
        tableModel.addRow("978-973...", "Kürk Mantadolu Madonna", "Sabahattin Ali", "Yapı Kredi", "Roman", "160");
        tableModel.addRow("978-970...", "Saatleri Ayarlama Enstitüsü", "Ahmet Hamdi Tanpınar", "Dergah", "Roman",
                "396");
    }

    // Load ratings table data for users
    public static void loadRatingsTableForUsers(WindowBasedTextGUI gui, TableModel<String> tableModel) {
        tableModel.clear();
        // TODO: Replace with MySQL database queries for DEĞERLENDİRME TABLOSU, OKUR
        // TABLOSU,
        // etc.
        tableModel.addRow("Nutuk", "8/10", "Tarihiydi");
        tableModel.addRow("Kestane Gürgen Palamut", "7/10", "Altı Yaprak Üstü Bulut");
        tableModel.addRow("Kürk Mantadolu Madonna", "6/10", "Sabahattin Aliliydi");
    }

    // Load ratings table data for a single book
    public static void loadRatingsTableForABook(WindowBasedTextGUI gui, TableModel<String> tableModel,
            String selectedIsbn) {
        tableModel.clear();
        // TODO: Replace with MySQL database queries for DEĞERLENDİRME TABLOSU, OKUR
        // TABLOSU,
        // etc.
        tableModel.addRow("Emre Karaduman", "8/10", "no way");
        tableModel.addRow("Melih Efe Tursun", "7/10", "Çok iyi");
        tableModel.addRow("Osman Kuru", "6/10", "Osman");
    }

    // Handle book search functionality
    public static void searchBooks(WindowBasedTextGUI gui, TableModel<String> tableModel, String arananKelime,
            int seciliFiltre, int seciliKitapTuru) {
        if (arananKelime == null || arananKelime.trim().isEmpty()) {
            String title = "Uyarı";
            String message = "Lütfen aranacak bir kelime veya ISBN girin.";
            popUpMakerOK(gui, title, message);
            return;
        } else {
            tableModel.clear();
            // TODO: Query MySQL database matching name, author, or ISBN
            // arananKelime -> Aranan Kelime String

            // Seçili Filtre:
            // 0-Kitap Adı
            // 1-Yazar
            // 2-Yayınevi
            // 3-Kütüphane

            // Secili Kitap Turu:
            // 0-Hepsi
            // 1-Roman
            // 2-Hikaye
            // 3-Şiir
            // 4-Destan

            tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî Eğitim", "Tarih", "550");
            tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî Eğitim", "Tarih", "550");
            tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî Eğitim", "Tarih", "550");
            tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî Eğitim", "Tarih", "550");
        }
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

    public static String getWhoBorrowed() {
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
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }

        con = connection;
        return;
    }
}
