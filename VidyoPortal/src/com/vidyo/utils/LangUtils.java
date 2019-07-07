package com.vidyo.utils;

import java.util.Locale;

public class LangUtils {

	public static int getLangId(String langStr){
		return getLangId(langStr, true);
	}
	
    public static int getLangId(String langStr, boolean useDefaultLang){
        if(langStr.equalsIgnoreCase("en") || langStr.equalsIgnoreCase("English") || langStr.toLowerCase().startsWith("en_")) {
            return 1;
        }
        else if(langStr.equalsIgnoreCase("fr") || langStr.equalsIgnoreCase("French") || langStr.toLowerCase().startsWith("fr_")) {
            return 2;
        }
        else if(langStr.equalsIgnoreCase("ja") || langStr.equalsIgnoreCase("Japanese") || langStr.toLowerCase().startsWith("ja_")) {
            return 3;
        }
        else if(langStr.equalsIgnoreCase("zh_CN") || langStr.equalsIgnoreCase("Chinese") || langStr.toLowerCase().startsWith("zh_cn")) {
            return 4;
        }
        else if(langStr.equalsIgnoreCase("es") || langStr.equalsIgnoreCase("Spanish") || langStr.toLowerCase().startsWith("es_")) {
            return 5;
        }
        else if(langStr.equalsIgnoreCase("it") || langStr.equalsIgnoreCase("Italian") || langStr.toLowerCase().startsWith("it_")) {
            return 6;
        }
        else if(langStr.equalsIgnoreCase("de") || langStr.equalsIgnoreCase("German") || langStr.toLowerCase().startsWith("de_")) {
            return 7;
        }
        else if(langStr.equalsIgnoreCase("ko") || langStr.equalsIgnoreCase("Korean") || langStr.toLowerCase().startsWith("ko_")) {
            return 8;
        }
        else if(langStr.equalsIgnoreCase("pt") || langStr.equalsIgnoreCase("Portuguese") || langStr.toLowerCase().startsWith("pt_")) {
            return 9;
        }
        else if(langStr.equalsIgnoreCase("fi") || langStr.equalsIgnoreCase("finnish") || langStr.toLowerCase().startsWith("fi_")) {
            return 11;
        }
        else if(langStr.equalsIgnoreCase("pl") || langStr.equalsIgnoreCase("polish") || langStr.toLowerCase().startsWith("pl_")) {
            return 12;
        }
        else if(langStr.equalsIgnoreCase("zh_TW") || langStr.equalsIgnoreCase("taiwanese") || langStr.toLowerCase().startsWith("zh_tw")) {
            return 13;
        }
        else if(langStr.equalsIgnoreCase("th") || langStr.equalsIgnoreCase("thai") || langStr.toLowerCase().startsWith("th_")) {
            return 14;
        }
        else if(langStr.equalsIgnoreCase("ru") || langStr.equalsIgnoreCase("russian") || langStr.toLowerCase().startsWith("ru_")) {
            return 15;
        }
        else if(langStr.equalsIgnoreCase("tr") || langStr.equalsIgnoreCase("turkish") || langStr.toLowerCase().startsWith("tr_")) {
            return 16;
        }
        else {
        	if (useDefaultLang) return 1; else return -1;
        }
    }

    public static Locale getLocaleByLangID(int langID) {
        switch (langID) {
            case 1:
                return Locale.ENGLISH;
            case 2:
                return Locale.FRENCH;
            case 3:
                return Locale.JAPANESE;
            case 4:
                return Locale.SIMPLIFIED_CHINESE;
            case 5:
                return new Locale("es");
            case 6:
                return Locale.ITALIAN;
            case 7:
                return Locale.GERMAN;
            case 8:
                return Locale.KOREAN;
            case 9:
                return new Locale("pt");
            case 10:        //10 means System Language, caller should avoid to calling this static method, return English as placeholder 
                return Locale.ENGLISH;
            case 11:
                return new Locale("fi");
            case 12:
                return new Locale("pl");
            case 13:
                return Locale.TAIWAN;
            case 14:
                return new Locale("th");
            case 15:
                return new Locale("ru");
            case 16:
                return new Locale("tr");
            default:
                return Locale.ENGLISH;
        }
    }

    public static boolean isPortalSupportedLang(Locale locale) {
        if (locale == null) {
            return false;
        }

        String lang = locale.getLanguage();

        return (Locale.ENGLISH.getLanguage().equals(lang) ||
                Locale.FRENCH.getLanguage().equals(lang) ||
                Locale.JAPANESE.getLanguage().equals(lang) ||
                Locale.SIMPLIFIED_CHINESE.equals(locale) ||
                "es".equals(lang) ||
                Locale.ITALIAN.getLanguage().equals(lang) ||
                Locale.GERMAN.getLanguage().equals(lang) ||
                Locale.KOREAN.getLanguage().equals(lang) ||
                "pt".equals(lang) ||
                "fi".equals(lang) ||
                "pl".equals(lang) ||
                Locale.TAIWAN.equals(locale) ||
                "th".equals(lang) ||
                "ru".equals(lang) ||
                "tr".equals(lang)
        );
    }
}
