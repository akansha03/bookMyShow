package com.firstProject.bookMyShow.service.format;

import com.firstProject.bookMyShow.model.Format;

import java.util.List;

public interface FormatService {

    Format addFormat(Format format);

    Format modifyFormat(Integer id, Format format);

    Format getFormatById(Integer id);

    void deleteFormat(Integer id);

    List<Format> getAllFormats();

    Format getFormatByName(String name);
}
