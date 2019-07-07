package com.vidyo.service.configuration;

import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import org.junit.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class ExternalDataTypeEnumTest {

    @Test
    public void processInvalidExtDataType(){
        int sanitizedExtDataType = ExternalDataTypeEnum.validateExtDataType(4343);
        assertEquals(ExternalDataTypeEnum.VIDYO.ordinal(), sanitizedExtDataType);
    }

    @Test
    public void processStringArgEpic(){
        int sanitizedExtDataType = ExternalDataTypeEnum.validateExtDataType("1");
        assertEquals(ExternalDataTypeEnum.EPIC.ordinal(), sanitizedExtDataType);
    }

    @Test
    public void processStringArgTytoCare(){
        int sanitizedExtDataType = ExternalDataTypeEnum.validateExtDataType("2");
        assertEquals(ExternalDataTypeEnum.TYTOCARE.ordinal(), sanitizedExtDataType);
    }
    
    @Test
    public void processStringArg_outofRangeValue(){
        int sanitizedExtDataType = ExternalDataTypeEnum.validateExtDataType("13232");
        assertEquals(ExternalDataTypeEnum.VIDYO.ordinal(), sanitizedExtDataType);
    }

    @Test
    public void processStringArg_incorrectInput(){
        int sanitizedExtDataType = ExternalDataTypeEnum.validateExtDataType("bla-bla");
        assertEquals(ExternalDataTypeEnum.VIDYO.ordinal(), sanitizedExtDataType);
    }

    @Test
    public void processStringArg_nullInput(){
        int sanitizedExtDataType = ExternalDataTypeEnum.validateExtDataType(null);
        assertEquals(ExternalDataTypeEnum.VIDYO.ordinal(), sanitizedExtDataType);
    }
    
    @Test
    public void checkInvalidExtDataType() {
    	assertFalse(ExternalDataTypeEnum.isValidEpicExtDataType("0"));
    	assertFalse(ExternalDataTypeEnum.isValidEpicExtDataType("2"));
        assertFalse(ExternalDataTypeEnum.isValidEpicExtDataType("test"));
    }
    
    @Test
    public void checkValidExtDataType() {
        assertTrue(ExternalDataTypeEnum.isValidEpicExtDataType("1"));
    }
}
