package com.news_management.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;


public class SortUtil {
    public static Sort getSort(String sortBy) {
        String[] sortArray = sortBy.split(",");
        if (sortArray.length != 2) {
            // Invalid sortBy parameter, return default sorting
            return Sort.by(Sort.Order.desc("Created"));
        }

        String field = sortArray[0];
        String direction = sortArray[1];
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);

        return Sort.by(sortDirection, field);
    }
}
