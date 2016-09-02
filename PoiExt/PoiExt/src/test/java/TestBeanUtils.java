import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

public class TestBeanUtils {
	public static void main(String[] args) throws Exception {
	
		testArray();
	}
	
	public static void testMap() throws Exception{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("a", 1);
		map.put("b", 2);
		
		Map<String,Integer> map2 = new HashMap<String,Integer>();
		
		BeanUtils.populate(map2, map);
		
		Set<Entry<String, Integer>> entrySet = map2.entrySet();
		
		for (Entry<String, Integer> entry : entrySet) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
	}
	
	
	public static void testArray() throws Exception{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("a", 1);
		map.put("b", 2);
		
		List<Integer> list = new ArrayList<Integer>();
		
		BeanUtils.populate(list, map);
		
		for (Integer integer : list) {
			System.out.println(integer);
		}
		
	}
}
