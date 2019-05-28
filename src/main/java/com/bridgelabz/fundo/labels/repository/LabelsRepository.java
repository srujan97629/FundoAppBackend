package com.bridgelabz.fundo.labels.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundo.labels.model.Labels;

@Repository
public interface LabelsRepository extends JpaRepository<Labels , Long>
{
    Optional<Labels> findByLabelName(String labelName); 
    public Labels findByLabelIdAndUserId(long labelId,long userId);
}
