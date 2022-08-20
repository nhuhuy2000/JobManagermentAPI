package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Job;
@Repository
public interface JobRepository extends JpaRepository<Job, Integer>{
	boolean existsJobById(Integer id);
	
	@Query("Select j from Job j Where j.jobName LIKE %?1% AND j.jobType LIKE %?2% AND j.salary LIKE %?3% AND j.city LIKE %?4%")
	public Page<Job> findAllWithKeywordAndFilters(String keyword, String jobType, String salary, String city, Pageable pageable);
	@Query("Select j from Job j Where  j.jobType LIKE %?1% AND j.salary LIKE %?2% AND j.city LIKE %?3%")
	public Page<Job> findAllWithFilters( String jobType, String salary, String city, Pageable pageable);
//	@Query("Select j from Job j Where j.status = true")
//	public List<Job> listJobActive();
	@Query("Select count(*) from Job j Where month(j.startDate) = ?1")
	public Integer countJobByMonth(int month);
}
