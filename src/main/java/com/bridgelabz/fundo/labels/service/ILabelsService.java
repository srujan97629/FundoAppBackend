package com.bridgelabz.fundo.labels.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.labels.dto.LabelsDto;
import com.bridgelabz.fundo.labels.model.Labels;
import com.bridgelabz.fundo.response.Response;

@Service
public interface ILabelsService {
	Response createLabel(LabelsDto labelsDto, String token);

	Response updateLabel(LabelsDto labelsDto, String token, long labelId);

	List<Labels> getAllLabels(String token);

	Response deleteLabel(String token, long labelId);

	Response addLabelToNote(String token, long noteId, long labelId);

	Response removeLabelFromNote(String token, long noteId, long labelId);

}
