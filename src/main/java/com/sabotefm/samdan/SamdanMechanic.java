package com.sabotefm.samdan;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.TableModel;
import java.sql.*;

public class SamdanMechanic {

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

    // Handle book search functionality
    public static void searchBooks(WindowBasedTextGUI gui, String query, TableModel<String> tableModel) {
        if (query == null || query.trim().isEmpty()) {
            new MessageDialogBuilder()
                    .setTitle("Uyarı")
                    .setText("Lütfen aranacak bir kelime veya ISBN girin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
            return;
        }
        // TODO: Query MySQL database matching name, author, or ISBN
        new MessageDialogBuilder()
                .setTitle("Arama Sonucu")
                .setText("Aranan kelime: '" + query + "'\n(MySQL veritabanı sorgusu buraya bağlanacak)")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
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
        new MessageDialogBuilder()
                .setTitle("Başarılı")
                .setText("Seçilen kitap (ISBN: " + selectedIsbn + ") edindiğiniz olarak işaretlendi.")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
    }

    // Check which library holds the book for borrowing (Ariyet)
    public static void checkLibraryLocation(WindowBasedTextGUI gui, String selectedIsbn) {
        if (selectedIsbn == null) {
            new MessageDialogBuilder()
                    .setTitle("Seçim Hatası")
                    .setText("Lütfen tablodan bir kitap seçin.")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(gui);
            return;
        }
        // TODO: Query KÜTÜPHANEDE BULUNMA TABLOSU from MySQL
        new MessageDialogBuilder()
                .setTitle("Ariyet Durumu")
                .setText("Seçilen kitabın bulunduğu kütüphaneler sorgulanıyor...\n(ISBN: " + selectedIsbn + ")")
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
        new MessageDialogBuilder()
                .setTitle("Değerlendirmeler")
                .setText("Bu kitap için henüz yorum girilmemiş.\n(ISBN: " + selectedIsbn + ")")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
    }
}
