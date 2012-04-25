package com.elex.newmq;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang.StringUtils;
import com.elex.newmq.constant.MQConfig;
import com.elex.newmq.exception.NewMQStartUpException;
import com.elex.newmq.service.DBService;
import com.elex.newmq.service.DataSourceManager;
import com.elex.newmq.service.PersistenceService;
import com.elex.newmq.service.RemotingServer;
import com.elex.newmq.service.StatsService;


public class MQStartUp {

	private RemotingServer remotingServer;
	private DBService dbService;
	private DataSourceManager dataSourceManager;
	private PersistenceService persistenceService;
	private StatsService statsService;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//获取配置文件路径
			final String configFilePath = getConfigFilePath(args);
			//初始化配置实例
			final HierarchicalConfiguration configure = getConfigure(configFilePath);
			MQStartUp startUp = new MQStartUp();
			startUp.init(configure);
			startUp.start();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch(NewMQStartUpException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public MQStartUp(){
		this.remotingServer = new RemotingServer();
		this.dbService = new DBService();
		this.dataSourceManager = new DataSourceManager();
		this.persistenceService = new PersistenceService();
		this.statsService = new StatsService();
	}
	
	public void init(HierarchicalConfiguration configure){
		this.remotingServer.init(configure.configurationAt("remotingserver"));
		this.dbService.init(configure.configurationAt("dbservice"));
		this.dataSourceManager.init(configure.configurationAt("dbservice"));
		this.persistenceService.init(configure.configurationAt("persistenceservice"));
		this.statsService.init(configure.configurationAt("statsservice"));
	}
	
	/**
	 * 启动newmq
	 * @throws IOException 
	 */
	public void start() throws IOException{
		this.remotingServer.start();
	}
	
	/**
	 * 解析命令行参数，获取配置文件的路径
	 * @param args
	 * @return
	 * @throws NewMQStartUpException
	 */
	public static String getConfigFilePath(final String[] args) throws NewMQStartUpException{
		final Options options = new Options();
        final Option file = new Option("f", true, "Configuration file path");
        options.addOption(file);
        final CommandLineParser parser = new PosixParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        }
        catch (final ParseException e) {
            throw new NewMQStartUpException("Parse command line failed", e);
        }
        String configFilePath = null;
        if (line.hasOption("f")) {
            configFilePath = line.getOptionValue("f");
        }
        else {
        	System.out.println("u not define the config file path!use the default path now!");
        	configFilePath = MQConfig.DEFAULT_CONFIG_PATH;
        }
        if (StringUtils.isBlank(configFilePath)) {
            throw new NewMQStartUpException("Blank file path");
        }
		return configFilePath;
	}
	/**
	 * 获取一个配置文件操作实例
	 * @return
	 */
	public static HierarchicalConfiguration getConfigure(String configFilePath) throws ConfigurationException{
		HierarchicalConfiguration configure = new XMLConfiguration();
		configure.setDelimiterParsingDisabled(true);
		configure.setExpressionEngine(new XPathExpressionEngine());
		((XMLConfiguration)configure).load(configFilePath);
		return configure;
	}
	
	/**
	 * 返回一个memcache server实例，用于接收mc请求。
	 * @return
	 */
	protected final RemotingServer newRemotingServer(){
		return null;
	}
	
	/**
	 * 重定向stdout和stderr
	 */
	protected final void redirectStd(){
		
	}
	/**
	 * 从持久化文件中恢复未提交到数据库中的数据。
	 */
	protected final void replay(){
		
	}

}
