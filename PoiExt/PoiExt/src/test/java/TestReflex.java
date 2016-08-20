import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.lemall.settlement.poi.excel.ExcelUtils;

public class TestReflex {
	
	public static void main(String[] args) {
		
		ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		Method[] methods = ExcelUtils.class.getMethods();
		for(int i=0;i<methods.length;i++){
			String[] parameterNames = discoverer.getParameterNames(methods[i]);
			for(int j=0;j<parameterNames.length;j++){
				System.out.println(parameterNames[j]);
			}
		}
	}
}
