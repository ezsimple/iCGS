package net.ion.service;

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

	public String exec(String mesg) throws Exception {

		// 심심이가 "?"있는 질문에 대해 응답을 못하고 있음.
		// 예: http://api.simsimi.com/request.p?lc=ko&key=ec69c1ea-c941-4770-938e-0d5808f0f877&text=%EC%95%84%EC%9D%B4%EC%98%A8%EC%9D%80%3f
		if(mesg!=null && mesg.contains("?"))
			mesg = StringUtils.replace(mesg, "?", ""); 

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
		param.put("text", mesg);
		String res = ProxyUtils.getText(url, param, "response");
		logger.debug(res);
		return res;
	}

}
