import java.lang.reflect.Method;

import com.lemall.settlement.poi.excel.ExcelUtils;
import com.lemall.settlement.poi.reflex.ReflexUtils;
import com.lemall.settlement.poi.reflex.model.MethodInfo;

public class TestMethodInfo {
	
	public static void main(String[] args) {
		Method[] methods = ExcelUtils.class.getMethods();
		for(int i=0;i<methods.length;i++){
			MethodInfo methodInfo = ReflexUtils.getMethodInfo(methods[i]);
			System.out.println(methodInfo);
		}
		
	}
}
