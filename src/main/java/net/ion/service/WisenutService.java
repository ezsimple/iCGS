package net.ion.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.ion.utils.ProxyUtils;

@Service
public class WisenutService {

	private Logger logger = LoggerFactory.getLogger(WisenutService.class);
	final String url = "http://211.39.140.244:10081/dialog/";
	
	public String getWnSessionId() throws Exception {
		Map<String,String> parameterMap = new HashMap<String, String>();
		JSONObject data = ProxyUtils.getWiseBotResponse(url,parameterMap);
		String wnSessionId = data.getString("sessionId");
		logger.debug(wnSessionId);
		return wnSessionId;
	}

	public String exec(String mesg) throws Exception {

		if (StringUtils.isEmpty(mesg)) {
			logger.warn("mesg is Empty");
			return null;
		}

		String wnSessionId = this.getWnSessionId();

		Map<String,String> parameterMap = new HashMap<String, String>();
		parameterMap.put("sessionId", wnSessionId);
		parameterMap.put("msg", mesg);
		parameterMap.put("isRealCounselor", "n"); // n: 채팅봇과의 대화일 경우
		parameterMap.put("isCounselor", "n");     // n일 경우 고객이 전달한 메세지
		JSONObject data = ProxyUtils.getWiseBotResponse(url, parameterMap);
		String answer = data.getString("answer");
		logger.debug(answer);

		return answer;
	}
	
}