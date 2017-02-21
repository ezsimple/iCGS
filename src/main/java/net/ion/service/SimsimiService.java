package net.ion.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.ion.utils.ProxyUtils;

@Service
public class SimsimiService {

	protected Log logger = LogFactory.getLog(SimsimiService.class);

	public String exec(final String mesg) throws Exception {
		if (StringUtils.isEmpty(mesg)) {
			logger.warn("mesg is Empty");
			return null;
		}
		String url = "http://api.simsimi.com/request.p";
		final String key = "ec69c1ea-c941-4770-938e-0d5808f0f877";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("key", key);
		param.put("ft", "1.0");
		param.put("lc", "ko");
		param.put("text", URLEncoder.encode(mesg, "UTF-8"));
		String res = ProxyUtils.getText(url, param, "response");
		logger.debug(res);
		return res;
	}

}
