package com.sabotefm.samdan;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SamdanDashboard {

    public static void showDashboard() {
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            
            SimpleTheme customTheme = SimpleTheme.makeTheme(
                true,
                TextColor.ANSI.YELLOW_BRIGHT,
                TextColor.ANSI.BLACK,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.CYAN,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLACK_BRIGHT,
                TextColor.ANSI.MAGENTA
            );
            
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            BasicWindow window = new BasicWindow("Şamdan - Esas Sayfa");
            window.setTheme(customTheme);

            Panel mainPanel = new Panel(new GridLayout(1));

            //Navbar, Header Panel
            Panel headerPanel = new Panel(new GridLayout(3));
            headerPanel.addComponent(new Label("ŞAMDAN KÜTÜPHANE"));
            headerPanel.addComponent(new Label(" | Kullanıcı: Emre Karaduman"));
            headerPanel.addComponent(new Button("Profil", () -> SamdanKullanici.showKullanici()));
            mainPanel.addComponent(headerPanel);

            mainPanel.addComponent(new Separator(Direction.HORIZONTAL));

            //Books Table View Setup with selection column at the start
            Table<String> bookTable = new Table<>("[ ]", "ISBN", "Kitap Adı", "Yazar", "Yayınevi", "Tür", "Sayfa Sayısı");
            bookTable.setVisibleRows(15);
            
            TableModel<String> tableModel = bookTable.getTableModel();

            // Track the explicitly selected row index (-1 means none selected yet)
            final int[] confirmedSelectedRow = {-1};

            // Helper to load and format table data with the [ ] selection column
            Runnable loadAndFormatArchive = () -> {
                tableModel.clear();
                Table<String> tempTable = new Table<>("ISBN", "Kitap Adı", "Yazar", "Yayınevi", "Tür", "Sayfa Sayısı");
                SamdanMechanic.loadBookArchive(gui, tempTable.getTableModel());
                
                for (int i = 0; i < tempTable.getTableModel().getRowCount(); i++) {
                    List<String> srcRow = tempTable.getTableModel().getRow(i);
                    List<String> newRow = new ArrayList<>();
                    newRow.add("[ ]");
                    newRow.addAll(srcRow);
                    tableModel.addRow(newRow.toArray(new String[0]));
                }
                confirmedSelectedRow[0] = -1;
            };

            loadAndFormatArchive.run();

            // Helper to update selection indicators based on the confirmed selection
            Runnable updateSelectionIndicators = () -> {
                int currentHighlight = bookTable.getSelectedRow();
                // If the user hits enter or explicitly interacts, we can lock in the highlighted row
                if (currentHighlight >= 0) {
                    confirmedSelectedRow[0] = currentHighlight;
                }

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String indicator = (i == confirmedSelectedRow[0] && confirmedSelectedRow[0] >= 0) ? "[*]" : "[ ]";
                    if (tableModel.getColumnCount() > 0) {
                        tableModel.setCell(0, i, indicator);
                    }
                }
            };

            // When user presses Enter or triggers selection action on a row
            bookTable.setSelectAction(() -> {
                int currentHighlight = bookTable.getSelectedRow();
                if (currentHighlight >= 0) {
                    confirmedSelectedRow[0] = currentHighlight;
                    updateSelectionIndicators.run();
                }
            });
            
            updateSelectionIndicators.run();

            mainPanel.addComponent(bookTable.withBorder(Borders.singleLine("Kitap Listesi")));

            //Search and Filter Panel
            Panel searchPanel = new Panel(new GridLayout(2));            
            
          	//Kitap Türü Combo Box
            new Label("Kitap Türü: ").addTo(searchPanel);
          	ComboBox<String> kitapTuruComboBox = new ComboBox<String>();
          	kitapTuruComboBox.addItem("Hepsi");
          	kitapTuruComboBox.addItem("Tarih");
          	kitapTuruComboBox.addItem("Bilimkurgu");
          	kitapTuruComboBox.addItem("İş Kitapları");
          	kitapTuruComboBox.addItem("Dil Öğrenimi");
          	kitapTuruComboBox.addItem("Çocuk");
          	kitapTuruComboBox.addItem("Macera");
          	kitapTuruComboBox.addItem("Gizem");
          	kitapTuruComboBox.addItem("Korku");
          	kitapTuruComboBox.addItem("Romantizm");
          	kitapTuruComboBox.addItem("Grafik Roman");
          	kitapTuruComboBox.addItem("Biyografi");
          	kitapTuruComboBox.addItem("Otobiyografi");
          	kitapTuruComboBox.addItem("Yemek");
          	kitapTuruComboBox.addItem("Şiir");
          	kitapTuruComboBox.addItem("Kılavuz");
          	kitapTuruComboBox.addItem("Seyahat");
          	kitapTuruComboBox.addItem("Albüm");
          	kitapTuruComboBox.addItem("Deneme");
          	kitapTuruComboBox.addItem("Bilim");
          	String kitapTurleri[] = {"Hepsi", "Tarih", "Bilimkurgu", "İş Kitapları", "Dil Öğrenimi", "Çocuk", "Macera", "Gizem", "Korku", "Romantizm", "Grafik Roman", "Biyografi", "Otobiyografi", "Yemek", "Şiir", "Kılavuz", "Seyahat", "Albüm", "Deneme", "Bilim"};
          	int seciliKitapTuru = kitapTuruComboBox.getSelectedIndex();
          	searchPanel.addComponent(kitapTuruComboBox);
          	
            searchPanel.addComponent(new Label("Kitap Adı:"));
            TextBox searchBoxKitapAdi = new TextBox(new TerminalSize(30, 1));
            searchPanel.addComponent(searchBoxKitapAdi.withBorder(Borders.singleLine()));
            
            searchPanel.addComponent(new Label("Yazar:"));
            TextBox searchBoxYazar = new TextBox(new TerminalSize(30, 1));
            searchPanel.addComponent(searchBoxYazar.withBorder(Borders.singleLine()));
            
            searchPanel.addComponent(new Label("Yayınevi:"));
            TextBox searchBoxYayinevi = new TextBox(new TerminalSize(30, 1));
            searchPanel.addComponent(searchBoxYayinevi.withBorder(Borders.singleLine()));
            
            searchPanel.addComponent(new Label("Kütüphane:"));
            TextBox searchBoxKutuphane = new TextBox(new TerminalSize(30, 1));
            searchPanel.addComponent(searchBoxKutuphane.withBorder(Borders.singleLine()));
            
            //Call SamdanMechanic for searching
            searchPanel.addComponent(new Button("Ara", () -> {
                tableModel.clear();
                Table<String> tempTable = new Table<>("ISBN", "Kitap Adı", "Yazar", "Yayınevi", "Tür", "Sayfa Sayısı");
                SamdanMechanic.searchBooks(gui, tempTable.getTableModel(), searchBoxKitapAdi.getTextOrDefault("0"), searchBoxYazar.getTextOrDefault("0"), searchBoxYayinevi.getTextOrDefault("0"), searchBoxKutuphane.getTextOrDefault("0"), kitapTurleri[seciliKitapTuru]);
                
                for (int i = 0; i < tempTable.getTableModel().getRowCount(); i++) {
                    List<String> srcRow = tempTable.getTableModel().getRow(i);
                    List<String> newRow = new ArrayList<>();
                    newRow.add("[ ]");
                    newRow.addAll(srcRow);
                    tableModel.addRow(newRow.toArray(new String[0]));
                }
                confirmedSelectedRow[0] = -1;
                updateSelectionIndicators.run();
            }));
            
            searchPanel.addComponent(new Button("Filtreleri Temizle", () -> {
                searchBoxKitapAdi.setText("");
                searchBoxYazar.setText("");
                searchBoxYayinevi.setText("");
                searchBoxKutuphane.setText("");
                loadAndFormatArchive.run();
                updateSelectionIndicators.run();
            }));
          	
          	mainPanel.addComponent(searchPanel);
          	
            //Action Buttons Panel
            Panel actionPanel = new Panel(new GridLayout(4));
            
            // Helper lambda to fetch the currently confirmed selected book's ISBN
            java.util.function.Supplier<String> getSelectedIsbn = () -> {
                // If nothing was explicitly selected via Enter, fall back to current highlight if valid
                if (confirmedSelectedRow[0] == -1 && bookTable.getSelectedRow() >= 0) {
                    confirmedSelectedRow[0] = bookTable.getSelectedRow();
                    updateSelectionIndicators.run();
                }
                
                if (confirmedSelectedRow[0] >= 0 && tableModel.getRowCount() > confirmedSelectedRow[0]) {
                    return tableModel.getCell(1, confirmedSelectedRow[0]);
                }
                return null;
            };

            actionPanel.addComponent(new Button("Kitabı Edin", () -> {
                SamdanMechanic.markBookAsBorrowed(gui, getSelectedIsbn.get());
            }));

            actionPanel.addComponent(new Button("Ariyet (Kitap Kimde?)", () -> {
                SamdanMechanic.checkWhoBorrowed(gui, getSelectedIsbn.get());
            }));

            actionPanel.addComponent(new Button("Değerlendirmeler", () -> {
                SamdanMechanic.openReviews(gui, getSelectedIsbn.get());
            }));
            
            actionPanel.addComponent(new Button("Kitap Hakkında", () -> {
                SamdanMechanic.openBookInformation(gui, getSelectedIsbn.get());
            }));

            mainPanel.addComponent(actionPanel);

            window.setComponent(mainPanel);
            gui.addWindowAndWait(window);

            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
