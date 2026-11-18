package com.sabotefm.samdan;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

public class SamdanGiris {

    public static void showGiris() {
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
            BasicWindow window = new BasicWindow("Şamdan - Giriş Sayfası");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            window.setTheme(customTheme);

            Panel mainPanel = new Panel(new GridLayout(1));

            //Kullanıcı Adı Text Box
            TextBox kullaniciAdiTextBox = new TextBox(new TerminalSize(30, 1));
			kullaniciAdiTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z0-9_ ]{0,30}$"));
			
			//Şifre Text Box
            TextBox sifreTextBox = new TextBox(new TerminalSize(30, 1));
			sifreTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z0-9_ ]{0,30}$"));
			sifreTextBox.setMask('*');
			
			mainPanel.addComponent(kullaniciAdiTextBox.withBorder(Borders.singleLine("Kullanıcı Adı")));
			mainPanel.addComponent(sifreTextBox.withBorder(Borders.singleLine("Şifre")));
            mainPanel.addComponent(new Button("Giriş", () -> SamdanMechanic.loginToSamdan(gui, kullaniciAdiTextBox.getText(), sifreTextBox.getText())));
			
            
            //Zemin Panel
            Panel zeminPanel = new Panel(new GridLayout(3));
            mainPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
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
