package cn.com.yusys.yufs.sharding;

import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class SingleColumnHashAlgorithm implements PreciseShardingAlgorithm<String> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		int size = availableTargetNames.size();
		String suffix = StrUtils.leftPaddingZero(Math.abs(shardingValue.getValue().hashCode() % size), size);
		for (String target : availableTargetNames) {
			if (target.endsWith(suffix)) {
				return target;
			}
		}
		throw new UnsupportedOperationException();
	}

}
