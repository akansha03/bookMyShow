package com.firstProject.bookMyShow.controller;

import com.firstProject.bookMyShow.exceptions.AlreadyExistsException;
import com.firstProject.bookMyShow.exceptions.ResourceNotFoundException;
import com.firstProject.bookMyShow.model.Format;
import com.firstProject.bookMyShow.payloads.APIResponse;
import com.firstProject.bookMyShow.service.format.FormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/formats")
public class FormatController {

    @Autowired
    public FormatService formatService;

    @PostMapping("/addFormat/")
    public ResponseEntity<APIResponse> addFormat(@RequestBody Format newformat) {
        try {
            Format format = formatService.addFormat(newformat);
            return ResponseEntity.ok(new APIResponse("Format Added!", format));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

   /* @PutMapping("/updateFormat/")
    public HttpStatus updateFormat(@PathVariable Integer formatId,
                                                    @RequestBody Format updateFormat) {
        try {
            Format format = formatService.modifyFormat(formatId, updateFormat);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @PutMapping("/updateFormat/{formatId}")
    public ResponseEntity<APIResponse> updateFormat(@PathVariable Integer formatId, @RequestBody Format updateFormat) {
        try {
            Format format = formatService.modifyFormat(formatId, updateFormat);
            return ResponseEntity.ok(new APIResponse("Format is updated", format));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse( e.getMessage(), null));
        }
    }

    @GetMapping("/getFormat/{formatId}")
    public ResponseEntity<APIResponse> getFormatById(@PathVariable Integer formatId) {
        try {
            Format format = formatService.getFormatById(formatId);
            return ResponseEntity.ok(new APIResponse("Format found!", format));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getAllFormats")
    public ResponseEntity<APIResponse> getAllFormats() {
        try {
            List<Format> formatList = formatService.getAllFormats();
            return ResponseEntity.ok(new APIResponse("List of Formats", formatList));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{formatId}")
    public ResponseEntity<APIResponse> deleteFormat(@PathVariable Integer formatId) {
        try {
            formatService.deleteFormat(formatId);
            return ResponseEntity.ok(new APIResponse("Format Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
