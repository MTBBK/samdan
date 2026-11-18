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
    public static String getBookName(String selectedIsbn) {
        // return "Osman Kuru'nun Hayatı";

        ResultSet rs = null;

        try {

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

        }

        return null;
    }

    // Writer Name
    public static String getWriterName(String selectedIsbn) {
        // return "Melih Efe Tursun";

        ResultSet rs = null;

        while (null == con) {
            establishConnection();
        }

        try {

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
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }

        return null;
    }

    // Publisher Name
    public static String getPublisherName(String selectedIsbn) {
        return "Osman Kuru Yayınları";
    }

    // Library Name
    public static String getLibraryName(String selectedIsbn) {
        return "İskenderiye Kütüphanesi";
    }

    // Book Type
    public static String getTypeName(String selectedIsbn) {
        return "Roman";
    }

    // Page Count
    public static String getPageCount(String selectedIsbn) {
        return "18";
    }

    // Login
    public static void loginToSamdan(String userName, String passWord) {
        SamdanDashboard.showDashboard();
    }

    // Send Ratings
    public static void saveRatings(String selectedIsbn, int points, String evalution) {
        // Save ratings
    }

    // Load book table data
    public static void loadBookArchive(TableModel<String> tableModel) {
        tableModel.clear();
        // TODO: Replace with MySQL database queries for KİTAP TABLOSU, YAZAR TABLOSU,
        // etc.
        tableModel.addRow("978-975...", "Nutuk", "Mustafa Kemal Atatürk", "Millî Eğitim", "Tarih", "550");
        tableModel.addRow("978-973...", "Kürk Mantadolu Madonna", "Sabahattin Ali", "Yapı Kredi", "Roman", "160");
        tableModel.addRow("978-970...", "Saatleri Ayarlama Enstitüsü", "Ahmet Hamdi Tanpınar", "Dergah", "Roman",
                "396");
    }

    // Load ratings table data for users
    public static void loadRatingsTableForUsers(TableModel<String> tableModel) {
        tableModel.clear();
        // TODO: Replace with MySQL database queries for DEĞERLENDİRME TABLOSU, OKUR
        // TABLOSU,
        // etc.
        tableModel.addRow("Nutuk", "8/10", "Tarihiydi");
        tableModel.addRow("Kestane Gürgen Palamut", "7/10", "Altı Yaprak Üstü Bulut");
        tableModel.addRow("Kürk Mantadolu Madonna", "6/10", "Sabahattin Aliliydi");
    }

    // Load ratings table data for a single book
    public static void loadRatingsTableForABook(TableModel<String> tableModel, String selectedIsbn) {
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
            new MessageDialogBuilder()
                    .setTitle("Uyarı")
                    .setText("Lütfen aranacak bir kelime veya ISBN girin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
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
    public static void markBookAsOwned(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            new MessageDialogBuilder()
                    .setTitle("Seçim Hatası")
                    .setText("Lütfen tablodan işlem yapmak için bir kitap seçin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
            return;
        }
        // TODO: Insert/Update record in user ownership database relation

        // Kitabı kişiye ekle
        new MessageDialogBuilder()
                .setTitle("Başarılı")
                .setText("Seçilen kitap (ISBN: " + selectedIsbn + ") edindiğiniz olarak işaretlendi.")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
    }

    // Check who owns the book right now (Ariyet)
    public static void checkWhoOwns(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            new MessageDialogBuilder()
                    .setTitle("Seçim Hatası")
                    .setText("Lütfen tablodan bir kitap seçin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
            return;
        }
        // TODO: Query ARİYET TABLOSU from MySQL

        // Kitabın kime ait olduğu
        String ownerOfTheBook = "Ahmet";

        // Eğer Sahibi Varsa
        String messageToShow = "Seçilen " + selectedIsbn + " ISBN numaralı kitap, " + ownerOfTheBook
                + " adlı şahsın elinde (Onu Kurtar)";

        // Eğer Sahibi Yoksa
        messageToShow = "Seçilen " + selectedIsbn
                + " ISBN numaralı kitap, kimsede değil merak etme git ve kütüphaneden al";

        // Mesaj Göster
        new MessageDialogBuilder()
                .setTitle("Ariyet Durumu")
                .setText(messageToShow)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
    }

    // View or write reviews/comments (Değerlendirmeler)
    public static void openReviews(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            new MessageDialogBuilder()
                    .setTitle("Seçim Hatası")
                    .setText("Lütfen tablodan bir kitap seçin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
            return;
        }
        // TODO: Query reviews/comments table for this book

        // Değerlendirme Yoksa Bu Koşul
        new MessageDialogBuilder()
                .setTitle("Değerlendirmeler")
                .setText("Bu kitap için henüz yorum girilmemiş.\n(ISBN: " + selectedIsbn + ")")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);

        // Değerlendirme varsa bu koşul
        SamdanDegerlendirmeler.showDegerlendirmeler(selectedIsbn);
    }

    // Go to Book Information Page
    public static void openBookInformation(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            new MessageDialogBuilder()
                    .setTitle("Seçim Hatası")
                    .setText("Lütfen tablodan bir kitap seçin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
            return;
        }
        SamdanKitap.showKitap(selectedIsbn);
    }

    // TODO: Establish connection to the database

    // https://www.geeksforgeeks.org/java/jdbc-tutorial/
    private static void establishConnection() {

        if (null == con) {
            return;
        }

        Connection connection = null;

        try {
            // 2. Establish Connection
            connection = DriverManager.getConnection(url, userName, password);

            // 3. Create Statement
            Statement stmt = con.createStatement();

            // 4. Execute Query
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            // 5. Process Results
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getInt("age"));
            }

            // 6. Close resources
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }

        con = connection;
        return;
    }
}
