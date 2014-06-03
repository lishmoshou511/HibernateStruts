package com.lish.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public class User {
	@Setter @Getter
	private Integer id;

	@Setter @Getter
	private String password;

	@Setter @Getter
	private String name;

	//one-to-many
	@Setter @Getter
	private Set<Message> sendMessages;
	@Setter @Getter
	private Set<Message> receiveMessages;



}
