/**
 * 
 */
package com.vidyo.service.dialin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.Country;
import com.vidyo.bo.TenantDialInCountry;
import com.vidyo.bo.TenantDialInCountryPK;
import com.vidyo.bo.tenant.Tenant;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.TenantDialInDao;
import com.vidyo.service.transaction.TransactionService;

/**
 * @author ysakurikar
 *
 */
@Service (value="tenantDialInService")
public class TenantDialInServiceImpl implements TenantDialInService {

	@Autowired
	private TenantDialInDao tenantDialInDao;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private TransactionService transactionService;
	
	

	/**
	 * @param tenantDialInDao the tenantDialInDao to set
	 */
	public void setTenantDialInDao(TenantDialInDao tenantDialInDao) {
		this.tenantDialInDao = tenantDialInDao;
	}

	/**
	 * Get Tenant dial in country list
	 * @param tenantId
	 * @return
	 */
	@Override
	public List<TenantDialInCountry> getTenantDialInCounties (int tenantId){
		List<TenantDialInCountry> list = null;
		try {
			list = tenantDialInDao.getTenantDialInCounties(tenantId);
		} catch (RuntimeException re){
			// ignore it as the list is empty  
		}
		
		return list;
	}
	
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * Delete the TenantDialInCountry
	 * @param tenantId
	 */
	@Override
	@Transactional
	public boolean deleteTenantDialInCountryForTenant( int tenantId) {
		return tenantDialInDao.deleteTenantDialInCountryForTenant(tenantId);
	}
	
	/**
	 * Save all the dial in numbers for the Tenant
	 * @param dialInNumbers
	 * @param tenantId
	 * @return
	 */
	@Override
	@Transactional
	public List<TenantDialInCountry> saveUpdateDialInNumbers (String dialInNumbers, int tenantId){
		// First delete all the countries for tenant
		tenantDialInDao.deleteTenantDialInCountryForTenant(tenantId);
		List<TenantDialInCountry> tenantDialInCountries = getDialInCountryList(dialInNumbers, tenantId);
		List<TenantDialInCountry> savedTenantDialIn = tenantDialInDao.saveAllTenantDialInCountries(tenantId, tenantDialInCountries);
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Dial-In Numbers Add/Update");
		transactionHistory.setTransactionParams("dial-in numbers - " + dialInNumbers + " Tenant Id - "+ tenantId);
		transactionHistory.setTransactionResult("SUCCESS");
		transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory);
		return savedTenantDialIn;
	}

    /**
     * Get the Dialin country list from delimited string
 	 * The dial in numbers contains records delimited with '|' 
	 * and each record contains columns delimited with ':'
     * @param dialInNumbers
     * @param tenantId
     * @return
     */
    private List<TenantDialInCountry> getDialInCountryList(String dialInNumbers, int tenantId){
    	com.vidyo.bo.tenant.Tenant tenant = getTenantDetails(tenantId);
		// Copy the old tenant structure to new structure
		
    	List<Country> countries = countryService.getAllCountries();

		// Now slipt the records and insert them.
		final List<TenantDialInCountry> tenantDialInCountries = new ArrayList<>();
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
					
					tenantDialInCountry.setTenant(tenant);
					Country country = countries.stream().filter(c -> c.getCountryID() == Integer.parseInt(tenantDialInCountryRecord[0])).findAny().orElse(null);

					// get the dialin label if any
					tenantDialInCountry.setDialInLabel(tenantDialInCountryRecord.length > 2 ? tenantDialInCountryRecord[2]:null);
					tenantDialInCountry.setTenantDialInCountryPK(tenantDialInCountryPK);
					tenantDialInCountry.setCountry(country);
					
					tenantDialInCountries.add(tenantDialInCountry);
				});

		return tenantDialInCountries;
    }

	/**
	 * Returns Tenant details by Tenant id.
	 *
	 * @param tenantId
	 */
	@Override
	public Tenant getTenantDetails(int tenantId) {
		return this.tenantDialInDao.getTenantDetails(tenantId);
	}

}
