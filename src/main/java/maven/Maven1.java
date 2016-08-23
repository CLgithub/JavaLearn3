package maven;

import org.junit.Test;

import com.cl.mavenTest1.App;

public class Maven1 {
	
	@Test
	public void say(){
		System.out.println("aa");
		App app = new App();
		String info = app.getInfo();
		System.out.println(info);
	}
}
