import java.io.File;

public class TestClass {
	public static void main(String[] args) {
		listFile("C:/a");
	}
	
	public static void listFile(String path){
		File file = new File(path);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory()){
					listFile(files[i].getPath());
				}else{
					System.out.println(files[i].getPath());
				}
			}
		}else{
			System.out.println(file.getPath());
		}
	}
}
