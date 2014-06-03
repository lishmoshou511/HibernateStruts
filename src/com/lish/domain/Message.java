package com.lish.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public class Message {
	@Setter @Getter
	private Integer id;
	@Setter @Getter
	private Date time;
	@Setter @Getter
	private String content;

	//对象
	@Setter @Getter
	private User sender;
	@Setter @Getter
	private User receiver;


}
