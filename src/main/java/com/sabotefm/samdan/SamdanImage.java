package com.sabotefm.samdan;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;


//Test Script For Image Rendering

// /assets/images/image.png

public class SamdanImage {
    public static void main(String[] args) {
        Screen screen = null;
        try {
            Terminal terminal = new DefaultTerminalFactory().setForceTextTerminal(true).createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            
            TextGraphics tg = screen.newTextGraphics();
            
            File imgFile = new File("assets/images/image.png");
            BufferedImage originalImage = ImageIO.read(imgFile);

            //Scale width to fit terminal
            int targetWidth = 60;
            int targetHeight = (originalImage.getHeight() * targetWidth) / originalImage.getWidth();
            
            Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage image = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            int offsetX = 2;
            int offsetY = 2;

            //Draw Image
            for (int y = 0; y < image.getHeight(); y += 2) {
                for (int x = 0; x < image.getWidth(); x++) {
                    
                    int topRgb = image.getRGB(x, y);
                    boolean topTrans = ((topRgb >> 24) & 0xFF) < 128;
                    TextColor topColor = getAdjustedColor(topRgb);

                    boolean bottomTrans = true;
                    TextColor bottomColor = TextColor.ANSI.DEFAULT;
                    if (y + 1 < image.getHeight()) {
                        int botRgb = image.getRGB(x, y + 1);
                        bottomTrans = ((botRgb >> 24) & 0xFF) < 128;
                        bottomColor = getAdjustedColor(botRgb);
                    }

                    if (topTrans && bottomTrans) {
                        continue; 
                    } else if (!topTrans && !bottomTrans) {
                        tg.setForegroundColor(topColor);
                        tg.setBackgroundColor(bottomColor);
                        tg.putString(offsetX + x, offsetY + (y / 2), "\u2580");
                    } else if (!topTrans && bottomTrans) {
                        tg.setForegroundColor(topColor);
                        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                        tg.putString(offsetX + x, offsetY + (y / 2), "\u2580"); 
                    } else { 
                        tg.setForegroundColor(bottomColor);
                        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                        tg.putString(offsetX + x, offsetY + (y / 2), "\u2584"); 
                    }
                }
            }
                        
            //5x5 words
            String[] S = {
                "#### ",
                "#    ",
                "#### ",
                "   # ",
                "#### "
            };
            String[] A = {
                " ##  ",
                "#  # ",
                "#### ",
                "#  # ",
                "#  # "
            };
            String[] M = {
                "#   #",
                "## ##",
                "# # #",
                "#   #",
                "#   #"
            };
            String[] D = {
                "###  ",
                "#  # ",
                "#  # ",
                "#  # ",
                "###  "
            };
            String[] N = {
                "#   #",
                "##  #",
                "# # #",
                "#  ##",
                "#   #"
            };

            String[][] samdanSprites = { S, A, M, D, A, N };

            int textX = offsetX + image.getWidth() + 4;
            int textY = offsetY + (image.getHeight() / 4) - 2; 
            if (textY < 0) textY = 0;

            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.setBackgroundColor(TextColor.ANSI.DEFAULT);

            for (String[] letterSprite : samdanSprites) {
                for (int row = 0; row < letterSprite.length; row++) {
                    for (int col = 0; col < letterSprite[row].length(); col++) {
                        if (letterSprite[row].charAt(col) == '#') {
                            tg.putString(textX + col, textY + row, "\u2588");
                        }
                    }
                }
                textX += letterSprite[0].length() + 1;
            }
            screen.refresh();
            
            KeyStroke key;
            do {
                key = screen.readInput();
            } while (key != null && key.getKeyType() != KeyType.Escape && key.getKeyType() != KeyType.EOF);

        } catch (Exception e) {
            if (screen != null) {
                try { screen.stopScreen(); } catch (Exception ignored) {}
            }
            System.err.println("LANTERNA Error");
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try { screen.stopScreen(); } catch (Exception ignored) {}
            }
        }
    }

    private static TextColor getAdjustedColor(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        if (r < 50 && g < 50 && b < 50) {
            return new TextColor.RGB(255, 255, 255);
        }
        return new TextColor.RGB(r, g, b);
    }
}
