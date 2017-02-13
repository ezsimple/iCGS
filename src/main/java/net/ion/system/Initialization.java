package net.ion.system;

import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"elasticsearchTemplate"})
public class Initialization {
	
	// @Autowired private AuthorityService authSvc;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {
		// authSvc.initAuthority();
		logger.warn("초기화는 DependsOn(BeanName) 이후 실행됩니다.");
		// authSvc.initAuthority();
	}
	
//	@PreDestroy
//	public void destroy() {
//		logger.warn("이게 먼저인가?");
//      이거 충돌하네 ... ㄷㄷㄷ 
//	}

}
