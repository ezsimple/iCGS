package net.ion.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.ion.entity.BotMessage;
import net.ion.entity.ChatMessage;
import net.ion.utils.ProxyUtils;

@Controller
public class ChatController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
    @MessageMapping("/hello/{userId}")
    @SendTo("/topic/greetings")
    public BotMessage greeting(ChatMessage message) throws Exception {

		String url = "http://api.simsimi.com/request.p";
		final String key = "ec69c1ea-c941-4770-938e-0d5808f0f877";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("key", key);
		param.put("text", message.getName());
		param.put("ft", "1.0");
		param.put("lc", "ko");
		String res = ProxyUtils.getText(url, param, "response");
		logger.debug(res);
        return new BotMessage(message.getName(), res);
    }

}
