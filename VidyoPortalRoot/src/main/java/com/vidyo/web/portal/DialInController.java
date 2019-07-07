package com.vidyo.web.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.dialin.CountryService;
import com.vidyo.service.dialin.TenantDialInService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Country;
import com.vidyo.bo.DialInCountry;
import com.vidyo.bo.Room;
import com.vidyo.bo.TenantDialInCountry;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author ysakurikar
 *
 */
@Controller
public class DialInController {

	@Autowired
	TenantDialInService tenantDialInService;

	@Autowired
	CountryService countryService;

	@Autowired
	ISystemService system;

	@Autowired
	IRoomService roomService;

	@RequestMapping(value = "/dial/*", method = POST )
	public ModelAndView handleRequestWrapper(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return handleRequest(request, response);
	}

	@RequestMapping(value = "/dial/*", method = GET )
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String requestURI = request.getRequestURI();
		String roomKey = null;
		if (requestURI != null) {
			roomKey = requestURI.substring(requestURI.indexOf("/dial/") + 6);
		}
		// Check if the meeting id is valid
		if (roomKey == null) {
			return new ModelAndView("international_dial_in", "model", model);
		}

		Room roomDetails = roomService.getRoomForKey(roomKey);
		if (roomDetails == null) {
            response.sendError(404, "invalidroom");
			return null;
		}
		if (!roomService.areGuestAllowedToThisRoom()) {
            response.sendError(404, "guestnotallowed");
            return null;
		}
		model.put("roomName", roomDetails.getDisplayName());
		if (roomDetails.getRoomExtNumber().length() > 0) {
			model.put("meetingId", roomDetails.getRoomExtNumber());
		}
		
		if (StringUtils.isNotBlank(roomDetails.getRoomPIN()) ){
			model.put("pin", roomDetails.getRoomPIN());
		}
		List<TenantDialInCountry> tenantDialInCountries = tenantDialInService
				.getTenantDialInCounties(TenantContext.getTenantId());
		if (tenantDialInCountries == null || tenantDialInCountries.size() == 0) {
			tenantDialInCountries = tenantDialInService.getTenantDialInCounties(0);
		}

		if (tenantDialInCountries != null && tenantDialInCountries.size() > 0) {

			Configuration configuration = system.getConfiguration("COUNTRYFLAG_IMAGE_PATH");
			final String imagePath = configuration != null
					&& StringUtils.isNotBlank(configuration.getConfigurationValue())
							? configuration.getConfigurationValue() : "/upload/";

			// If there are any coutries, iterate over and make the group by
			// on country for numbers
			Map<Country, List<TenantDialInCountry>> tenantDialInCountriesMap = tenantDialInCountries.stream()
					.collect(Collectors.groupingBy(t -> t.getCountry()));

			// Sorting the countries list is ascending order
			Map<Country, List<TenantDialInCountry>> sortedTenantDialInCountriesMap = new LinkedHashMap<>();
			tenantDialInCountriesMap.entrySet()
				.stream()
				.sorted((t1,t2) -> t1.getKey().getName().compareTo(t2.getKey().getName()))
				.forEachOrdered(cn -> sortedTenantDialInCountriesMap.put(cn.getKey(), cn.getValue()));
				
				
			List<DialInCountry> dialInNumbers = new ArrayList<>();
			sortedTenantDialInCountriesMap.forEach((country, tenantDialIns) -> {
				DialInCountry dialInCountry = new DialInCountry();

				dialInCountry.setCountryId(country.getCountryID());
				dialInCountry.setCountryName(country.getName());
				dialInCountry.setCountryFlagPath(imagePath + country.getFlagFileName());
				dialInCountry.setDialinLabelToNumberMap(tenantDialIns.stream().collect(Collectors.toMap(
						td -> country.getPhoneCode() +td.getTenantDialInCountryPK().getDialInNumber(),
						td -> (td.getDialInLabel() != null ? td.getDialInLabel():""))));
				dialInNumbers.add(dialInCountry);
			});
			model.put("list", dialInNumbers);
		}

		String logo = PortalUtils.prepareLogo(this.system);
        model.put("logoUrl", logo);
		return new ModelAndView("international_dial_in", "model", model);
	}
}
