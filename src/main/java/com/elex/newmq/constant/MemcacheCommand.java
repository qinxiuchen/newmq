package com.elex.newmq.constant;

public enum MemcacheCommand {
	get, set, stats, add, delete;
	public static MemcacheCommand getCommand(String command){
		return valueOf(command.toLowerCase());
	}
}
