package com.sabotefm.samdan;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.gui2.Direction.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SamdanDegerlendirmeler {

    public static void showDegerlendirmeler(String selectedIsbn) {
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
            
            TextColor araplanRengi = new TextColor.RGB(255, 90, 90);
            
           	MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(araplanRengi));
            BasicWindow window = new BasicWindow("Şamdan - Değerlendirmeler Sayfası");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            window.setTheme(customTheme);

            Panel mainPanel = new Panel(new GridLayout(1));
			
			Label kullaniciAdi = new Label("Kitap Adı: " + SamdanMechanic.getBookName(gui, selectedIsbn)).addTo(mainPanel);
			
			mainPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
			
			Table<String> ratingsTable = new Table<>("[ ]", "Kullanıcı Adı", "Puan", "Değerlendirme");
			TableModel<String> tableModel = ratingsTable.getTableModel();

            // Track the explicitly selected row index (-1 means none selected yet)
            final int[] confirmedSelectedRow = {-1};

            // Helper to load and format table data with the [ ] selection column
            Runnable loadAndFormatArchive = () -> {
                tableModel.clear();
                Table<String> tempTable = new Table<>("Kullanıcı Adı", "Puan", "Değerlendirme");
                SamdanMechanic.loadRatingsTableForABook(tempTable.getTableModel(), selectedIsbn);
                
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
                int currentHighlight = ratingsTable.getSelectedRow();
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
            ratingsTable.setSelectAction(() -> {
                int currentHighlight = ratingsTable.getSelectedRow();
                if (currentHighlight >= 0) {
                    confirmedSelectedRow[0] = currentHighlight;
                    updateSelectionIndicators.run();
                }
            });
            
            updateSelectionIndicators.run();
            
            mainPanel.addComponent(ratingsTable.withBorder(Borders.singleLine("Değerlendirmeler")));
            
            mainPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
            
            //Puan Combo Box
            new Label("Puan: ").addTo(mainPanel);
          	ComboBox<String> puanComboBox = new ComboBox<String>();
          	puanComboBox.addItem("1/10");
          	puanComboBox.addItem("2/10");
          	puanComboBox.addItem("3/10");
          	puanComboBox.addItem("4/10");
          	puanComboBox.addItem("5/10");
          	puanComboBox.addItem("6/10");
          	puanComboBox.addItem("7/10");
          	puanComboBox.addItem("8/10");
          	puanComboBox.addItem("9/10");
          	puanComboBox.addItem("10/10");
          	int puan = puanComboBox.getSelectedIndex() + 1;
          	
          	mainPanel.addComponent(puanComboBox);
          	mainPanel.addComponent(new Separator(Direction.HORIZONTAL));
          	
            //Değerlendirme Text Box
            TextBox degerlendirmeTextBox = new TextBox(new TerminalSize(40, 8));
			mainPanel.addComponent(degerlendirmeTextBox.withBorder(Borders.singleLine("Değerlendirme")));
			
			//Değerlendirme Gönder Butonu
			mainPanel.addComponent(new Button("Gönder", () -> SamdanMechanic.saveRatings(selectedIsbn, puan, degerlendirmeTextBox.getText())));
            
            //Zemin Panel
            Panel zeminPanel = new Panel(new GridLayout(2));
            mainPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
            zeminPanel.addComponent(new Button("Geri Gel", () -> SamdanDashboard.showDashboard()));
            zeminPanel.addComponent(new Button("Programı Kapat", window::close));
            mainPanel.addComponent(zeminPanel);

            window.setComponent(mainPanel);
            gui.addWindowAndWait(window);

            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
