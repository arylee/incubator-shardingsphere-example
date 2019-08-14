package cn.com.yusys.yufs.sharding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

public class SingleColumnHashAlgorithm2 implements ComplexKeysShardingAlgorithm<String> {

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
