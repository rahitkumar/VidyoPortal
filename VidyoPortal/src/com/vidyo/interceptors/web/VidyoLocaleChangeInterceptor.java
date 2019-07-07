/**
 * 
 */
package com.vidyo.interceptors.web;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.vidyo.bo.Language;
import com.vidyo.service.ISystemService;

/**
 * @author Ganesh
 * 
 */
public class VidyoLocaleChangeInterceptor extends LocaleChangeInterceptor {

	/**
	 * 
	 */
	/** Logger for this class and subclasses */
	protected Logger logger = LoggerFactory.getLogger(VidyoLocaleChangeInterceptor.class.getName());

	/**
	 * 
	 */
	private ISystemService systemService;

	/**
	 * 
	 */
	private List<Language> langs = null;

	/**
	 * 
	 */
	private Language sysLang = null;

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		String newLocale = request.getParameter(getParamName());
		if (newLocale != null) {
			LocaleResolver localeResolver = RequestContextUtils
					.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException(
						"No LocaleResolver found: not in a DispatcherServlet request?");
			}
			boolean isSupported = isSupported(newLocale);
			if (!isSupported) {
				if (sysLang == null) {
					if (logger.isInfoEnabled())
						logger.info("System Language holder is null, loading it now");
					sysLang = systemService.getSystemLang();
				}
				newLocale = sysLang.getLangCode();
			}
			localeResolver.setLocale(request, response,
					StringUtils.parseLocaleString(newLocale));
		}
		// Proceed in any case.
		return true;
	}

	/**
	 * Checks whether the extracted locale is supported or not.
	 * 
	 * @param localeString
	 *            locale as string value
	 * @return TRUE/FALSE
	 */
	protected boolean isSupported(String localeString) {
		// Validate with the system supported locales
		boolean isSupported = false;
		if (localeString == null) {
			return isSupported;
		}

		if (langs == null) {
			if (logger.isInfoEnabled())
				logger.info("Languages holder is null, loading it now");
			langs = systemService.getLanguages();
		}

		for (Language language : langs) {
			if (language.getLangCode().equalsIgnoreCase(localeString)) {
				isSupported = true;
				break;
			}
		}
		return isSupported;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
