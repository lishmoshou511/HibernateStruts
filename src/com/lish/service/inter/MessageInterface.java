package com.lish.service.inter;

import com.lish.domain.Message;

import java.util.List;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public interface MessageInterface extends BaseInterface{
	public List<Message> messages();
}
