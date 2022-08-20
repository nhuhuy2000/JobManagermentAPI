package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Job;
import com.example.demo.repository.JobRepository;

@Service
public class JobService {
	
	@Autowired
	private JobRepository repository;
	
	public List<Job> listAll(String keyword, String jobType, String salary, String city,String sortDir){
		int pageSize = repository.findAll().size();
		if(pageSize == 0) {
			return new ArrayList<Job>();
		}
		int pageNum = 0;
		Sort sort = Sort.by("id");
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		
		if(keyword != null && !keyword.isEmpty()) {
			
			Page<Job> pageResultKeywordAndFilter = repository.findAllWithKeywordAndFilters(keyword, jobType, salary, city, pageable);
			return pageResultKeywordAndFilter.getContent();
		}
		Page<Job> pageResultFilters = repository.findAllWithFilters(jobType, salary, city, pageable);
		return pageResultFilters.getContent();
	}
//	public int listJobActive(){
//		return repository.listJobActive().size();
//	}
	public List<Integer> listJobByMonth(){
		List<Integer> list = new ArrayList<>();
		int result = 0;
		for(int i = 1; i <= 12; i++) {
			result = repository.countJobByMonth(i);
			list.add(result);
		}
		return list;
	}
	public Job saveJob(Job job) {
		repository.save(job);
		Job savedJob = repository.findById(job.getId()).get();
		return savedJob;
	}
	public Job getJob(Integer jobId) {
		boolean existJob = repository.existsJobById(jobId);
		if(existJob == true) {
			
			return repository.findById(jobId).get();
		}
		else return null;
	}
	public List<Job> deleteJob(Integer id, String keyword, String jobType, String salary, String city, String sortDir) {
		 repository.deleteById(id);
		 List<Job> list = listAll(keyword, jobType, salary, city, sortDir);
		return list;
	}
	public Job updateJob(Job job, Integer id) {
		Job jobInDB = repository.findById(id).get();
		jobInDB.setAmountOfPeople(job.getAmountOfPeople());
		jobInDB.setCity(job.getCity());
		jobInDB.setDescription(job.getDescription());
		jobInDB.setEndDate(job.getEndDate());
		jobInDB.setStartDate(job.getStartDate());
		jobInDB.setJobName(job.getJobName());
		jobInDB.setLogo(job.getLogo());
		jobInDB.setSalary(job.getSalary());
		jobInDB.setStatus(job.isStatus());
		repository.save(jobInDB);
		return repository.findById(id).get();
	}
//	public Job updateStatusJob(Integer id) {
//		Job jobInDB = repository.findById(id).get();
//		jobInDB.setStatus(false);
//		repository.save(jobInDB);
//		return repository.findById(id).get();
//	}
	
	
	
}
