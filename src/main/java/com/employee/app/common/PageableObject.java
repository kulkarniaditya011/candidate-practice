package com.employee.app.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PageableObject {
    private final ModelMapper modelMapper;
    private final ObjectMapper mapper;

    public <S,T> T map(S source, Class<T> targetClass){
        return modelMapper.map(source,targetClass);
    }

    public <S,T>List<T> mapList(List<S>sourceList, Class<T> targetClass){
        return sourceList.stream()
                .map(s-> modelMapper.map(s, targetClass))
                .collect(Collectors.toList());
    }

    public <T> T readValue(String content, Class<T> targetClass){
        try{
            return mapper.readValue(content, targetClass);
        }catch (JsonProcessingException e){
            return null;
        }
    }

    public JsonNode getJsonNode(String jsonString){
        try{
            return mapper.readTree(jsonString);
        }catch (Exception e){
            return null;
        }
    }

    public <S, T>Page<T> paginateList(List<T> fullList, Page<S> source){
        return new PageImpl<>(fullList,
                PageRequest.of(source.getNumber(),
                        source.getSize(),
                        source.getSort()),
                source.getTotalElements());
    }
}
