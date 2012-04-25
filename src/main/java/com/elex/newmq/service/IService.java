package com.elex.newmq.service;

import java.io.IOException;

import org.apache.commons.configuration.HierarchicalConfiguration;

public interface IService {
	void init(HierarchicalConfiguration configure);
	void start() throws IOException;
	void stop() throws IOException;
	void reload();
}
