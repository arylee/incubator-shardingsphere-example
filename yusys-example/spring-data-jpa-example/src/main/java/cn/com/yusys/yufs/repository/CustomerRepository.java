package cn.com.yusys.yufs.repository;

import org.springframework.data.repository.CrudRepository;

import cn.com.yusys.yufs.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
