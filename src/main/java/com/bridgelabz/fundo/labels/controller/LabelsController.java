package com.bridgelabz.fundo.labels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.labels.dto.LabelsDto;
import com.bridgelabz.fundo.labels.model.Labels;
import com.bridgelabz.fundo.labels.service.ILabelsService;
import com.bridgelabz.fundo.response.Response;

@RestController
@RequestMapping("/user/labels")
public class LabelsController {
	@Autowired
	ILabelsService iLabelService;

	@PostMapping("/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelsDto labelsDto,
			@RequestHeader(name = "token") String token) {
		System.out.println(" in controller");
		Response response = iLabelService.createLabel(labelsDto, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteLabel(@RequestHeader(value = "token") String token,
			@RequestParam(name = "labelId") long labelId) {
		Response response = iLabelService.deleteLabel(token, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelsDto labelsDto,
			@RequestHeader(value = "token") String token, @RequestParam(name = "labelId") long labelId) {
		Response response = iLabelService.updateLabel(labelsDto, token, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getAllLabels")
	public List<Labels> getAllLabels(@RequestHeader(value = "token") String token)
	{
		System.out.println("in controll starting");
		List<Labels> allLabels=iLabelService.getAllLabels(token);
		System.out.println("in control ending");
		return allLabels;
	}

	@PostMapping("/addNote")
	public ResponseEntity<Response> addLabelToNote(@RequestHeader(value = "token") String token,
			@RequestParam(name = "noteId") long noteId, @RequestParam(name = "labelId") long labelId) {
		Response response = iLabelService.addLabelToNote(token, noteId, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/removeNote")
	public ResponseEntity<Response> removeLabelFromNote(@RequestHeader(value = "token") String token,
			@RequestParam(name = "noteId") long noteId, @RequestParam(name = "labelId") long labelId) {
		Response response = iLabelService.removeLabelFromNote(token, noteId, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
