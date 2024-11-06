package com.firstProject.bookMyShow.service.format;

import com.firstProject.bookMyShow.exceptions.AlreadyExistsException;
import com.firstProject.bookMyShow.exceptions.ResourceNotFoundException;
import com.firstProject.bookMyShow.model.Format;
import com.firstProject.bookMyShow.repository.FormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FormatServiceImpl implements FormatService{

    @Autowired
    public FormatRepository formatRepository;

    @Override
    public Format addFormat(Format format) {
        return Optional.of(format)
                .filter(f -> !formatRepository.existsByName(f.getName()))
                .map(formatRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException("Format " + format.getName() + " already exists"));
    }

    @Override
    public Format modifyFormat(Integer id, Format updateFormatRequest) {
        return Optional.ofNullable(getFormatById(id))
                .map(existingFormat -> { existingFormat.setName(updateFormatRequest.getName());
                    return formatRepository.save(existingFormat);
                }).orElseThrow(() -> new ResourceNotFoundException("Format Not Found!"));
    }

    @Override
    public Format getFormatById(Integer id) {
        return formatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Format Not Found!"));
    }

    @Override
    public void deleteFormat(Integer id) {
        formatRepository.findById(id)
                .ifPresentOrElse(formatRepository :: delete,
                        () -> {throw new ResourceNotFoundException("Format Not Found!");});
    }

    @Override
    public List<Format> getAllFormats() {
        return formatRepository.findAll();
    }

    @Override
    public Format getFormatByName(String name) {
        return formatRepository.findByName(name)
                .orElseThrow(() -> new AlreadyExistsException("Format Already Exists!"));
    }
}
