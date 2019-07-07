package  com.vidyo.utils.room;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.IRoomDao;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.framework.context.TenantContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import static org.mockito.Mockito.*;

public class RoomExtGeneratorTest {

    @InjectMocks
    private RoomExtnGenerator roomExtnGenerator = new RoomExtnGenerator();

    @Mock
    private TenantConfigurationDao tenantConfigurationDao;

    @Mock
    private IRoomDao roomDao;

    @Mock
    private TenantConfiguration tenantConfiguration;

    @BeforeMethod(alwaysRun = true)
    public void injectDoubles() {
        MockitoAnnotations.initMocks(this); // This could be pulled up into a shared base class
    }


    @Test
    public void generateRandomRoomExtnSuccessScenario(){
        TenantContext.setTenantId(4);
        when(tenantConfigurationDao.getTenantConfiguration(any(Integer.class))).thenReturn(tenantConfiguration);
        when(tenantConfiguration.getExtnLength()).thenReturn(4);
        when(tenantConfiguration.getRoomCountThreshold()).thenReturn(10);
        when(tenantConfiguration.getRoomCountThreshold()).thenReturn(40);
        when(roomDao.getRoomCountByExtnLength(any(Integer.class),any(Integer.class))).thenReturn(300);
        String randomNumber = roomExtnGenerator.generateRandomRoomExtn(4, "34");
        
        assert (randomNumber.length() == 4);

        verify(roomDao, times(1)).isRoomExistForRoomExtNumber(anyString(),anyInt());
        verify(tenantConfigurationDao, never()).updateTenantConfiguration(anyInt(),any(TenantConfiguration.class));
        verify(roomDao, never()).clearRoomCounterCache(anyInt());
    }


    @Test
    public void generateRandomRoomExtnExceedingThreshold(){
        TenantContext.setTenantId(4);
        when(tenantConfigurationDao.getTenantConfiguration(any(Integer.class))).thenReturn(tenantConfiguration);
        when(tenantConfiguration.getExtnLength()).thenReturn(4);
        when(tenantConfiguration.getRoomCountThreshold()).thenReturn(40);
        when(roomDao.getRoomCountByExtnLength(any(Integer.class),any(Integer.class))).thenReturn(997788798);
        String randomNumber = roomExtnGenerator.generateRandomRoomExtn(4, "34");

        assertEquals (randomNumber.length(), 5);

        verify(roomDao, times(1)).isRoomExistForRoomExtNumber(anyString(),anyInt());
        verify(tenantConfigurationDao, times(1)).updateTenantConfiguration(anyInt(),any(TenantConfiguration.class));
        verify(roomDao, times(1)).clearRoomCounterCache(anyInt());
    }

    @Test
    public void generateRandomRoomExtnCheckSettingExtensionLength(){
        TenantContext.setTenantId(4);
        when(tenantConfiguration.getExtnLength()).thenReturn(4);
        when(tenantConfigurationDao.getTenantConfiguration(any(Integer.class))).thenReturn(tenantConfiguration);
        when(tenantConfiguration.getRoomCountThreshold()).thenReturn(40);
        when(roomDao.getRoomCountByExtnLength(any(Integer.class),any(Integer.class))).thenReturn(997788798);
        String randomNumber = roomExtnGenerator.generateRandomRoomExtn(4, "34");

        assertEquals (randomNumber.length() , 5);

        verify(roomDao, times(1)).isRoomExistForRoomExtNumber(anyString(),anyInt());
        verify(tenantConfigurationDao, times(1)).updateTenantConfiguration(anyInt(),any(TenantConfiguration.class));
        verify(roomDao, times(1)).clearRoomCounterCache(anyInt());
        verify(tenantConfiguration, times(1)).setExtnLength(tenantConfiguration.getExtnLength());
    }

    @Test
    public void generateRandomRoomExtnwithTenDigitExtension(){
        TenantContext.setTenantId(4);
        when(tenantConfiguration.getExtnLength()).thenReturn(10);
        when(tenantConfigurationDao.getTenantConfiguration(any(Integer.class))).thenReturn(tenantConfiguration);
        when(tenantConfiguration.getRoomCountThreshold()).thenReturn(40);
        when(roomDao.getRoomCountByExtnLength(any(Integer.class),any(Integer.class))).thenReturn(997788798);
        String randomNumber = roomExtnGenerator.generateRandomRoomExtn(4, "34");

        assertEquals (randomNumber.length() , 10);
        assertEquals(tenantConfiguration.getExtnLength(), 10);
        verify(roomDao, times(1)).isRoomExistForRoomExtNumber(anyString(),anyInt());
        verify(tenantConfigurationDao, never()).updateTenantConfiguration(anyInt(),any(TenantConfiguration.class));
        verify(roomDao, never()).clearRoomCounterCache(anyInt());
        verify(tenantConfiguration, never()).setExtnLength(anyInt());
    }

    @Test
    public void generateRandomRoomExtnwithThirteenDigitExtension(){
        TenantContext.setTenantId(4);
        when(tenantConfiguration.getExtnLength()).thenReturn(13);
        when(tenantConfigurationDao.getTenantConfiguration(any(Integer.class))).thenReturn(tenantConfiguration);
        when(tenantConfiguration.getRoomCountThreshold()).thenReturn(40);
        when(roomDao.getRoomCountByExtnLength(any(Integer.class),any(Integer.class))).thenReturn(997788798);
        String randomNumber = roomExtnGenerator.generateRandomRoomExtn(4, "34");

        assertEquals (randomNumber.length() , 13);
        assertEquals(tenantConfiguration.getExtnLength(), 13);
        verify(roomDao, times(1)).isRoomExistForRoomExtNumber(anyString(),anyInt());
        verify(tenantConfigurationDao, never()).updateTenantConfiguration(anyInt(),any(TenantConfiguration.class));
        verify(roomDao, never()).clearRoomCounterCache(anyInt());
        verify(tenantConfiguration, never()).setExtnLength(anyInt());
    }


}
