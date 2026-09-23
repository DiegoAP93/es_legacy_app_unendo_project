package com.eslegacy.admin.util;

import java.awt.*;
import java.io.InputStream;

public class FontManager {

    private static Font baskerville;

    private static Font loadBaseFont() {

        try {

            if (baskerville == null) {

                InputStream is = FontManager.class.getResourceAsStream("/fonts/baskerville-old-face.ttf");
                baskerville = Font.createFont(Font.TRUETYPE_FONT, is);
            }

            return baskerville;

        } catch (Exception e) {

            return new Font("Serif", Font.PLAIN, 14);
        }
    }
    
    public static Font title() {
        return loadBaseFont().deriveFont(Font.BOLD, 22f);
    }

    public static Font subtitle() {
        return loadBaseFont().deriveFont(Font.BOLD, 18f);
    }

    public static Font normal() {
        return loadBaseFont().deriveFont(Font.PLAIN, 14f);
    }

    public static Font button() {
        return loadBaseFont().deriveFont(Font.BOLD, 14f);
    }

    public static Font table() {
        return loadBaseFont().deriveFont(Font.PLAIN, 13f);
    }
}