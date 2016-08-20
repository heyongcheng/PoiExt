import com.lemall.settlement.poi.configure.ConfigContext;
import com.lemall.settlement.poi.configure.ConfigureLoader;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.parser.DefConfigureLoader;

public class ParseConfig {

	public static void main(String[] args) {
		
		ConfigureLoader loader = new DefConfigureLoader();
		
		loader.load("D:\\github\\PoiExt\\PoiExt\\src\\main\\resources\\excel-import.xml");
		
		Config config = ConfigContext.CONFIG_MAP.get("test");
		
		System.out.println(config);
	}
}
