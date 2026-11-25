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

public class SamdanYonetici {

    public static void showYonetici() {
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
            BasicWindow window = new BasicWindow("Şamdan - Yönetici Sayfası");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            window.setTheme(customTheme);

            Panel mainPanel = new Panel(new GridLayout(1));
			
            //Kitap Ekleme Text Box
            //kitapISBN, kitapAdi, kitapTuru, kitapSayfaSayisi, kitapCikisYili, kitapBaskiSayisi
            Panel kitapOzellikleriPanel = new Panel(new GridLayout(3));
            
            TextBox kitapISBNTextBox = new TextBox(new TerminalSize(40, 1));
            kitapISBNTextBox.setValidationPattern(Pattern.compile("^[0-9]{0,13}$"));
			kitapOzellikleriPanel.addComponent(kitapISBNTextBox.withBorder(Borders.singleLine("ISBN")));
			
            TextBox kitapAdiTextBox = new TextBox(new TerminalSize(40, 1));
            kitapAdiTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z0-9_ ]{0,30}$"));
			kitapOzellikleriPanel.addComponent(kitapAdiTextBox.withBorder(Borders.singleLine("Kitap Adı")));
			
            TextBox kitapTuruTextBox = new TextBox(new TerminalSize(40, 1));
            kitapTuruTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z ]{0,30}$"));
			kitapOzellikleriPanel.addComponent(kitapTuruTextBox.withBorder(Borders.singleLine("Kitap Türü")));
			
            TextBox kitapSayfaSayisiTextBox = new TextBox(new TerminalSize(40, 1));
            kitapSayfaSayisiTextBox.setValidationPattern(Pattern.compile("^[0-9]{0,10}$"));
			kitapOzellikleriPanel.addComponent(kitapSayfaSayisiTextBox.withBorder(Borders.singleLine("Sayfa Sayısı")));
			
            TextBox kitapCikisYiliTextBox = new TextBox(new TerminalSize(40, 1));
            kitapCikisYiliTextBox.setValidationPattern(Pattern.compile("^[0-9]{0,4}$"));
			kitapOzellikleriPanel.addComponent(kitapCikisYiliTextBox.withBorder(Borders.singleLine("Çıkış Yılı")));
			
            TextBox kitapBaskiSayisiTextBox = new TextBox(new TerminalSize(40, 1));
            kitapBaskiSayisiTextBox.setValidationPattern(Pattern.compile("^[0-9]{0,5}$"));
			kitapOzellikleriPanel.addComponent(kitapBaskiSayisiTextBox.withBorder(Borders.singleLine("Baskı Sayısı")));
			
			//Yeni Kitap Ekle Butonu
			kitapOzellikleriPanel.addComponent(new Button("Yeni Kitabı Ekle", () -> SamdanMechanic.addNewBook(gui, kitapISBNTextBox.getText(), kitapAdiTextBox.getText(), kitapTuruTextBox.getText(), kitapSayfaSayisiTextBox.getText(), kitapCikisYiliTextBox.getText(), kitapBaskiSayisiTextBox.getText())));
            
            
            
            //Kullanıcı Ekleme Text Box
            //kullaniciAdi, gercekAdi, sifre, yetkiSeviyesi, okurYasi, TCNO
            Panel kullanıcıOzellikleriPanel = new Panel(new GridLayout(3));
            
            TextBox kullaniciAdiTextBox = new TextBox(new TerminalSize(40, 1));
            kullaniciAdiTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z0-9_ ]{0,30}$"));
			kullanıcıOzellikleriPanel.addComponent(kullaniciAdiTextBox.withBorder(Borders.singleLine("Kullanıcı Adı")));
			
            TextBox gercekAdiTextBox = new TextBox(new TerminalSize(40, 1));
            gercekAdiTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z ]{0,30}$"));
			kullanıcıOzellikleriPanel.addComponent(gercekAdiTextBox.withBorder(Borders.singleLine("Gerçek Ad")));
			
            TextBox sifreTextBox = new TextBox(new TerminalSize(40, 1));
            sifreTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z0-9_ ]{0,30}$"));
			kullanıcıOzellikleriPanel.addComponent(sifreTextBox.withBorder(Borders.singleLine("Şifre")));
			
            TextBox yetkiSeviyesiTextBox = new TextBox(new TerminalSize(40, 1));
            yetkiSeviyesiTextBox.setValidationPattern(Pattern.compile("^[a-zA-Z ]{0,30}$"));
			kullanıcıOzellikleriPanel.addComponent(yetkiSeviyesiTextBox.withBorder(Borders.singleLine("Yetki Seviyesi")));
			
            TextBox okurYasiTextBox = new TextBox(new TerminalSize(40, 1));
            okurYasiTextBox.setValidationPattern(Pattern.compile("^[0-9]{0,10}$"));
			kullanıcıOzellikleriPanel.addComponent(okurYasiTextBox.withBorder(Borders.singleLine("Okur Yaşı")));
			
            TextBox TCNOTextBox = new TextBox(new TerminalSize(40, 1));
            TCNOTextBox.setValidationPattern(Pattern.compile("^[0-9]{0,11}$"));
			kullanıcıOzellikleriPanel.addComponent(TCNOTextBox.withBorder(Borders.singleLine("T.C. NO")));
			
			//Yeni Kullanıcı Ekle Butonu
			kullanıcıOzellikleriPanel.addComponent(new Button("Yeni Kullanıcıyı Ekle", () -> SamdanMechanic.addNewUser(gui, kullaniciAdiTextBox.getText(), gercekAdiTextBox.getText(), sifreTextBox.getText(), yetkiSeviyesiTextBox.getText(), okurYasiTextBox.getText(), TCNOTextBox.getText())));
            
            

			new Label("Yeni Kitap: ").addTo(mainPanel);
            mainPanel.addComponent(kitapOzellikleriPanel);
            mainPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
            new Label("Yeni Kullanıcı: ").addTo(mainPanel);
            mainPanel.addComponent(kullanıcıOzellikleriPanel);
            
            
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
