package cn.com.yusys.yufs.sharding;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcQueryHashAlgorithm2 implements ComplexKeysShardingAlgorithm<String> {

	private static Logger logger = LoggerFactory.getLogger(JdbcQueryHashAlgorithm2.class);

	private static Object lock = new Object();

	private static DataSource dataSource;
	
	private static String sql = "SELECT cert_no FROM myjb_loan_detail WHERE contract_no = ?";

	public JdbcQueryHashAlgorithm2() {
		synchronized (lock) {
			if (dataSource == null) {
				try {
					dataSource = YamlShardingDataSourceFactory
							.createDataSource(new File("/home/dmp/sharding-proxy/conf/config-sharding.yaml"));					
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			ComplexKeysShardingValue<String> shardingValue) {
		String key = null;
		int count = 0;
		Iterator<String> keys = shardingValue.getColumnNameAndShardingValuesMap().keySet().iterator();
		while (keys.hasNext()) {
			key = keys.next();
			count++;
		}
		if (count == 1) {
			String value = shardingValue.getColumnNameAndShardingValuesMap().get(key).toArray()[0].toString();
			ResultSet rs = null;
			try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, value);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					value = rs.getString("cert_no");
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
			}
			String suffix = String.valueOf(Math.abs(value.hashCode() % availableTargetNames.size()));
			for (String target : availableTargetNames) {
				if (target.endsWith(suffix)) {
					List<String> list = new ArrayList<>();
					list.add(target);
					return list;
				}
			}
		}
		throw new UnsupportedOperationException();
	}

}
