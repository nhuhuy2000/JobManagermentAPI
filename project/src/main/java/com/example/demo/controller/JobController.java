package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Job;
import com.example.demo.error.ErrorResponse;
import com.example.demo.service.JobService;

@RestController
@RequestMapping("/job")
@CrossOrigin("*")
public class JobController {
	
	@Autowired
	private JobService jobService;
	@GetMapping("/list")
	public ResponseEntity<List<Job>> listAll(@RequestParam String keyword,@RequestParam String jobType, @RequestParam String salary, @RequestParam String city, @RequestParam String sortDir){
		return new ResponseEntity<List<Job>>(jobService.listAll(keyword, jobType, salary,city, sortDir), HttpStatus.OK);
	}
	@PostMapping("/save")
	public ResponseEntity<?> saveJob(@RequestBody Job job){
		Job existJob = jobService.getJob(job.getId());
		
		if(existJob != null) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Job đã tồn tại!");
			return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(jobService.saveJob(job), HttpStatus.ACCEPTED);
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getJob(@PathVariable("id") Integer id){
		Job existJob = jobService.getJob(id);
		if(existJob == null) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Job không tồn tại!");
			return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Job>(existJob, HttpStatus.OK);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteJob(@PathVariable("id") Integer id,@RequestParam String keyword,@RequestParam String jobType, @RequestParam String salary, @RequestParam String city, @RequestParam String sortDir){
		Job existJob = jobService.getJob(id);
		if(existJob == null) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Job không tồn tại!");
			return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Job>>(jobService.deleteJob(id, keyword, jobType, salary, city, sortDir), HttpStatus.ACCEPTED);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateJob(@RequestBody Job job , @PathVariable("id") Integer id){
		Job existJob = jobService.getJob(id);
		if(existJob == null) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Job không tồn tại!");
			return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(jobService.updateJob(job, id), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/countJob")
	public ResponseEntity<List<Integer>> listCountJobByMonth(){
		return new ResponseEntity<List<Integer>>(jobService.listJobByMonth(), HttpStatus.OK);
	}
//	@PatchMapping("/update/status/{id}")
//	public ResponseEntity<?> updateStatusJob(@PathVariable("id") Integer id){
//		Job existJob = jobService.getJob(id);
//		if(existJob == null) {
//			ErrorResponse error = new ErrorResponse();
//			error.setMessage("Job không tồn tại!");
//			return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
//		}
//
//	}
}
