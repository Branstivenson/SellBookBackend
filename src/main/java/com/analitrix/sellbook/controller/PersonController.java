package com.analitrix.sellbook.controller;

import com.analitrix.sellbook.dto.PersonDtoDelete;
import com.analitrix.sellbook.dto.PersonDtoId;
import com.analitrix.sellbook.entity.Person;
import com.analitrix.sellbook.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="User")
@RestController
@RequestMapping ("/api/v1/user")
@CrossOrigin(origins = "*")
public class PersonController {
	
	@Autowired
	private PersonService personService;

//	@GetMapping("/{id}")
//	public ResponseEntity<Person> findById(@PathVariable Long id){
//		return personService.findById(id);
//	}

	@GetMapping("/{mail}")
	public ResponseEntity<Person> findByMail(@PathVariable String mail){
		return personService.findByMail(mail);
	}
	
	@PostMapping("/insertUser")
	public ResponseEntity<String> insertUser(@RequestBody Person person) {
		return personService.insertUser(person);
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody Person person) {
		return personService.updateUser(person);
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestBody PersonDtoDelete personDtoDelete) {
		return personService.deleteUser(personDtoDelete);
	}
}
