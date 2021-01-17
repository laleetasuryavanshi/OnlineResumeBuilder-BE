package com.example.demo.test;

//import static org.junit.Assertions.assertNull;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dao.ExperiencedRepository;
import com.example.demo.entities.Education;
import com.example.demo.entities.Experienced;
import com.example.demo.entities.Project;
import com.example.demo.entities.Skill;
import com.example.demo.entities.WorkEx;
import com.example.demo.exception.ResumeNotFoundException;
import com.example.demo.service.ExperiencedServiceImpl;

@SpringBootTest
class ExperiencedServiceTest {

	@MockBean
	private ExperiencedRepository experiencedRepository;

	@Autowired
	private ExperiencedServiceImpl service;
	
	private static Experienced e;

	@BeforeEach
	public void createExperienced() {

		e = new Experienced("Ramesh", "Kumar", "9876543210", "Mumbai", "Maharashtra", 423151,
				"rk@gmail.com");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date from_date = new Date();
		try {
			from_date = dateFormat.parse("12-JAN-2014");
		} catch (ParseException ex) {

		}
		Date to_date = new Date();
		try { 
			to_date = dateFormat.parse("12-JAN-2017");
		} catch (ParseException ex) {
		}
		WorkEx we = new WorkEx("Programmer", "Amazon", "Mumbai", "MAH", from_date, to_date);
		Date s_date = new Date();
		try { 
			dateFormat.parse("12-JAN-2004");
		} catch (ParseException ex) {
		}
		Date e_date = new Date();
		try { 
			dateFormat.parse("12-JAN-2010");
		} catch (ParseException ex) {
		}
		Education ed = new Education(85, 72, "Engg", 67, "Computer", s_date, e_date);
		Project p1 = new Project("Shopping website");
		Project p2 = new Project("Resume Builder");
		e.getProjects().add(p1);
		e.getProjects().add(p2);
		Skill s1 = new Skill("Java");
		Skill s2 = new Skill("Python");
		e.getSkills().add(s1);
		e.getSkills().add(s2);
		e.setWorkex(we);
		e.setEducation(ed);
		we.setExperienced(e);
		ed.setExperienced(e);
		s1.setExperienced(e);
		s2.setExperienced(e);
		p1.setExperienced(e);
		p2.setExperienced(e);

	}

	@Test
	public void testGetAllExperienced() {

		when(experiencedRepository.findAll()).thenReturn(Stream
				.of(new Experienced("Ram", "Kumar", "9876543210", "Mumbai", "Maharashtra", 423151, "rk@gmail.com"))
				.collect(Collectors.toList()));
		List<Experienced> employees = service.getAllExperienced();
		assertEquals(1, employees.size());
	}

	@Test
	public void testAddExperienced() {
		when(experiencedRepository.save(e)).thenReturn(e);
		Experienced e1=service.addExperienced(e);
		assertEquals(e,e1);
		
	}
	
	@Test
	public void testDeleteExperiencedById()
	{
		assertThrows(ResumeNotFoundException.class,()->service.deleteExperiencedById(e.getId()));
	}
	
	@Test
	public void testGetExperiencedById()
	{
		when(experiencedRepository.findById(e.getId())).thenReturn(Optional.of(e));
		Experienced ex=service.getExperiencedById(e.getId());
		assertEquals(e.getId(),ex.getId());
	}

}
