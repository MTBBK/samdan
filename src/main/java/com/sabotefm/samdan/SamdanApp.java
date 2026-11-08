package com.sabotefm.samdan;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class SamdanApp {
    public static void main(String[] args) {
        try {
            // Initialize terminal and screen buffers
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Create window-based text GUI manager
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            // Create main application window
            BasicWindow window = new BasicWindow("Şamdan Kütüphane Yönetim Sistemi");
            
            Panel contentPanel = new Panel(new GridLayout(1));
            contentPanel.addComponent(new Label("Hoş Geldiniz! Şamdan TUI başarıyla başlatıldı."));
            
            // Exit button to close the app safely
            contentPanel.addComponent(new Button("Çıkış", window::close));

            window.setComponent(contentPanel);

            // Display the window and wait for interaction
            gui.addWindowAndWait(window);

            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
