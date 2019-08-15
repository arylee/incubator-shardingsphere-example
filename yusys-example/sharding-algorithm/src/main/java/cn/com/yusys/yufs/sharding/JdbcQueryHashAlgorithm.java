package cn.com.yusys.yufs.sharding;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcQueryHashAlgorithm implements PreciseShardingAlgorithm<String> {

    private static Logger logger = LoggerFactory.getLogger(JdbcQueryHashAlgorithm.class);

    private static DataSource dataSource;

    private static String sql;

    static {
        try {
            ClassLoader classLoader = JdbcQueryHashAlgorithm.class.getClassLoader();
            InputStream sqlInputStream = classLoader.getResourceAsStream("META-INF/JdbcQuery.sql");
            byte[] bytes = new byte[sqlInputStream.available()];
            sqlInputStream.read(bytes);
            sql = new String(bytes);
            InputStream configInputStream = classLoader.getResourceAsStream("META-INF/config-sharding.yaml");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = configInputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            dataSource = YamlShardingDataSourceFactory.createDataSource(output.toByteArray());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        ResultSet rs = null;
        String value = shardingValue.getValue();
        int size = availableTargetNames.size();
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
        String suffix = StrUtils.leftPaddingZero(Math.abs(shardingValue.getValue().hashCode() % size), size);
        for (String target : availableTargetNames) {
            if (target.endsWith(suffix)) {
                return target;
            }
        }
        throw new UnsupportedOperationException();
    }

}
