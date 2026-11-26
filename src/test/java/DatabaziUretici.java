import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DatabaziUretici {

    private static final int BATCH_SIZE = 2500;

    public static class MasterKeys {
        public List<Integer> yazarIds;
        public List<Integer> yayineviIds;
        public List<Integer> kutuphaneIds;
        public List<String> kitapIsbns;
        public List<String> kullaniciAdlari;
    }

    public static void main(String[] args) {
        String serverUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
        String user = "root";
        String password = "your_password";
        String dbName = "samdan_db";

        List<String> tableDefinitions = List.of(
            // 1. Independent Parent Tables
            """
            CREATE TABLE IF NOT EXISTS KITAP (
                kitapISBN CHAR(13) PRIMARY KEY,
                kitapAdi VARCHAR(255) NOT NULL,
                kitapTuru VARCHAR(100),
                kitapSayfaSayisi INT,
                kitapCikisYili INT,
                kitapBaskiSayisi INT
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS YAZAR (
                yazarNumarasi INT AUTO_INCREMENT PRIMARY KEY,
                yazarAdi VARCHAR(150) NOT NULL
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS YAYINEVI (
                yayineviNumarasi INT AUTO_INCREMENT PRIMARY KEY,
                yayineviAdi VARCHAR(150) NOT NULL
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS KUTUPHANE (
                kutuphaneNumarasi INT AUTO_INCREMENT PRIMARY KEY,
                kutuphaneAdi VARCHAR(150) NOT NULL,
                kutuphaneKonumu VARCHAR(255)
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS KULLANICI (
                kullaniciAdi VARCHAR(50) PRIMARY KEY,
                gercekAdi VARCHAR(150) NOT NULL,
                sifre VARCHAR(255) NOT NULL,
                yetkiSeviyesi VARCHAR(30),
                okurYasi INT,
                TCNO CHAR(11),
                UNIQUE (TCNO)
            ) ENGINE=InnoDB
            """,

            // 2. Dependent Relationship & Junction Tables
            """
            CREATE TABLE IF NOT EXISTS DEGERLENDIRME (
                kitapISBN CHAR(13),
                kullaniciAdi VARCHAR(50),
                degerlendirme TEXT,
                puan INT CHECK (puan BETWEEN 0 AND 10),
                PRIMARY KEY (kitapISBN, kullaniciAdi),
                FOREIGN KEY (kitapISBN) REFERENCES KITAP(kitapISBN)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (kullaniciAdi) REFERENCES KULLANICI(kullaniciAdi)
                    ON DELETE CASCADE ON UPDATE CASCADE
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS YAZARI_OLMA (
                kitapISBN CHAR(13),
                yazarNumarasi INT,
                PRIMARY KEY (kitapISBN, yazarNumarasi),
                FOREIGN KEY (kitapISBN) REFERENCES KITAP(kitapISBN)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (yazarNumarasi) REFERENCES YAZAR(yazarNumarasi)
                    ON DELETE CASCADE ON UPDATE CASCADE
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS YAYINEVI_OLMA (
                kitapISBN CHAR(13),
                yayineviNumarasi INT,
                PRIMARY KEY (kitapISBN, yayineviNumarasi),
                FOREIGN KEY (kitapISBN) REFERENCES KITAP(kitapISBN)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (yayineviNumarasi) REFERENCES YAYINEVI(yayineviNumarasi)
                    ON DELETE CASCADE ON UPDATE CASCADE
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS KUTUPHANE_BULUNMA (
                kitapISBN CHAR(13),
                kutuphaneNumarasi INT,
                kopyaNumarasi INT,
                PRIMARY KEY (kitapISBN, kutuphaneNumarasi, kopyaNumarasi),
                FOREIGN KEY (kitapISBN) REFERENCES KITAP(kitapISBN)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (kutuphaneNumarasi) REFERENCES KUTUPHANE(kutuphaneNumarasi)
                    ON DELETE CASCADE ON UPDATE CASCADE
            ) ENGINE=InnoDB
            """,
            """
            CREATE TABLE IF NOT EXISTS ARIYET (
                kitapISBN CHAR(13),
                kopyaNumarasi INT,
                kutuphaneNumarasi INT,
                kullaniciAdi VARCHAR(50),
                PRIMARY KEY (kitapISBN, kopyaNumarasi, kutuphaneNumarasi),
                FOREIGN KEY (kitapISBN, kutuphaneNumarasi, kopyaNumarasi)
                    REFERENCES KUTUPHANE_BULUNMA(kitapISBN, kutuphaneNumarasi, kopyaNumarasi)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (kullaniciAdi) REFERENCES KULLANICI(kullaniciAdi)
                    ON DELETE CASCADE ON UPDATE CASCADE
            ) ENGINE=InnoDB
            """
        );

        try (Connection conn = DriverManager.getConnection(serverUrl, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName +
                               " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            stmt.execute("USE " + dbName);

            for (String ddl : tableDefinitions) {
                stmt.executeUpdate(ddl);
            }
            System.out.println("All tables created successfully in '" + dbName + "'.");

            long startTime = System.currentTimeMillis();
            MasterKeys masterKeys = seedMasterTables(conn);
            seedDependentTables(conn, masterKeys);
            long elapsedMs = System.currentTimeMillis() - startTime;

            System.out.printf("Seeding complete in %.2f seconds.%n", elapsedMs / 1000.0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // 1. MASTER TABLES
    // =========================================================================

    public static MasterKeys seedMasterTables(Connection conn) throws SQLException {
        MasterKeys keys = new MasterKeys();
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            keys.yazarIds = seedYazarlar(conn, 1000);
            keys.yayineviIds = seedYayinevleri(conn, 200);
            keys.kutuphaneIds = seedKutuphaneler(conn, 50);
            keys.kitapIsbns = seedKitaplar(conn, 5000);
            keys.kullaniciAdlari = seedKullanicilar(conn, 2500);

            conn.commit();
            System.out.printf("Master tables seeded: %d books, %d authors, %d publishers, %d libraries, %d users.%n",
                keys.kitapIsbns.size(), keys.yazarIds.size(), keys.yayineviIds.size(),
                keys.kutuphaneIds.size(), keys.kullaniciAdlari.size());
            return keys;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(previousAutoCommit);
        }
    }

    private static List<Integer> seedYazarlar(Connection conn, int count) throws SQLException {
        String[] adlar = {"Ahmet", "Mehmet", "Ayşe", "Fatma", "Mustafa", "Zeynep", "Emre", "Elif", "Can", "Deniz",
                          "Burak", "Selin", "Mert", "Ece", "Kerem", "İrem", "Oğuz", "Ceren", "Kaan", "Melis", "Sabahattin",
                          "Barbaros", "Mesut", "Michael", "Goku" , "Vegeta", "Jotaro", "Dio", "Kakyoin", "Recaizade Mahmut"
        };
        String[] soyadlar = {"Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Yıldız", "Yıldırım", "Öztürk", "Aydın", "Özdemir",
                             "Arslan", "Doğan", "Kılıç", "Aslan", "Çetin", "Kara", "Koç", "Kurt", "Özkan", "Şimşek", "Ali",
                             "Gergin", "Köş", "Jackson", "Jackson Jr.", "Kujo", "Brando" , "Ekrem" 
        };

        String sql = "INSERT IGNORE INTO YAZAR (yazarNumarasi, yazarAdi) VALUES (?, ?)";
        List<Integer> ids = new ArrayList<>(count);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String yazarAdi = adlar[i % adlar.length] + " " + soyadlar[(i / adlar.length) % soyadlar.length] + " " + i;
                ps.setInt(1, i);
                ps.setString(2, yazarAdi);
                ps.addBatch();
                ids.add(i);

                if (i % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return ids;
    }

    private static List<Integer> seedYayinevleri(Connection conn, int count) throws SQLException {
        String[] kokler = {"Atlas", "Kuzey", "Güney", "Ufuk", "Bilgi", "Evren", "Anadolu", "Bosphorus", "Odak", "Çağdaş"};
        String sql = "INSERT IGNORE INTO YAYINEVI (yayineviNumarasi, yayineviAdi) VALUES (?, ?)";
        List<Integer> ids = new ArrayList<>(count);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String yayineviAdi = kokler[i % kokler.length] + " Yayınları " + i;
                ps.setInt(1, i);
                ps.setString(2, yayineviAdi);
                ps.addBatch();
                ids.add(i);

                if (i % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return ids;
    }

    private static List<Integer> seedKutuphaneler(Connection conn, int count) throws SQLException {
        String[] sehirler = {"Ankara", "İstanbul", "İzmir", "Bursa", "Antalya", "Eskişehir", "Konya", "Adana", "Trabzon", "Samsun"};
        String[] semtler = {"Merkez", "Çankaya", "Kadıköy", "Beşiktaş", "Bornova", "Nilüfer", "Muratpaşa", "Tepebaşı", "Selçuklu", "Seyhan"};

        String sql = "INSERT IGNORE INTO KUTUPHANE (kutuphaneNumarasi, kutuphaneAdi, kutuphaneKonumu) VALUES (?, ?, ?)";
        List<Integer> ids = new ArrayList<>(count);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String sehir = sehirler[i % sehirler.length];
                String semt = semtler[(i / sehirler.length) % semtler.length];
                ps.setInt(1, i);
                ps.setString(2, semt + " Halk Kütüphanesi " + i);
                ps.setString(3, semt + ", " + sehir);
                ps.addBatch();
                ids.add(i);

                if (i % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return ids;
    }

    private static List<String> seedKitaplar(Connection conn, int count) throws SQLException {
        String[] sifatlar = {"Kayıp", "Son", "Gizli", "Karanlık", "Sessiz", "Uzak", "Sonsuz", "Eski", "Yeni", "Derin", "Lezzetli"};
        String[] isimler = {"Şehir", "Zaman", "Gölge", "Yolculuk", "Kitap", "Gece", "Deniz", "Rüzgar", "Anılar", "Dünya", "Armut"};
        String[] turler = { "Tarih", "Bilimkurgu", "İş Kitapları", "Dil Öğrenimi", "Çocuk", "Macera", "Gizem", "Korku", "Romantizm", 
                            "Grafik Roman", "Biyografi", "Otobiyografi", "Yemek", "Şiir", "Kılavuz", "Seyahat", "Albüm", "Deneme", "Bilim"};

        String sql = "INSERT IGNORE INTO KITAP (kitapISBN, kitapAdi, kitapTuru, kitapSayfaSayisi, kitapCikisYili, kitapBaskiSayisi) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        List<String> isbns = new ArrayList<>(count);
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String isbn = String.format("978%010d", i);
                String kitapAdi = sifatlar[i % sifatlar.length] + " " + isimler[(i / sifatlar.length) % isimler.length] + " #" + i;
                String kitapTuru = turler[i % turler.length];

                ps.setString(1, isbn);
                ps.setString(2, kitapAdi);
                ps.setString(3, kitapTuru);
                ps.setInt(4, rand.nextInt(80, 950));
                ps.setInt(5, rand.nextInt(1950, 2027));
                ps.setInt(6, rand.nextInt(1, 25));
                ps.addBatch();
                isbns.add(isbn);

                if (i % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return isbns;
    }

    private static List<String> seedKullanicilar(Connection conn, int count) throws SQLException {
        String[] adlar = {"Ali", "Veli", "Ayşe", "Fatma", "Mert", "Seda", "Kaan", "Buse", "Tarık", "Gamze",
                          "Berk", "Dilara", "Sinan", "Nazlı", "Tolga", "Hande", "Umut", "Gizem", "Onur", "Aslı",
                          "Emre", "Melih Efe", "Mehmet Efe", "Mesut Barbaros", "Osman", "Mehmet Alp", "Furkan",
                          "Ahmet Taha", "Juan", "Pablo", "Sedat", "Jonathan", "Jeff", "Joseph", "Kyle", "Elizabeth",
                          "Perso"
        };
        String[] soyadlar = {"Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Yıldız", "Öztürk", "Aydın", "Arslan", "Doğan",
                             "Oğuzoğulları", "Yıldırımoğulları", "Kayaoğulları", "Mehmetoğulları", "Karaduman", "Karadoğan",
                             "Darksmoke", "Popsmoke", "Jackson", "Brown", "Brown Jr.", "Köş", "Escobar", "Saraç", "Peker",
                             "Kuru", "Oğuz Tekin", "Uzunpostalcı", "Kılınç", "Tursun", "Dursun", "Joestar", "Nabir"
        };

        String sql = "INSERT IGNORE INTO KULLANICI (kullaniciAdi, gercekAdi, sifre, yetkiSeviyesi, okurYasi, TCNO) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        List<String> kullaniciAdlari = new ArrayList<>(count);
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String kullaniciAdi = "okur_" + i;
                String gercekAdi = adlar[i % adlar.length] + " " + soyadlar[(i / adlar.length) % soyadlar.length];
                String tcNo = String.valueOf(10000000000L + i);

                ps.setString(1, kullaniciAdi);
                ps.setString(2, gercekAdi);
                ps.setString(3, "pass_" + i);
                ps.setString(4, (i % 10 == 0) ? "Gorevli" : "Okur");
                ps.setInt(5, rand.nextInt(15, 75));
                ps.setString(6, tcNo);
                ps.addBatch();
                kullaniciAdlari.add(kullaniciAdi);

                if (i % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return kullaniciAdlari;
    }

    // =========================================================================
    // 2. JUNCTION & TRANSACTIONAL TABLES
    // =========================================================================

    public static void seedDependentTables(Connection conn, MasterKeys keys) throws SQLException {
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            int yazariOlmaRows = seedYazariOlma(conn, keys);
            int yayineviOlmaRows = seedYayineviOlma(conn, keys);
            int degerlendirmeRows = seedDegerlendirmeler(conn, keys);
            int kutuphaneBulunmaRows = seedKutuphaneBulunma(conn, keys, 2); // 2 copies per book-library pair
            int ariyetRows = seedAriyet(conn, keys);

            conn.commit();
            System.out.printf(
                "Dependent tables seeded: YAZARI_OLMA=%d, YAYINEVI_OLMA=%d, DEGERLENDIRME=%d, KUTUPHANE_BULUNMA=%d, ARIYET=%d.%n",
                yazariOlmaRows, yayineviOlmaRows, degerlendirmeRows, kutuphaneBulunmaRows, ariyetRows
            );
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(previousAutoCommit);
        }
    }

    // 5,000 books x 1 author = 5,000 rows
    private static int seedYazariOlma(Connection conn, MasterKeys keys) throws SQLException {
        String sql = "INSERT IGNORE INTO YAZARI_OLMA (kitapISBN, yazarNumarasi) VALUES (?, ?)";
        int totalRows = 0;
        int yazarCount = keys.yazarIds.size();
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < keys.kitapIsbns.size(); i++) {
                ps.setString(1, keys.kitapIsbns.get(i));
                ps.setInt(2, keys.yazarIds.get(i % yazarCount));
                ps.addBatch();
    
                if (++totalRows % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return totalRows;
    }

    // 5,000 books x 1 publisher = 5,000 rows
    private static int seedYayineviOlma(Connection conn, MasterKeys keys) throws SQLException {
        String sql = "INSERT IGNORE INTO YAYINEVI_OLMA (kitapISBN, yayineviNumarasi) VALUES (?, ?)";
        int totalRows = 0;
        int yayineviCount = keys.yayineviIds.size();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < keys.kitapIsbns.size(); i++) {
                ps.setString(1, keys.kitapIsbns.get(i));
                ps.setInt(2, keys.yayineviIds.get(i % yayineviCount));
                ps.addBatch();

                if (++totalRows % BATCH_SIZE == 0) ps.executeBatch();
            }
            ps.executeBatch();
        }
        return totalRows;
    }

    // 2,500 books x 100 users = 250,000 rows
    private static int seedDegerlendirmeler(Connection conn, MasterKeys keys) throws SQLException {
        String[] yorumHavuzu = {
            "Akici ve surukleyici bir anlatim.",
            "Beklentimin altinda kaldi.",
            "Konu islenisi gayet basarili.",
            "Ortalama bir eser, okunabilir.",
            "Karakter gelisimi cok etkileyiciydi."
        };

        String sql = "INSERT IGNORE INTO DEGERLENDIRME (kitapISBN, kullaniciAdi, degerlendirme, puan) VALUES (?, ?, ?, ?)";
        int totalRows = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < 2500; i++) {
                String isbn = keys.kitapIsbns.get(i);
                for (int j = 0; j < 100; j++) {
                    ps.setString(1, isbn);
                    ps.setString(2, keys.kullaniciAdlari.get(j));
                    ps.setString(3, yorumHavuzu[(i + j) % yorumHavuzu.length]);
                    ps.setInt(4, (i + j) % 11); // Satisfies CHECK (puan BETWEEN 0 AND 10)
                    ps.addBatch();

                    if (++totalRows % BATCH_SIZE == 0) ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
        return totalRows;
    }

    // 5,000 books x 50 libraries x 2 copies = 500,000 rows
    private static int seedKutuphaneBulunma(Connection conn, MasterKeys keys, int copiesPerLibrary) throws SQLException {
        String sql = "INSERT IGNORE INTO KUTUPHANE_BULUNMA (kitapISBN, kutuphaneNumarasi, kopyaNumarasi) VALUES (?, ?, ?)";
        int totalRows = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String isbn : keys.kitapIsbns) {
                for (int kutuphaneId : keys.kutuphaneIds) {
                    for (int kopyaNo = 1; kopyaNo <= copiesPerLibrary; kopyaNo++) {
                        ps.setString(1, isbn);
                        ps.setInt(2, kutuphaneId);
                        ps.setInt(3, kopyaNo);
                        ps.addBatch();

                        if (++totalRows % BATCH_SIZE == 0) ps.executeBatch();
                    }
                }
            }
            ps.executeBatch();
        }
        return totalRows;
    }

    // 5,000 books x 50 libraries x copy #1 = 250,000 active loans (copy #2 remains on shelf)
    private static int seedAriyet(Connection conn, MasterKeys keys) throws SQLException {
        String sql = "INSERT IGNORE INTO ARIYET (kitapISBN, kopyaNumarasi, kutuphaneNumarasi, kullaniciAdi) VALUES (?, ?, ?, ?)";
        int totalRows = 0;
        int userCount = keys.kullaniciAdlari.size();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int b = 0; b < keys.kitapIsbns.size(); b++) {
                String isbn = keys.kitapIsbns.get(b);
                for (int l = 0; l < keys.kutuphaneIds.size(); l++) {
                    int kutuphaneId = keys.kutuphaneIds.get(l);
                    String kullaniciAdi = keys.kullaniciAdlari.get((b + l) % userCount);

                    ps.setString(1, isbn);
                    ps.setInt(2, 1); // Copy #1 is guaranteed to exist in KUTUPHANE_BULUNMA
                    ps.setInt(3, kutuphaneId);
                    ps.setString(4, kullaniciAdi);
                    ps.addBatch();

                    if (++totalRows % BATCH_SIZE == 0) ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
        return totalRows;
    }
}
