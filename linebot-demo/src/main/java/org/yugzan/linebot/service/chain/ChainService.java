package org.yugzan.linebot.service.chain;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.yugzan.linebot.service.action.AbstractAction;

/**
 * @author yongzan
 * @date 2018/04/10 
 */
public abstract class ChainService<E extends AbstractAction<T>, T> {

	@Autowired
	protected List<E> chain;

	@PostConstruct
	public void init() {
		Collections.sort(chain, AnnotationAwareOrderComparator.INSTANCE);
	}

	public void executeChain(T context) {
		chain.stream()
		.filter(chainElement -> chainElement.excute(context) )
		.findFirst();
	}
}
