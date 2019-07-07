package com.vidyo.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static com.vidyo.validators.TytoIdValidator.ALLOWED_PATTERN;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import javax.servlet.http.HttpServletRequest;


@RunWith(MockitoJUnitRunner.class)
public class TytoIdValidatorTest {

    @Test
    public void isValidVisit_success() {
        String identifier = "JKJKJKjaskdjkajsd8@asjdasd:daa.";
        assertTrue(TytoIdValidator.isValid(identifier, ALLOWED_PATTERN));
    }

    @Test
    public void isValidVisit_failed_due_invalid_chars() throws Exception {
        String identifier = "JKj1!$";
        assertFalse(TytoIdValidator.isValid(identifier, ALLOWED_PATTERN));
    }

    @Test
    public void truncateStationId() {
        String raw = "45007eee-7f26-4522-9e83-a67f5b4d0e5b-123456789";
        String truncated = TytoIdValidator.truncateStationGUID(raw);
        assertEquals("7f26-4522-9e83-a67f5b4d0e5b-123456789", truncated);
        assertEquals("7f26-4522-9e83-a67f5b4d0e5b-123456789".length(), truncated.length());
    }
}

