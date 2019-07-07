package com.vidyo.framework.security.htmlcleaner;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;

public class HtmlEscapingObjectMapper extends ObjectMapper {

    public HtmlEscapingObjectMapper() {
        super();
        this.getJsonFactory().setCharacterEscapes(new CustomCharacterEscapes());
    }

    public class CustomCharacterEscapes extends CharacterEscapes {
        private final int[] asciiEscapes;

        public CustomCharacterEscapes() {
            // start with set of characters known to require escaping (double-quote, backslash etc)
            asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
            // and force escaping of a few others:
            asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
        }
        @Override
        public int[] getEscapeCodesForAscii() {
            return asciiEscapes;
        }

        @Override
        public SerializableString getEscapeSequence(int ch) {
            if (ch == '<' || ch == '>' || ch == '&' || ch == '"' || ch == '\'') {
                String escaped = StringEscapeUtils.escapeHtml(Character.toString((char) ch));
                return new SerializedString(escaped);
            }
            return new SerializedString(Character.toString((char) ch));
        }
    }

}