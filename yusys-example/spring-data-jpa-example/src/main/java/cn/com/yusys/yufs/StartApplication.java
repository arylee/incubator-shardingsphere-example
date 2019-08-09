package cn.com.yusys.yufs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.com.yusys.yufs.entity.Customer;
import cn.com.yusys.yufs.repository.CustomerRepository;

@SpringBootApplication
public class StartApplication implements CommandLineRunner{

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Customer("123456789", "张三", "男"));
		repository.save(new Customer("123456777", "李四", "男"));
		repository.save(new Customer("123456888", "王五", "男"));
		repository.save(new Customer("123456999", "美眉", "女"));
		System.out.println("Done.");
	}

}
