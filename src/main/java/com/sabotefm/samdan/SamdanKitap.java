package com.sabotefm.samdan;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
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

public class SamdanKitap {

    public static void showKitap(String selectedIsbn) {
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
            BasicWindow window = new BasicWindow("Şamdan - Kitap Sayfası");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            window.setTheme(customTheme);

            Panel mainPanel = new Panel(new GridLayout(1));
			
			Panel informationPanel = new Panel();
			informationPanel.setLayoutManager(new GridLayout(2));
			
			Panel firstPanel = new Panel();
			firstPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Kitap Adı").addTo(firstPanel);
			new Label(SamdanMechanic.getBookName(gui, selectedIsbn)).addTo(firstPanel);
			firstPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel seventhPanel = new Panel();
			seventhPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Tür").addTo(seventhPanel);
			new Label(SamdanMechanic.getTypeName(gui, selectedIsbn)).addTo(seventhPanel);
			seventhPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel fourthPanel = new Panel();
			fourthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Yazar").addTo(fourthPanel);
			new Label(SamdanMechanic.getWriterName(gui, selectedIsbn)).addTo(fourthPanel);
			fourthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel ninthPanel = new Panel();
			ninthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Yazar Numarası").addTo(ninthPanel);
			new Label(SamdanMechanic.getWriterNumber(gui, selectedIsbn)).addTo(ninthPanel);
			ninthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel fifthPanel = new Panel();
			fifthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Yayınevi").addTo(fifthPanel);
			new Label(SamdanMechanic.getPublisherName(gui, selectedIsbn)).addTo(fifthPanel);
			fifthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel tenthPanel = new Panel();
			tenthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Yayınevi Numarası").addTo(tenthPanel);
			new Label(SamdanMechanic.getPublisherNumber(gui, selectedIsbn)).addTo(tenthPanel);
			tenthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel secondPanel = new Panel();
			secondPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Basım Tarihi").addTo(secondPanel);
			new Label(SamdanMechanic.getReleaseDate(gui, selectedIsbn)).addTo(secondPanel);
			secondPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel thirdPanel = new Panel();
			thirdPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Baskı Sayısı").addTo(thirdPanel);
			new Label(SamdanMechanic.getNumberOfPrints(gui, selectedIsbn)).addTo(thirdPanel);
			thirdPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel eighthPanel = new Panel();
			eighthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Sayfa Sayısı").addTo(eighthPanel);
			new Label(SamdanMechanic.getPageCount(gui, selectedIsbn)).addTo(eighthPanel);
			eighthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel sixthPanel = new Panel();
			sixthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Kütüphane").addTo(sixthPanel);
			new Label(SamdanMechanic.getLibraryName(gui, selectedIsbn)).addTo(sixthPanel);
			sixthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel eleventhPanel = new Panel();
			eleventhPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Kütüphane Numarası").addTo(eleventhPanel);
			new Label(SamdanMechanic.getLibraryNumber(gui, selectedIsbn)).addTo(eleventhPanel);
			eleventhPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			Panel twelvthPanel = new Panel();
			twelvthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Kütüphane Konumu").addTo(twelvthPanel);
			new Label(SamdanMechanic.getLibraryLocation(gui, selectedIsbn)).addTo(twelvthPanel);
			twelvthPanel.addComponent(new Separator(Direction.HORIZONTAL));
			
			informationPanel.addComponent(firstPanel);
			informationPanel.addComponent(seventhPanel);
			informationPanel.addComponent(eighthPanel);
			informationPanel.addComponent(secondPanel);
			informationPanel.addComponent(thirdPanel);
			informationPanel.addComponent(fourthPanel);
			informationPanel.addComponent(ninthPanel);
			informationPanel.addComponent(fifthPanel);
			informationPanel.addComponent(tenthPanel);
			informationPanel.addComponent(sixthPanel);
			informationPanel.addComponent(eleventhPanel);
			informationPanel.addComponent(twelvthPanel);
			
			mainPanel.addComponent(informationPanel);
			
            mainPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
            
            Panel ariyetPanel = new Panel(new GridLayout(1));
            new Label("Kitabı Ariyeten Alan: " + SamdanMechanic.getWhoBorrowed(gui, selectedIsbn)).addTo(ariyetPanel);
            
            mainPanel.addComponent(ariyetPanel);
            
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
