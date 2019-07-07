package com.vidyo.service.dialin;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Country;
import com.vidyo.bo.DialInCountry;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantDialInCountry;
import com.vidyo.bo.TenantDialInCountryPK;
import com.vidyo.db.CountryDao;
import com.vidyo.db.ITenantDao;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.db.TenantDialInDao;
import com.vidyo.db.repository.CountryRepository;
import com.vidyo.service.ITenantService;
import com.vidyo.service.TenantServiceImpl;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantDialInServiceImplTest {

	@SpringBeanByType
	private TenantDialInService tenantDialInService;

	@SpringBeanByType
	private ITenantService tenantService;

	@SpringBeanByType
	private CountryService countryService;

	private Mock<ITenantDao> mockTenantDao;
	private Mock<TenantConfigurationDao> mockTenantConfigurationDao;
	private Mock<CountryDao> mockCountryDao;
	private Mock<TenantDialInDao> mockTenantDialIndao;

	@Before
	public void initialize() {
		((TenantServiceImpl) tenantService).setDao(mockTenantDao.getMock());
		((TenantServiceImpl) tenantService).setTenantConfigurationDao(mockTenantConfigurationDao.getMock());
		((CountryServiceImpl) countryService).setCountryDao(mockCountryDao.getMock());
		((TenantDialInServiceImpl) tenantDialInService).setTenantDialInDao(mockTenantDialIndao.getMock());
	}

	@Test
	public void getTenantDialCountriesTest(){
		List<TenantDialInCountry> tenantDialInCounties = tenantDialInService.getTenantDialInCounties(1);

		assertTrue(tenantDialInCounties != null && tenantDialInCounties.size() >= 0);
	}

	@Test
	public void SaveTenantDialInCountries(){
		List<TenantDialInCountry> tenantDialInCounties = tenantDialInService.saveUpdateDialInNumbers(
				"1:2013338854:local|2:44433334343|3:45457672225223:tollfree|6:564568834534", 1);

		assertTrue(tenantDialInCounties != null && tenantDialInCounties.size() >= 0);
	}

    private List<TenantDialInCountry> getDialInCountryList(String dialInNumbers, int tenantId){

		// Now slipt the records and insert them.
		final List<TenantDialInCountry> tenantDialInCountries = new ArrayList<>();
		List<Country> countries = countryService.getAllCountries();
		Arrays.asList(dialInNumbers.split("\\|")).stream().
				forEach(tenantDialInCountryStr ->
				{
					String[] tenantDialInCountryRecord = tenantDialInCountryStr.split("\\:");
					TenantDialInCountry tenantDialInCountry = new TenantDialInCountry();
					TenantDialInCountryPK tenantDialInCountryPK = new TenantDialInCountryPK();
					// Get the country code
					tenantDialInCountryPK.setCountryId(Integer.parseInt(tenantDialInCountryRecord[0]));
					tenantDialInCountryPK.setTenantId(tenantId);
					// Get the dial in number
					tenantDialInCountryPK.setDialInNumber(tenantDialInCountryRecord[1]);
					Tenant tenant = tenantService.getTenant(tenantId);
					// Copy the old tenant structure to new structure
					com.vidyo.bo.tenant.Tenant tenantForDialIn = new com.vidyo.bo.tenant.Tenant();
					tenantForDialIn.setTenantId(tenantId);
					tenantForDialIn.setTenantName(tenant.getTenantName());
					tenantForDialIn.setTenantURL(tenant.getTenantURL());
					tenantForDialIn.setTenantPrefix(tenant.getTenantPrefix());
					tenantForDialIn.setDescription(tenant.getDescription());
					tenantForDialIn.setMobileLogin(tenant.getMobileLogin());
					tenantForDialIn.setScheduledRoomEnabled(tenant.getScheduledRoomEnabled());
					tenantForDialIn.setTenantWebRTCURL(tenant.getTenantWebRTCURL());
					tenantDialInCountry.setTenant(tenantForDialIn);
					Country country = countries.stream().filter(c -> c.getCountryID() == Integer.parseInt(tenantDialInCountryRecord[0])).findAny().orElse(null);

					// get the dialin label if any
					tenantDialInCountry.setDialInLabel(tenantDialInCountryRecord.length > 2 ? tenantDialInCountryRecord[2]:null);
					tenantDialInCountry.setTenantDialInCountryPK(tenantDialInCountryPK);
					tenantDialInCountry.setCountry(country);

					tenantDialInCountries.add(tenantDialInCountry);
				});

		return tenantDialInCountries;
    }
	@Test
	public void deleteTenantDialInCountriesForTenant(){
		boolean success = tenantDialInService.deleteTenantDialInCountryForTenant(1);

		assertTrue(!success);
	}

	@Test
	public void checkAllCountries(){
		List<Country> countries = countryService.getAllCountries();

		assertTrue(countries != null && countries.size() >= 0);
	}

	@Test
	public void callTenantDialInCountries() {
	List<DialInCountry> dialInNumbers = new ArrayList<>();

	// Get the Dial in contries list
			List<TenantDialInCountry> tenantDialInCountries =  new ArrayList<>();
			Country country1 = new Country();
			country1.setCountryID(1);
			country1.setName("United States");
			country1.setPhoneCode(1);
			country1.setFlagFileName("USA.png");
			Country country2 = new Country();
			country2.setCountryID(2);
			country2.setName("United Kingdom");
			country2.setPhoneCode(44);
			country2.setFlagFileName("United Kingdom.png");
			TenantDialInCountry tenantDialInCountry = new TenantDialInCountry();
			tenantDialInCountry.setCountry(country1);
			TenantDialInCountryPK tenantDialInCountryPK = new TenantDialInCountryPK();
			tenantDialInCountryPK.setCountryId(1);
			tenantDialInCountryPK.setDialInNumber("34534544");
			tenantDialInCountry.setTenantDialInCountryPK(tenantDialInCountryPK);
			tenantDialInCountry.setDialInLabel("Toll Free");
			tenantDialInCountries.add(tenantDialInCountry);
			TenantDialInCountry tenantDialInCountry1 = new TenantDialInCountry();
			tenantDialInCountry1.setCountry(country2);
			TenantDialInCountryPK tenantDialInCountryPK1 = new TenantDialInCountryPK();
			tenantDialInCountryPK1.setCountryId(2);
			tenantDialInCountryPK1.setDialInNumber("77734444");
			tenantDialInCountry1.setTenantDialInCountryPK(tenantDialInCountryPK1);
			tenantDialInCountry1.setDialInLabel("Toll Free");
			tenantDialInCountries.add(tenantDialInCountry1);
			TenantDialInCountry tenantDialInCountry2 = new TenantDialInCountry();
			tenantDialInCountry2.setCountry(country1);
			TenantDialInCountryPK tenantDialInCountryPK2 = new TenantDialInCountryPK();
			tenantDialInCountryPK2.setCountryId(1);
			tenantDialInCountryPK2.setDialInNumber("9994545");
			tenantDialInCountry2.setTenantDialInCountryPK(tenantDialInCountryPK2);
			tenantDialInCountry2.setDialInLabel("Local");
			tenantDialInCountries.add(tenantDialInCountry2);
			TenantDialInCountry tenantDialInCountry3 = new TenantDialInCountry();
			tenantDialInCountry3.setCountry(country2);
			TenantDialInCountryPK tenantDialInCountryPK3 = new TenantDialInCountryPK();
			tenantDialInCountryPK3.setCountryId(2);
			tenantDialInCountryPK3.setDialInNumber("343432111");
			tenantDialInCountry3.setTenantDialInCountryPK(tenantDialInCountryPK3);
			tenantDialInCountry3.setDialInLabel("Toll Free");
			tenantDialInCountries.add(tenantDialInCountry3);

			if (tenantDialInCountries != null && tenantDialInCountries.size() > 0){

				final String imagePath = "/upload/";

				// If there are any coutries, iterate over and make the group by on country for numbers
				Map<Country, List<TenantDialInCountry>> tenantDialInCountriesMap = tenantDialInCountries.stream().collect(Collectors.groupingBy(t -> t.getCountry()));


				tenantDialInCountriesMap.forEach((country, tenantDialIns) -> {
					DialInCountry dialInCountry = new DialInCountry();

					dialInCountry.setCountryId(country.getCountryID());
					dialInCountry.setCountryName(country.getName());
					dialInCountry.setCountryFlagPath(imagePath+"/"+country.getFlagFileName());
					/*dialInCountry.setDialNoAndLabel(tenantDialIns.stream()
							.map(td -> (org.apache.commons.lang.StringUtils.isNotBlank(td.getDialInLabel()) ? (td.getDialInLabel()+" - "):"") + "+"+country.getPhoneCode() + td.getTenantDialInCountryPK().getDialInNumber())
							.collect(Collectors.toList()));*/

					dialInNumbers.add(dialInCountry);
				});
			}

			assertTrue(dialInNumbers != null && dialInNumbers.size() >= 0);
	}

}
