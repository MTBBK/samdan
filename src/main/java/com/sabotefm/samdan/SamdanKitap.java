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
			new Label("Kitap: " + SamdanMechanic.getBookName(selectedIsbn)).addTo(firstPanel);
			
			Panel secondPanel = new Panel();
			secondPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Yazar: " + SamdanMechanic.getWriterName(selectedIsbn)).addTo(secondPanel);
			
			Panel thirdPanel = new Panel();
			thirdPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Yayınevi: " + SamdanMechanic.getPublisherName(selectedIsbn)).addTo(thirdPanel);
			
			Panel fourthPanel = new Panel();
			fourthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Kütüphane: " + SamdanMechanic.getLibraryName(selectedIsbn)).addTo(fourthPanel);
			
			Panel fifthPanel = new Panel();
			fifthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Tür: " + SamdanMechanic.getTypeName(selectedIsbn)).addTo(fifthPanel);
			
			Panel sixthPanel = new Panel();
			sixthPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			new Label("Sayfa Sayısı: " + SamdanMechanic.getPageCount(selectedIsbn)).addTo(sixthPanel);			
			
			informationPanel.addComponent(firstPanel);
			informationPanel.addComponent(secondPanel);
			informationPanel.addComponent(thirdPanel);
			informationPanel.addComponent(fourthPanel);
			informationPanel.addComponent(fifthPanel);
			informationPanel.addComponent(sixthPanel);
			
			mainPanel.addComponent(informationPanel);
            
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
