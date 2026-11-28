package com.sabotefm.samdan;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.TableModel;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SamdanMechanic {

    // for database connection
    private static String databaseURL;
    private static String databaseUser;
    private static String databasePassword;
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
        // Function to add new user to the KULLANICI table
        // Every string directly maps to the attribute that has the same name

        boolean flag = false;
        if ("0".equals(kullaniciAdi)) {
            flag = true;
        } else if ("0".equals(gercekAdi)) {
            flag = true;
        } else if ("0".equals(sifre)) {
            flag = true;
        } else if ("0".equals(yetkiSeviyesi)) {
            flag = true;
        } else if ("0".equals(okurYasi)) {
            flag = true;
        } else if ("0".equals(TCNO)) {
            flag = true;
        }

        if (flag) {
            popUpMakerOK(gui, "Hata", "Tüm bilgiler dolu olmalı.");
            return;
        }

        try {
            establishConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO KULLANICI (kullaniciAdi, gercekAdi, sifre, yetkiSeviyesi, okurYasi, TCNO) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, kullaniciAdi);
            ps.setString(2, gercekAdi);
            ps.setString(3, sifre);
            ps.setString(4, yetkiSeviyesi);
            ps.setString(5, okurYasi);
            ps.setString(6, TCNO);

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
            return;
        }

        popUpMakerOK(gui, "İşlem Başarılı!", "Kullanıcı: '" + kullaniciAdi + "' sisteme eklendi.");
    }

    // Handle marking a book as acquired by the user (Kitap Edinme)
    public static void markBookAsBorrowed(WindowBasedTextGUI gui, String kitapISBN, String kullaniciAdi,
            String kutuphaneNumarasi) {

        boolean flag = false;
        if ("0".equals(kullaniciAdi)) {
            flag = true;
        } else if ("0".equals(kitapISBN)) {
            flag = true;
        } else if ("0".equals(kutuphaneNumarasi)) {
            flag = true;
        }

        if (flag) {
            popUpMakerOK(gui, "Hata", "Tüm bilgiler dolu olmalı.");
            return;
        }

        try {

            establishConnection();
            Statement stat = con.createStatement();

            String query = """
                    SELECT KB.kopyaNumarasi
                    FROM KUTUPHANE_BULUNMA KB
                    LEFT JOIN ARIYET A
                        ON KB.kitapISBN = A.kitapISBN
                        AND KB.kopyaNumarasi = A.kopyaNumarasi
                        AND KB.kutuphaneNumarasi = A.kutuphaneNumarasi
                    WHERE KB.kitapISBN='""" + kitapISBN + """
                    ' AND KB.kutuphaneNumarasi='""" + kutuphaneNumarasi + """
                    ' AND A.kopyaNumarasi IS NULL;""";
            ResultSet rs = stat.executeQuery(query);

            String kopyaNumarasi = null;

            if (rs.next()) {
                kopyaNumarasi = rs.getString(1);
            } else {
                popUpMakerOK(gui, "Error", "Bu kitabın ödünç alınabilir bir kopyası bulunamadı");
                return;
            }

            String insertQuery = """
                    INSERT INTO ARIYET (kitapISBN, kopyaNumarasi, kutuphaneNumarasi, kullaniciAdi)
                    VALUES (?, ?, ?, ?);""";
            PreparedStatement ps = con.prepareStatement(insertQuery);

            ps.setString(1, kitapISBN);
            ps.setString(2, kopyaNumarasi);
            ps.setString(3, kutuphaneNumarasi);
            ps.setString(4, kullaniciAdi);

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
            return;
        }

        // Kitabı kişiye ekle
        String title = "Başarılı";
        String message = "Seçilen kitap (ISBN: " + kitapISBN + ") edindiğiniz olarak işaretlendi.";
        popUpMakerOK(gui, title, message);
    }

    // Check all those who borrow the book right now (Ariyet)
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

    public static void returnBook(WindowBasedTextGUI gui, String kitapISBN, String kullaniciAdi,
            String kutuphaneNumarasi) {
        boolean flag = false;
        if ("0".equals(kullaniciAdi)) {
            flag = true;
        } else if ("0".equals(kitapISBN)) {
            flag = true;
        } else if ("0".equals(kutuphaneNumarasi)) {
            flag = true;
        }

        if (flag) {
            popUpMakerOK(gui, "Hata", "Tüm bilgiler dolu olmalı.");
            return;
        }

        try {

            establishConnection();

            String deleteQuery = """
                    DELETE FROM ARIYET
                    WHERE kitapISBN = ? AND kullaniciAdi = ? AND kutuphaneNumarasi = ?
                    LIMIT 1;""";

            PreparedStatement ps = con.prepareStatement(deleteQuery);

            ps.setString(1, kitapISBN);
            ps.setString(2, kullaniciAdi);
            ps.setString(3, kutuphaneNumarasi);

            ps.execute();

            ps.close();

        } catch (SQLException e) {
            String SQL_STATE = e.getSQLState();
            popUpMakerOK(gui, "Error: " + SQL_STATE, e.getMessage());
            return;
        }

        String title = "Başarılı";
        String message = "Seçilen kitap (ISBN: " + kitapISBN + ") iade edilmiştir.";
        popUpMakerOK(gui, title, message);

    }

    public static String getWhoBorrowed(WindowBasedTextGUI gui, String selectedIsbn) {
        return null;
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

    // https://www.geeksforgeeks.org/java/jdbc-tutorial/
    private static void establishConnection() {

        if (null != con) {
            return;
        }

        getDatabaseUser();

        Connection connection = null;

        try {
            // Establish Connection
            connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }

        con = connection;
        return;
    }

    private static void getDatabaseUser() {
        if (null == databaseURL || null == databaseUser || null == databasePassword) {
            databaseURL = ConfigLoader.get("database-url");
            databaseUser = ConfigLoader.get("database-user");
            databasePassword = ConfigLoader.get("password");
        }
    }

    class ConfigLoader {
        private static final Properties properties = new Properties();

        static {
            try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find config.properties in src/main/resources");
                }
                properties.load(input);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load config.properties", ex);
            }
        }

        public static String get(String key) {
            // 1. Check real OS environment variables first (e.g., DATABASE_URL)
            String envKey = key.toUpperCase().replace('-', '_');
            String envValue = System.getenv(envKey);
            if (envValue != null && !envValue.isBlank()) {
                return envValue;
            }

            // 2. Fall back to config.properties (e.g., database-url)
            return properties.getProperty(key);
        }
    }
}
